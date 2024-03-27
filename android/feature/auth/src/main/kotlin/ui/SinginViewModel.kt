package pro.herlian.vihara.feature.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.herlian.vihara.core.network.ViharaNetworkDataSource
import timber.log.Timber
import javax.inject.Inject

class SigninViewModel @Inject constructor(
    private val networkDataSource: ViharaNetworkDataSource
): ViewModel() {
    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = networkDataSource.getUsers()
                if (response.isSuccessful) {
                    Timber.d("Masuk pak eko ${response.body()?.toString()}")
                } else {
                    Timber.e(response.errorBody()?.toString())
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}