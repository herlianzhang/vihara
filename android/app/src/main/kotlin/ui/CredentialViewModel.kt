package pro.herlian.vihara.ui

import android.app.Application
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pro.herlian.vihara.APIService
import pro.herlian.vihara.Credential
import pro.herlian.vihara.User
import pro.herlian.vihara.core.common.env.Env
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MantapPak @Inject constructor() {
    fun printMasukPakeko() {
        Timber.d("Masuk pak eko")
    }
}

class CredentialViewModel @Inject constructor(application: Application, mantapPak: MantapPak): AndroidViewModel(application) {
    private val service = APIService.getInstance(APIService.getClient(application))
    private val _isSignedIn = MutableStateFlow(Credential.isSignedIn())
    val isSignedIn: StateFlow<Boolean> = _isSignedIn.asStateFlow()

    private val credentialManager by lazy {
        CredentialManager.create(application)
    }

    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(false)
        .setServerClientId(Env.GOOGLE_CLIENT_ID)
        .build()

    private val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    init {
        mantapPak.printMasukPakeko()
        fetchUsers()
    }

    fun signIn(context: Context) {
        viewModelScope.launch {
            try {
                val credential = getCredential(context) ?: return@launch

                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        Credential.storeIDToken(googleIdTokenCredential.idToken)
                        _isSignedIn.emit(true)
                        Timber.d(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Timber.e("Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Timber.e("Unexpected type of credential")
                }
            } catch (e: Exception) {
                Timber.e("Error getting credential", e)
            }
        }
    }

    private fun fetchUsers() {
        val call = service.listUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(p0: Call<List<User>>, p1: Response<List<User>>) {
                if (p1.isSuccessful) {
                    val user = p1.body()
                    Timber.d(user?.toString() ?: "")
                }
            }

            override fun onFailure(p0: Call<List<User>>, p1: Throwable) {
                Timber.e("Error to fetch users", p1)
            }
        })
    }

    fun logout() {
        Credential.clearAll()
        viewModelScope.launch {
            _isSignedIn.emit(false)
        }
    }

    private suspend fun getCredential(context: Context): CustomCredential? {
        try {
            val credentialResponse = credentialManager.getCredential(
                request = request,
                context = context,
            )

            return credentialResponse.credential as? CustomCredential
        }
        catch (e: GetCredentialCancellationException) {
            //User cancelled the request. Return nothing
            Timber.e("Error getting credential", e)
            return null
        }
        catch (e: NoCredentialException) {
            //We don't have a matching credential
            Timber.e("Error getting credential", e)
            return null
        }
        catch (e: GetCredentialException) {
            Timber.e("Error getting credential", e)
            throw e
        }
    }
}