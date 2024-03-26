package pro.herlian.vihara.core.network.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pro.herlian.vihara.core.common.env.Env
import pro.herlian.vihara.core.network.ViharaNetworkDataSource
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

data class User(
    val userId: String,
    val username: String,
    val googleId: String?,
    val appleId: String?,
    val name: String,
    val email: String?,
    val avatar: String?
)

private interface RetrofitViharaNetworkApi {
    @GET("users")
    suspend fun listUsers(): Response<List<User>>
}

@Singleton
class RetrofitViharaNetwork @Inject constructor(
    gson: Gson,
    client: OkHttpClient
): ViharaNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(Env.BACKEND_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(RetrofitViharaNetworkApi::class.java)

    override suspend fun getUsers(): Response<List<User>> = networkApi.listUsers()
}