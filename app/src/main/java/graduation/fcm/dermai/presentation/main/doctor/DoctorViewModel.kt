package graduation.fcm.dermai.presentation.main.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.DoctorResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

    private val _doctorResult = MutableLiveData<ResponseState<DoctorResponse>>()
    val doctorResult: LiveData<ResponseState<DoctorResponse>> = _doctorResult

    private fun getDoctors() {
        networkCall({ repo.getDoctors(0.0, 0.0) }, {
            _doctorResult.value = it
        })
    }


    init {
        getDoctors()
    }

}

