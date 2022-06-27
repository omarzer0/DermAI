package graduation.fcm.dermai.data.remote

import graduation.fcm.dermai.core.BaseResponse
import graduation.fcm.dermai.domain.model.auth.AuthResponse
import graduation.fcm.dermai.domain.model.home.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<AuthResponse>

    @POST("login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<AuthResponse>

    @Multipart
    @POST("diseases")
    suspend fun uploadDiseaseImage(
        @Part img: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ScanResponse>


    @Multipart
    @POST("diseases")
    suspend fun uploadFakeDiseaseImage(
        @Part img: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ScanResponse>


    @GET("get_user_data")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @GET("scan/history")
    suspend fun getScanHistory(
        @Header("Authorization") token: String
    ): Response<HistoryResponse>

    @POST("scan/history/{resultId}/confirm-unconfirm/{diseaseId}")
    suspend fun confirmOrUnConfirm(
        @Path("resultId") resultId: Int,
        @Path("diseaseId") diseaseId: Int,
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("scan/history/{diseaseId}")
    suspend fun getSingleScanHistory(
        @Path("diseaseId") diseaseId: Int,
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("search")
    suspend fun getDiseaseSearchResult(
        @Query("query") searchQuery: String,
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("doctors")
    suspend fun getDoctors(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Header("Authorization") token: String
    ): Response<DoctorResponse>

    @POST("search/history/update/{id}")
    suspend fun updateSearchHistory(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse>

    @GET("search/history")
    suspend fun getSearchHistory(
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("medicines")
    suspend fun getMedicines(
        @Query("disease_id") diseaseId: Int,
        @Header("Authorization") token: String
    ): Response<MedicineResponse>

}