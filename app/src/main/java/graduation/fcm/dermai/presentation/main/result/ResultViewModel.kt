package graduation.fcm.dermai.presentation.main.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.Event
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

    private val _scanResult = MutableLiveData<Event<ScanResponse>>()
    val scanResult: LiveData<Event<ScanResponse>> = _scanResult

    private fun uploadDiseaseImage(uriString: String) {
        safeCallApi({
            repo.uploadDiseaseImage(uriString)
        }, {
            _scanResult.value = Event(it)
        })
    }

    init {
        val uri = sharedPreferenceManger.imageUri
        if (uri.isNotEmpty()) {
            uploadDiseaseImage(uri)
        }
        //TODO handle else
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferenceManger.imageUri = ""
    }
}

