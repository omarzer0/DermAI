package graduation.fcm.dermai.presentation.main.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.MedicineResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
    private val stateHandle: SavedStateHandle
) : BaseViewModel() {

    private val diseaseId = stateHandle.get<Int>("diseaseId") ?: -1

    private val _medicineResult = MutableLiveData<ResponseState<MedicineResponse>>()
    val medicineResult: LiveData<ResponseState<MedicineResponse>> = _medicineResult

    private fun getMedicine(diseaseId: Int) {
        networkCall({ repo.getMedicines(diseaseId) }, {
            _medicineResult.value = it
        })
    }


    init {
        if (diseaseId != -1) getMedicine(diseaseId)
    }
}

