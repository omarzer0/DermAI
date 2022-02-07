package graduation.fcm.dermai.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.Event
import graduation.fcm.dermai.common.Status
import graduation.fcm.dermai.common.UNKNOWN_ERROR
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _status = MutableStateFlow<Event<Status>>(Event(Status.Empty))
    val status: StateFlow<Event<Status>>
        get() = _status

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {

            Log.e("BaseViewModel", "BaseViewModel exceptionHandler: ${throwable.localizedMessage}")

            val throwableMessage = throwable.localizedMessage
            Log.e("BaseViewModel", "error $throwableMessage")
            val errorMessage =
                if (throwableMessage != null && throwableMessage.contains("No address associated with hostname"))
                    "Check your internet connection!"
                else UNKNOWN_ERROR

            _status.value = Event(Status.Error(errorMessage))
        }
    }

    protected fun <T> safeCallApi(
        action: suspend () -> Response<T>,
        response: (T) -> Unit,
    ) {
        viewModelScope.launch(exceptionHandler) {

            val callResponse: Response<T> = action()

            if (callResponse.isSuccessful) {
                val code = callResponse.code()
                Log.e("BaseViewModel", "BaseViewModel safeCallApi - if: $code")
                // returns the value of the api call
                callResponse.body()?.let { response(it) }

            } else {
                Log.e(
                    "BaseViewModel",
                    "BaseViewModel safeCallApi - else: ${callResponse.code()} ${callResponse.message()}"
                )
                when (callResponse.code()) {
                    401 -> {

                    }
                    404 -> {

                    }
                    405 -> {
                        _status.value = Event(Status.Error("Method not allowed"))
                    }
                    else -> {

                    }
                }
            }
        }
    }
}