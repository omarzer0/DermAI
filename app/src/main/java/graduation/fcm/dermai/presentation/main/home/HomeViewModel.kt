package graduation.fcm.dermai.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.HistoryResponse
import graduation.fcm.dermai.domain.model.home.MedicineResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

    private val _historyResult = MutableLiveData<ResponseState<HistoryResponse>>()
    val historyResult: LiveData<ResponseState<HistoryResponse>> = _historyResult

    private val _medicineResult = MutableLiveData<ResponseState<MedicineResponse>>()
    val medicineResult: LiveData<ResponseState<MedicineResponse>> = _medicineResult

    fun getScanHistory() {
        networkCall({ repo.getScanHistory() }, {
            _historyResult.value = it
        })
    }

    fun getMedicine() {
        networkCall({ repo.getMedicines() }, {
            _medicineResult.value = it
        })
    }

}

