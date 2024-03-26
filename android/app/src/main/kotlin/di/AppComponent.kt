package pro.herlian.vihara.di

import dagger.Component
import pro.herlian.vihara.MainActivity
import pro.herlian.vihara.core.common.di.ViewModelFactoryModule
import pro.herlian.vihara.core.network.di.BindNetworkModule
import pro.herlian.vihara.core.network.di.NetworkModule
import pro.herlian.vihara.feature.auth.di.AuthComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    SubcomponentsModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    NetworkModule::class,
    BindNetworkModule::class
])
interface AppComponent {
    fun authComponent(): AuthComponent.Factory

    fun inject(activity: MainActivity)
}