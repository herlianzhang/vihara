package pro.herlian.vihara

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.herlian.vihara.di.AppComponent
import pro.herlian.vihara.feature.auth.AuthActivity
import pro.herlian.vihara.ui.CredentialViewModel
import pro.herlian.vihara.ui.rememberViharaAppState
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appComponent: AppComponent

    @Inject lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as ViharaApplication).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Credential.initSharedPref(this)
//        startActivity(Chucker.getLaunchIntent(this))
        setContent {
            val appState = rememberViharaAppState(vmFactory = this@MainActivity.vmFactory)
            MaterialTheme {
//                ViharaApp(appState)
                Button(
                    onClick = {
                        val context = this@MainActivity
                        val intent = Intent(context, AuthActivity::class.java)
                        context.startActivity(intent)
                    }, content = {
                        Text("Go Next - Test Fastlane 5")
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(viewModel: CredentialViewModel = viewModel()) {
    val context = LocalContext.current
    val isSignedIn by viewModel.isSignedIn.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSignedIn) {
            Text(text = "Hello Guys")
            Button(onClick = {
                viewModel.logout()
            }) {
                Text(text = "Logout")
            }
        } else {
            Text(text = "Hello Guys")
            Button(onClick = {
                viewModel.signIn(context)
            }) {
                Text(text = "Login")
            }
        }
    }
}