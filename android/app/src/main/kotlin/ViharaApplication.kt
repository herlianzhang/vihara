package pro.herlian.vihara

import android.app.Application
import pro.herlian.vihara.core.common.env.Env
import pro.herlian.vihara.di.AppComponent
import pro.herlian.vihara.di.AppModule
import pro.herlian.vihara.di.DaggerAppComponent
import pro.herlian.vihara.feature.auth.di.AuthComponent
import pro.herlian.vihara.feature.auth.di.AuthComponentProvider
import timber.log.Timber


class ViharaApplication: Application(), AuthComponentProvider {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent =  DaggerAppComponent.builder().appModule(AppModule(this)).build()
        if (Env.IS_DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun provideAuthComponent(): AuthComponent {
        return appComponent.authComponent().create()
    }
}