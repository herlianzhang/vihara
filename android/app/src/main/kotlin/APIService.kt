package pro.herlian.vihara

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class User(

    val userId: String,
    val username: String,
    val googleId: String?,
    val appleId: String?,
    val name: String,
    val email: String?,
    val avatar: String?
)

object APIService {
    private const val BASE_URL = "http://192.168.1.3:8080/"

    fun getInstance(client: OkHttpClient): API {
        val gson = GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        return retrofit.create(API::class.java)
    }

    fun getClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    interface API {
        @GET("users")
        fun listUsers(): Call<List<User>>
    }
}