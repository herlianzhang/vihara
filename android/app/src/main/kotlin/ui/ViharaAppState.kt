package pro.herlian.vihara.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberViharaAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    vmFactory: ViewModelProvider.Factory
): ViharaAppState {
    val getVmFactory: ViewModelProvider.Factory = remember { vmFactory }
    return remember(
        navController,
        coroutineScope,
    ) {
        ViharaAppState(
            navController,
            getVmFactory,
            coroutineScope,
        )
    }
}

@Stable
class ViharaAppState(
    val navController: NavHostController,
    val vmFactory: ViewModelProvider.Factory,
    coroutineScope: CoroutineScope,
)
