package pro.herlian.vihara.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import pro.herlian.vihara.navigation.ViharaNavHost

@Composable
fun ViharaApp(appState: ViharaAppState) {
    Scaffold {
        ViharaNavHost(
            appState = appState
        )
    }
}