package at.steinbacher.flickrdemo.feature.home.viewmodel

import at.steinbacher.common.BaseViewModel
import at.steinbacher.flickrdemo.feature.home.repository.IHomeRepository
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage

data class ImageDetailScreenState(
    var image: FlickrImage? = null
)

class ImageDetailViewModel(
    flickrImage: FlickrImage,
) : BaseViewModel<ImageDetailScreenState, NavEvent>(
    initialState = ImageDetailScreenState()
) {
    init {
        setDefaultState {
            image = flickrImage
        }
    }
}