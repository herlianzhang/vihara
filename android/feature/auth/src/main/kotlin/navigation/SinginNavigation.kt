package pro.herlian.vihara.feature.auth.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pro.herlian.vihara.feature.auth.ui.SigninRoute

const val SINGIN_ROUTE = "SIGNIN"

fun NavGraphBuilder.signinScreen(vmFactory: ViewModelProvider.Factory) {
    composable(route = SINGIN_ROUTE) {
        SigninRoute(vmFactory)
    }
}