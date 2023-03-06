package at.steinbacher.flickrdemo.feature.home.viewmodel

import android.util.Log
import at.steinbacher.common.BaseViewModel
import at.steinbacher.flickrdemo.feature.home.repository.IHomeRepository
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage

data class HomeScreenState(
    var tags: List<String> = listOf("cat"),
    var images: List<FlickrImage> = listOf(),
)

sealed class NavEvent {
    class ImageDetail(val image: FlickrImage): NavEvent()
}

class HomeViewModel(
    val homeRepository: IHomeRepository
) : BaseViewModel<HomeScreenState, NavEvent>(
    initialState = HomeScreenState()
) {
    init {
        loadImages(currentState.tags)
    }

    fun onImageClicked(image: FlickrImage) {
        navigate(NavEvent.ImageDetail(image))
    }

    fun onNewTagEntered(newTag: String) {
        setDefaultState {
            tags = ArrayList(currentState.tags).apply { add(newTag) }
        }

        loadImages(currentState.tags)
    }

    fun onTagClicked(tag: String) {
        setDefaultState {
            tags = ArrayList(currentState.tags).apply { remove(tag) }
        }

        loadImages(currentState.tags)
    }

    private fun loadImages(tags: List<String>) {
        collectFlow(
            flowRequest = { homeRepository.getPictures(tags) },
            onSuccess = {
                setDefaultState {
                    images = it
                }
            }
        )
    }
}