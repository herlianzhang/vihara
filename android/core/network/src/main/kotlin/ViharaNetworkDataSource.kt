package pro.herlian.vihara.core.network

import pro.herlian.vihara.core.network.retrofit.User
import retrofit2.Response

interface ViharaNetworkDataSource {
    suspend fun getUsers(): Response<List<User>>
}