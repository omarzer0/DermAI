package graduation.fcm.dermai.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import graduation.fcm.dermai.common.*
import graduation.fcm.dermai.data.remote.ApiService
import graduation.fcm.dermai.di.ApplicationScope
import graduation.fcm.dermai.domain.model.home.ScanResponse
import kotlinx.coroutines.CoroutineScope
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val pref: SharedPreferenceManger,
    private val app: Application,
    @ApplicationScope private val scope: CoroutineScope
) {

    suspend fun uploadDiseaseImage(uriString: String): Response<ScanResponse> {
        val uri = Uri.parse(uriString)
        val img = getImageAsMultipartBodyPart(app, uri, IMAGE_SEND_KEY)
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

    suspend fun getMedicines(diseaseId: Int? = null) = api.getMedicines(diseaseId, pref.token)

    suspend fun deleteSingleSearchHistory(diseaseId: Int) =
        api.deleteSingleSearchHistory(diseaseId, pref.token)

    suspend fun updateUserInfo(skinColor: String, age: Int, gender: Int) =
        api.updateUserInfo(skinColor, age, gender, pref.token)

    suspend fun addToSearchHistory(diseaseId: Int) = api.addToSearchHistory(diseaseId, pref.token)
}