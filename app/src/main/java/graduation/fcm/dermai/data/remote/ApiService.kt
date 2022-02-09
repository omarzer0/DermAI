package graduation.fcm.dermai.data.remote

import graduation.fcm.dermai.domain.model.auth.AuthResponse
import graduation.fcm.dermai.domain.model.home.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
    ): Response<AuthResponse>

    @POST("login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Response<AuthResponse>

    @Multipart
    @POST("diseases")
    suspend fun uploadDiseaseImage(
        @Part img: MultipartBody.Part,
        @Header("Authorization") token: String,
    ): Response<ScanResponse>

}