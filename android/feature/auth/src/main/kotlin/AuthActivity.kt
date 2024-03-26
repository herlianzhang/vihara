package pro.herlian.vihara.feature.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import pro.herlian.vihara.feature.auth.di.AuthComponent
import pro.herlian.vihara.feature.auth.di.AuthComponentProvider
import pro.herlian.vihara.feature.auth.ui.SigninRoute
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private lateinit var authComponent: AuthComponent

    @Inject lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        authComponent = (applicationContext as AuthComponentProvider).provideAuthComponent()
        authComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                SigninRoute(vmFactory)
            }
        }
    }
}