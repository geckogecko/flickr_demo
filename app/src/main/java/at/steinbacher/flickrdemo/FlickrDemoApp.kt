package at.steinbacher.flickrdemo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import at.steinbacher.flickrdemo.components.FlickrDemoScaffold
import at.steinbacher.flickrdemo.navigation.NavGraphs
import at.steinbacher.flickrdemo.navigation.appCurrentDestinationAsState
import at.steinbacher.flickrdemo.navigation.destinations.HomeScreenDestination
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun FlickrDemoApp() {
    val engine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING, //default `rootDefaultAnimations` means no animations
    )
    val navController = engine.rememberNavController()

    FlickrDemoScaffold(
        startRoute = HomeScreenDestination,
        navController = navController,
        topBar = { _, _ -> },
        bottomBar = {},
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            engine = engine,
            startRoute = HomeScreenDestination,
            navController = navController
        )
    }
}
