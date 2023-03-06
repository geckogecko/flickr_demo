package at.steinbacher.common

import at.steinbacher.common.network.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class FlickrResult<T> {
    data class Success<T>(val data: T) : FlickrResult<T>()
    data class SoftError<T>(val error: Throwable) : FlickrResult<T>()
    data class Error<T>(val error: Throwable) : FlickrResult<T>()

    companion object {
        fun <T> ApiResult<T>.toResult(): FlickrResult<T> = when(this) {
            is ApiResult.Success -> Success(data = this.data)
            is ApiResult.Error -> Error(error = this.error)
        }

        fun <T> FlickrResult<T>.toFlow(): Flow<FlickrResult<T>> = flowOf(this)
    }

    fun <Q> transform(target: (apiResult: T) -> Q): FlickrResult<Q> {
        return when(this) {
            is Success -> Success(data = target.invoke(this.data))
            is Error -> Error(error = this.error)
            is SoftError -> SoftError(error = this.error)
        }
    }
}