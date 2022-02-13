package graduation.fcm.dermai.data.remote

import graduation.fcm.dermai.domain.model.auth.AuthResponse
import graduation.fcm.dermai.domain.model.home.HistoryResponse
import graduation.fcm.dermai.domain.model.home.ScanResponse
import graduation.fcm.dermai.domain.model.home.UserResponse
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

    @GET("get_user_data")
    suspend fun getUserData(
        @Header("Authorization") token: String,
    ): Response<UserResponse>

    @GET("scan/history")
    suspend fun getScanHistory(
        @Header("Authorization") token: String,
    ): Response<HistoryResponse>

    @POST("scan/history/{resultId}/confirm-unconfirm/{diseaseId}")
    suspend fun confirmOrUnConfirm(
        @Path("resultId") resultId: Int,
        @Path("diseaseId") diseaseId: Int,
        @Header("Authorization") token: String,
    ): Response<ScanResponse>

}