package pro.herlian.vihara.feature.auth.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun SigninRoute(
    vmFactory: ViewModelProvider.Factory,
    viewModel: SigninViewModel = viewModel(factory = vmFactory)
) {
    SigninScreen()
}

@Composable
internal fun SigninScreen() {
    Text("Lets signin")
}