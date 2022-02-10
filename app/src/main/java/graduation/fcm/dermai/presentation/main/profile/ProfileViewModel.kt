package graduation.fcm.dermai.presentation.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.UserData
import graduation.fcm.dermai.domain.model.home.UserResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

    private val _userResult = MutableLiveData<UserResponse>()
    val userResult: LiveData<UserResponse> = _userResult

    fun getUserData() {
        safeCallApi({ repo.getUserData() }, {
            _userResult.value = it
        }, errorResponse = {
            Log.e("TAG", "uploadDiseaseImage: error")
            val errorResponse = UserResponse(UserData("", "", "", "", ""))
            errorResponse.error = it
            _userResult.value = errorResponse
        })
    }

}

