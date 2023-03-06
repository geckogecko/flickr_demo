package at.steinbacher.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.steinbacher.common.UIState

@Composable
fun ScreenContent(
    state: UIState<*>,
    modifier: Modifier = Modifier,
    onError: @Composable (modifier: Modifier) -> Unit = { DefaultOnError(modifier) },
    onLoading: @Composable (modifier: Modifier) -> Unit = { DefaultOnLoading(modifier) },
    onDefault: @Composable (modifier: Modifier) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        onDefault(modifier)

        when (state) {
            is UIState.Error -> onError(modifier)
            is UIState.Loading -> onLoading(modifier)
            else -> {}
        }
    }
}

@Composable
private fun DefaultOnError(modifier: Modifier) {
    Box{}
}

@Composable
private fun DefaultOnLoading(modifier: Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center)
                .size(50.dp)
        )
    }
}