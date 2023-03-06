package at.steinbacher.flickrdemo.network.flickr.server

import at.steinbacher.flickrdemo.network.flickr.model.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrServer {

    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPictures(
        @Query("tags") tags: List<String>
    ): Response<FlickrResponse>
}