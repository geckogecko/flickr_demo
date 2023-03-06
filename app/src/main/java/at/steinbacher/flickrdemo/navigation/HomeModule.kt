package at.steinbacher.flickrdemo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import at.steinbacher.flickrdemo.feature.home.view.HomeScreenContent
import at.steinbacher.flickrdemo.feature.home.view.ImageDetailScreenContent
import at.steinbacher.flickrdemo.navigation.destinations.ImageDetailScreenDestination
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    HomeScreenContent(
        onImageDetail = { image -> navigator.navigate(ImageDetailScreenDestination(image))},
        modifier = Modifier.testTag("home_screen")
    )
}

@Destination
@Composable
fun ImageDetailScreen(
    image: FlickrImage,
    navigator: DestinationsNavigator
) {
    ImageDetailScreenContent(
        image = image,
        modifier = Modifier.testTag("image_detail_screen")
    )
}