package pro.herlian.vihara.feature.auth.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.herlian.vihara.core.common.di.ViewModelKey
import pro.herlian.vihara.feature.auth.ui.SigninViewModel

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SigninViewModel::class)
    abstract fun bindsSinginViewModel(viewModel: SigninViewModel): ViewModel
}