package graduation.fcm.dermai.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import graduation.fcm.dermai.common.IMAGE_SEND_KEY
import graduation.fcm.dermai.common.SharedPreferenceManger
import graduation.fcm.dermai.common.getImageAsMultipartBodyPart
import graduation.fcm.dermai.data.remote.ApiService
import graduation.fcm.dermai.domain.model.home.ScanResponse
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val pref: SharedPreferenceManger,
    private val app: Application
) {

    suspend fun uploadDiseaseImage(uriString: String): Response<ScanResponse> {
        val uri = Uri.parse(uriString)
        val img = getImageAsMultipartBodyPart(app, uri, IMAGE_SEND_KEY)
        Log.e("TAG", "uploadDiseaseImage: img=$img\nuri=$uri")
        return api.uploadDiseaseImage(img, pref.token)
    }

    suspend fun getUserData() = api.getUserData(pref.token)

    suspend fun getScanHistory() = api.getScanHistory(pref.token)

    suspend fun confirmOrUnConfirm(resultId: Int, diseaseId: Int) =
        api.confirmOrUnConfirm(resultId, diseaseId, pref.token)

    suspend fun getSingleScanHistory(diseaseId: Int) =
        api.getSingleScanHistory(diseaseId, pref.token)

    suspend fun getDiseaseSearchResult(searchQuery: String) =
        api.getDiseaseSearchResult(searchQuery, pref.token)

    suspend fun getDoctors(longitude: Double, latitude: Double) =
        api.getDoctors(longitude, latitude, pref.token)

    suspend fun updateSearchHistory(id: Int) = api.updateSearchHistory(id, pref.token)

    suspend fun getSearchHistory() = api.getSearchHistory(pref.token)

    suspend fun getMedicines(diseaseId: Int) = api.getMedicines(diseaseId, pref.token)
}