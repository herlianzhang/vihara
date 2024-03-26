package pro.herlian.vihara.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import pro.herlian.vihara.feature.auth.navigation.SINGIN_ROUTE
import pro.herlian.vihara.feature.auth.navigation.signinScreen
import pro.herlian.vihara.ui.ViharaAppState


@Composable
fun ViharaNavHost(
    appState: ViharaAppState,
    modifier: Modifier = Modifier,
    startDestination: String = SINGIN_ROUTE
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        signinScreen(appState.vmFactory)
    }
}