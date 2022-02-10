package graduation.fcm.dermai.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.Event
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.auth.AuthData
import graduation.fcm.dermai.domain.model.auth.AuthResponse
import graduation.fcm.dermai.repository.AuthRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo: AuthRepositoryImpl) :
    BaseViewModel() {

    private val _registerMLD = MutableLiveData<Event<AuthResponse>>()
    val registerLD: LiveData<Event<AuthResponse>> = _registerMLD

    fun register(name: String, email: String, password: String) {
        safeCallApi({ repo.register(name, email, password) }, {
            _registerMLD.value = Event(it)
        }, errorResponse = {
            val errorResponse = AuthResponse(AuthData(""))
            errorResponse.error = it
            _registerMLD.value = Event(errorResponse)
        })
    }

}