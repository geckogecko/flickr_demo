package at.steinbacher.flickrdemo

import android.app.Application
import at.steinbacher.common.di.commonModule
import at.steinbacher.flickrdemo.feature.home.di.homeModule
import at.steinbacher.flickrdemo.network.flickr.di.flickrServerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlickrDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FlickrDemoApplication)
            modules(listOf(
                commonModule,
                homeModule,
                flickrServerModule
            ))
        }
    }
}