package at.steinbacher.flickrdemo.network.flickr.di

import at.steinbacher.flickrdemo.network.flickr.server.FlickrServer
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DEFAULT_OK_HTTP_CLIENT = "default_ok_http_client"
private const val RETROFIT = "retrofit"

val flickrServerModule = module {
    single(named(DEFAULT_OK_HTTP_CLIENT)) { getOkHttp() }
    single { moshi }

    single(named(RETROFIT)) {
        retrofit(get(named(DEFAULT_OK_HTTP_CLIENT)), "https://api.flickr.com/services/", get())
    }

    single<FlickrServer> { getFlickrServer(get(named(RETROFIT))) }
}

private fun getOkHttp() = OkHttpClient.Builder().apply {
}.build()


private val moshi by lazy {
    Moshi.Builder()
        .build()
}

private fun retrofit(client: OkHttpClient, baseUrl: String, moshi: Moshi) = Retrofit.Builder()
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(baseUrl)
    .build()

private fun getFlickrServer(retrofit: Retrofit) = retrofit.create(FlickrServer::class.java)