package graduation.fcm.dermai.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.Event
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.auth.AuthResponse
import graduation.fcm.dermai.repository.AuthRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: AuthRepositoryImpl) : BaseViewModel() {

    private val _loginMLD = MutableLiveData<Event<AuthResponse>>()
    val loginLD: LiveData<Event<AuthResponse>> = _loginMLD

    fun login(email: String, password: String) {
        safeCallApi({ repo.login(email, password) }, {
            _loginMLD.value = Event(it)
        })
    }

}