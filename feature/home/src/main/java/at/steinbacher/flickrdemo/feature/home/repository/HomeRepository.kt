package at.steinbacher.flickrdemo.feature.home.repository

import at.steinbacher.common.FlickrResult
import at.steinbacher.common.FlickrResult.Companion.toFlow
import at.steinbacher.common.FlickrResult.Companion.toResult
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage
import at.steinbacher.flickrdemo.network.flickr.server.FlickrServer
import com.example.common.network.makeApiCall
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
    suspend fun getPictures(tags: List<String>): Flow<FlickrResult<List<FlickrImage>>>
}

class HomeRepository(
    private val server: FlickrServer
): IHomeRepository {
    override suspend fun getPictures(tags: List<String>): Flow<FlickrResult<List<FlickrImage>>> {
        return makeApiCall { server.getPictures(tags) }
            .toResult()
            .transform { it.items }
            .toFlow()
    }
}