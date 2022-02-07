package graduation.fcm.dermai.data.remote

import graduation.fcm.dermai.domain.model.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("gender") gender: Int,
    ): Response<AuthResponse>

    @POST("login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Response<AuthResponse>

}