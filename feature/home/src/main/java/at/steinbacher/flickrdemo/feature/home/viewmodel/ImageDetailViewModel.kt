package at.steinbacher.flickrdemo.feature.home.viewmodel

import at.steinbacher.common.BaseViewModel
import at.steinbacher.flickrdemo.feature.home.repository.IHomeRepository
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage

data class ImageDetailScreenState(
    var test: String = ""
)

class ImageDetailViewModel(
) : BaseViewModel<ImageDetailScreenState, NavEvent>(
    initialState = ImageDetailScreenState()
) {
    sealed class NavEvent {

    }
}