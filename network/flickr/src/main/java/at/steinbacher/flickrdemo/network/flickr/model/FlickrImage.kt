package at.steinbacher.flickrdemo.network.flickr.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FlickrImage(
    val title: String,
    val link: String,
    val media: Media
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Media(
    val m: String
): Parcelable