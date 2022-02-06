package graduation.fcm.dermai.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {

            Log.e("BaseViewModel", "BaseViewModel exceptionHandler: ${throwable.localizedMessage}")

            val throwableMessage = throwable.localizedMessage
            Log.e("BaseViewModel", "error $throwableMessage")
//            val errorMessage =
//                if (throwableMessage != null && throwableMessage.contains("No address associated with hostname"))
//                    "Check your internet connection!"
//                else UNKNOWN_ERROR
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
                    else -> {

                    }
                }
            }
        }
    }
}