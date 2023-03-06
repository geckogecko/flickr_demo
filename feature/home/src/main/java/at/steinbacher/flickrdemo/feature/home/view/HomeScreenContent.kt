package at.steinbacher.flickrdemo.feature.home.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import at.steinbacher.flickrdemo.feature.home.viewmodel.HomeViewModel
import at.steinbacher.flickrdemo.feature.home.viewmodel.NavEvent
import at.steinbacher.flickrdemo.network.flickr.model.FlickrImage
import at.steinbacher.theme.components.ScreenContent
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreenContent(
    onImageDetail: (image: FlickrImage) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = getViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsState().value

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = false
    )

    LaunchedEffect(viewModel.navEvents) {
        viewModel.navEvents.collect {
            when (it) {
                is NavEvent.ImageDetail -> onImageDetail(it.image)
            }
        }
    }

    ScreenContent(
        state = state,
        modifier = modifier,
    ) { contentModifier ->
        Box(contentModifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = contentModifier.fillMaxSize(),
                content = {
                    items(state.data.images.size) { index ->
                        val image = state.data.images[index]

                        PreviewImage(
                            url = image.media.m,
                            title = image.title,
                            onClicked = { viewModel.onImageClicked(image) },
                            modifier = Modifier
                                .width(150.dp)
                                .height(250.dp)
                        )
                    }
                }
            )

            TagBar(
                tags = state.data.tags,
                onNewTagEntered = { viewModel.onNewTagEntered(it) },
                onTagClicked = { viewModel.onTagClicked(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun PreviewImage(
    title: String,
    url: String,
    onClicked: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = url,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClicked() }
        )

        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TagBar(
    tags: List<String>,
    onNewTagEntered: (newTag: String) -> Unit,
    onTagClicked: (tag: String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.background(Color.Black.copy(alpha = 0.7f))
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            items(tags) { tag ->
                Chip(
                    onClick = { onTagClicked(tag) }
                ) {
                    Text(text = tag)
                }
            }
        }

        val inputText = remember { mutableStateOf("") }
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onNewTagEntered(inputText.value)
                    inputText.value = ""
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
                .systemBarsPadding()
        )
    }
}