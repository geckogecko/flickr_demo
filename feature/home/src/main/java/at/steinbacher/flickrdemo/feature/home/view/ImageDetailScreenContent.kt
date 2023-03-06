package at.steinbacher.flickrdemo.feature.home.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import at.steinbacher.flickrdemo.feature.home.viewmodel.HomeViewModel
import at.steinbacher.flickrdemo.feature.home.viewmodel.ImageDetailViewModel
import at.steinbacher.flickrdemo.feature.home.viewmodel.NavEvent
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage
import at.steinbacher.theme.components.ScreenContent
import coil.compose.AsyncImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ImageDetailScreenContent(
    image: FlickrImage,
    modifier: Modifier = Modifier
) {
    val viewModel = getViewModel<ImageDetailViewModel> { parametersOf(image) }
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(viewModel.navEvents) {
        viewModel.navEvents.collect {
            when (it) {
                else -> {}
            }
        }
    }

    ScreenContent(
        state = state,
        modifier = modifier,
    ) { contentModifier ->
        state.data.image?.let {
            AsyncImage(
                model = it.media.m,
                contentDescription = it.title,
                contentScale = ContentScale.Crop,
                modifier = contentModifier
                    .fillMaxSize()
            )
        }
    }
}