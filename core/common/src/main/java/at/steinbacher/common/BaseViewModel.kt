package at.steinbacher.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class UIState<T>(val data: T) {
    class Loading<T>(data: T) : UIState<T>(data)
    class Default<T>(data: T) : UIState<T>(data)
    class Error<T>(data: T, val error: String) : UIState<T>(data)
}

open class BaseViewModel<T, NAV_EVENT>(
    initialState: T
) : ViewModel(), KoinComponent {

    companion object {
        const val TAG = "BaseViewModel"
    }

    protected val dispatchers by inject<Dispatchers>()

    private val _state: MutableStateFlow<UIState<T>> = MutableStateFlow(UIState.Default(initialState))
    val state: StateFlow<UIState<T>>
        get() = _state

    private val _navEvents = Channel<NAV_EVENT>()
    val navEvents = _navEvents.receiveAsFlow()

    val currentState
        get() = state.value.data

    fun setLoadingState(update: (T.() -> Unit)? = null) {
        _state.value = UIState.Loading(currentState)
            .onLoadingState()
            .applyUpdate(update)
    }

    open fun UIState.Loading<T>.onLoadingState(): UIState.Loading<T> {
        return this
    }

    fun setDefaultState(update: (T.() -> Unit)? = null) {
        _state.value = UIState.Default(currentState)
            .onDefaultState()
            .applyUpdate(update)
    }

    open fun UIState.Default<T>.onDefaultState(): UIState.Default<T> {
        return this
    }

    fun setErrorState(throwable: Throwable?, alternativeErrorText: String? = null, update: (T.() -> Unit)? = null) {
        setErrorState(message = "Error: ${throwable?.message ?: alternativeErrorText}", update = update)
    }

    fun setErrorState(message: String, update: (T.() -> Unit)? = null) {
        _state.value = UIState.Error(currentState, error = message)
            .applyUpdate(update)

    }

    fun <T> collectFlow(
        flowRequest: suspend () -> Flow<FlickrResult<T>>,
        onSuccess: (data: T) -> Unit,
        onError: ((error: Throwable) -> Unit)? = null,
        onLoading: () -> Unit = { setLoadingState() },
    ): Job {
        return viewModelScope.launch(dispatchers.io) {
            onLoading()

            flowRequest().collect { result ->
                when (result) {
                    is FlickrResult.Success -> {
                        onSuccess(result.data)
                    }
                    is FlickrResult.Error -> {
                        if(onError != null) {
                            onError.invoke(result.error)
                        } else {
                            setErrorState(
                                throwable = result.error,
                                alternativeErrorText = "collectFlow failed and exception is null"
                            )
                        }
                    }
                    is FlickrResult.SoftError -> {
                        Log.i(TAG, "collectFlow: ${result.error.stackTrace}")
                    }
                }
            }
        }
    }

    fun <T> collectFlowBackground(
        flowRequest: suspend () -> Flow<FlickrResult<T>>,
        onSuccess: (data: T) -> Unit,
        onError: ((error: Throwable) -> Unit)? = null,
    ): Job {
        return collectFlow(
            flowRequest = flowRequest,
            onSuccess = onSuccess,
            onError = onError,
            onLoading = {}
        )
    }

    fun navigate(navEvent: NAV_EVENT) {
        viewModelScope.launch {
            _navEvents.send(navEvent)
        }
    }

    private fun UIState<T>.applyUpdate(update: (T.() -> Unit)?): UIState<T> {
        if (update != null) this.data.update()
        return this
    }
}
