package graduation.fcm.dermai.presentation.main.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.SharedPreferenceManger
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.ScanData
import graduation.fcm.dermai.domain.model.home.ScanResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
    private val sharedPreferenceManger: SharedPreferenceManger
) : BaseViewModel() {

    private val _scanResult = MutableLiveData<ScanResponse>()
    val scanResult: LiveData<ScanResponse> = _scanResult

    var errorHandled = false

    private fun uploadDiseaseImage(uriString: String) {
        safeCallApi({
            errorHandled = false
            repo.uploadDiseaseImage(uriString)
        }, {
            _scanResult.value = it
        }, errorResponse = {
            Log.e("TAG", "uploadDiseaseImage: error")
            val errorResponse = ScanResponse(ScanData(emptyList()))
            errorResponse.error = it
            _scanResult.value = errorResponse
        })
    }

    init {
        val uri = sharedPreferenceManger.imageUri
        if (uri.isNotEmpty()) {
            uploadDiseaseImage(uri)
        }
    }

}

