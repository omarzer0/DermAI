package graduation.fcm.dermai.data.remote

import graduation.fcm.dermai.core.BaseResponse
import graduation.fcm.dermai.domain.model.auth.AuthResponse
import graduation.fcm.dermai.domain.model.home.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("v1/register")
    suspend fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<AuthResponse>

    @POST("v1/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<AuthResponse>

    @Multipart
    @POST("v1/diseases")
    suspend fun uploadDiseaseImage(
        @Part img: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ScanResponse>


    @Multipart
    @POST("v1/diseases")
    suspend fun uploadFakeDiseaseImage(
        @Part img: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ScanResponse>


    @GET("v1/get_user_data")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @GET("v1/scan/history")
    suspend fun getScanHistory(
        @Header("Authorization") token: String
    ): Response<HistoryResponse>

    @POST("v1/scan/history/{resultId}/confirm-unconfirm/{diseaseId}")
    suspend fun confirmOrUnConfirm(
        @Path("resultId") resultId: Int,
        @Path("diseaseId") diseaseId: Int,
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("v1/scan/history/{resultID}")
    suspend fun getSingleScanHistory(
        @Path("resultID") diseaseId: Int,
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("v1/search")
    suspend fun getDiseaseSearchResult(
        @Query("query") searchQuery: String,
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("v1/doctors")
    suspend fun getDoctors(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Header("Authorization") token: String
    ): Response<DoctorResponse>

    @POST("v1/search/history/update/{id}")
    suspend fun updateSearchHistory(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse>

    @GET("v1/search/history")
    suspend fun getSearchHistory(
        @Header("Authorization") token: String
    ): Response<ScanResponse>

    @GET("v1/medicines")
    suspend fun getMedicines(
        @Query("disease_id") diseaseId: Int?,
        @Header("Authorization") token: String
    ): Response<MedicineResponse>

    @POST("v1/search/history/delete/{id}")
    suspend fun deleteSingleSearchHistory(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<ScanResponse>


    @POST("v1/update_user_data")
    suspend fun updateUserInfo(
        @Query("skin_color") skinColor: String,
        @Query("age") age: Int,
        @Query("gender") gender: Int,
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @POST("v1/search/history/{diseaseID}")
    suspend fun addToSearchHistory(
        @Path("diseaseID") diseaseID: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse>
}