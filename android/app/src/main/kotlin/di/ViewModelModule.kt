package pro.herlian.vihara.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.herlian.vihara.core.common.di.ViewModelKey
import pro.herlian.vihara.ui.CredentialViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CredentialViewModel::class)
    abstract fun bindsCredentialViewModel(viewModel: CredentialViewModel): ViewModel
}