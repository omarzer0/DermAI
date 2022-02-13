package graduation.fcm.dermai.presentation.main.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.common.SharedPreferenceManger
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.ScanResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
    private val sharedPreferenceManger: SharedPreferenceManger
) : BaseViewModel() {

    private val _scanResult = MutableLiveData<ResponseState<ScanResponse>>()
    val scanResult: LiveData<ResponseState<ScanResponse>> = _scanResult

    private fun uploadDiseaseImage(uriString: String) {
        networkCall({ repo.uploadDiseaseImage(uriString) }, {
            _scanResult.value = it
        })
    }

    fun confirmOrUnConfirm(resultId: Int, diseaseId: Int) {
        networkCall({ repo.confirmOrUnConfirm(resultId, diseaseId) }, {
            _scanResult.value = it
        }, showLoading = false)
    }

    init {
        val uri = sharedPreferenceManger.imageUri
        if (uri.isNotEmpty()) {
            uploadDiseaseImage(uri)
        }
    }

}

