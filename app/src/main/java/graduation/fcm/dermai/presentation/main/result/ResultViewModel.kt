package graduation.fcm.dermai.presentation.main.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.common.SharedPreferenceManger
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.ScanResponse
import graduation.fcm.dermai.presentation.main.utils.FromScreen
import graduation.fcm.dermai.presentation.main.utils.FromScreen.*
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
    private val sharedPreferenceManger: SharedPreferenceManger,
    private val stateHandle: SavedStateHandle
) : BaseViewModel() {

    private val fromScreen = stateHandle.get<FromScreen>("fromScreen") ?: SCAN
    private val diseaseId = stateHandle.get<Int>("diseaseId") ?: -1
    private val searchQuery = stateHandle.get<String>("searchQuery") ?: ""
    private val imgUrl = stateHandle.get<String>("imgUrl") ?: ""
    val shouldSave = fromScreen == SEARCH

    fun addToSearchHistory(diseaseId: Int) {
        Log.e("addToSearchHistory", "addToSearchHistory: isSearch: ${fromScreen == SEARCH}")
        if (fromScreen != SEARCH) return
        networkCall({ repo.addToSearchHistory(diseaseId) }, {})
    }

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
        })
    }

    private fun getSingleScanHistory(diseaseId: Int) {
        networkCall({ repo.getSingleScanHistory(diseaseId) }, {
            _scanResult.value = it
        })
    }

    private fun getDiseaseSearchResult(searchQuery: String) {
        networkCall({ repo.getDiseaseSearchResult(searchQuery) }, {
            _scanResult.value = it
        })
    }

    init {
        when (fromScreen) {
            HOME -> {
                if (diseaseId != -1) getSingleScanHistory(diseaseId)
            }
            SCAN -> {
                if (imgUrl.isNotEmpty()) uploadDiseaseImage(imgUrl)
            }
            SEARCH -> {
                if (searchQuery.isNotEmpty()) getDiseaseSearchResult(searchQuery)
            }
            SEARCH_HISTORY -> {

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferenceManger.imageUri = ""
    }

}

