package pro.herlian.vihara.feature.auth.di

import dagger.Subcomponent
import pro.herlian.vihara.feature.auth.AuthActivity

@Subcomponent(
    modules = [
        ViewModelModule::class,
    ]
)
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(activity: AuthActivity)
}