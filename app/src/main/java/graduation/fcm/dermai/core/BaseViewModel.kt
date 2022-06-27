package graduation.fcm.dermai.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.*
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

    private fun exceptionHandler(
        errorResponse: ((String) -> Unit)? = null
    ) = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {

            Log.e("BaseViewModel", "BaseViewModel exceptionHandler: ${throwable.localizedMessage}")

            val throwableMessage = throwable.localizedMessage
            Log.e("BaseViewModel", "error $throwableMessage")
            val errorMessage =
                if (throwableMessage != null && throwableMessage.contains("No address associated with hostname"))
                    "Check your internet connection!"
                else UNKNOWN_ERROR

            _status.value = Event(Status.Error(errorMessage))
            errorResponse?.invoke(errorMessage)
        }
    }

    protected fun <T : BaseResponse> networkCall(
        action: suspend () -> Response<T>,
        onReply: ((ResponseState<T>) -> Unit)? = null,
        showLoading: Boolean = true
    ) {
        viewModelScope.launch {
            if (showLoading) onReply?.invoke(ResponseState.Loading())
            try {
                val response = action()
                if (response.isSuccessful) {
                    val body = response.body() ?: return@launch
                    if (body.error.isNotEmpty()) {
                        // error from api
                        onReply?.invoke(ResponseState.Error(body.error))
                        Log.e("networkCall", "error ${body.error}")
                    } else {
                        // success
                        onReply?.invoke(ResponseState.Success(body))
                        Log.e("networkCall", "success ${response.code()} $body")
                    }

                } else {
                    when (response.code()) {
                        401 -> {
                            onReply?.invoke(ResponseState.NotAuthorized(NOT_AUTHORIZED_ERROR))
                            Log.e("networkCall", "error ${response.code()} not auth")
                        }
                        else -> {
                            onReply?.invoke(ResponseState.Error(UNKNOWN_ERROR))
                            Log.e("networkCall", "error ${response.code()} ${response.errorBody()}")
                        }
                    }
                }
            } catch (e: Exception) {
                // network error
                onReply?.invoke(ResponseState.Error(NETWORK_ERROR))
                Log.e("networkCall", "network error ${e.localizedMessage}")
            }
        }
    }

    // _historyResult.value = ResponseState.Loading()
    //        try {
    //            val response = repo.getScanHistory()
    //            if (response.isSuccessful) {
    //                val body = response.body() ?: throw Exception("Empty body!")
    //                if (body.error.isNotEmpty()) {
    //                    // error
    //                    Log.e("SimpleHandle", "error: ${body.error}")
    //                    _historyResult.value = ResponseState.Error(body.error)
    //                } else {
    //                    _historyResult.value = ResponseState.Success(body)
    //                }
    //
    //            } else {
    //                Log.e("SimpleHandle", "error: ${response.code()} ${response.message()}")
    //                when (response.code()) {
    //                    401 -> {
    //                        _historyResult.value =
    //                            ResponseState.NotAuthorized(NOT_AUTHORIZED_ERROR)
    //                    }
    //                    else -> {
    //                        _historyResult.value =
    //                            ResponseState.NotAuthorized(NOT_AUTHORIZED_ERROR)
    //                    }
    //                }
    //            }
    //
    //        } catch (e: Exception) {
    //            Log.e("SimpleHandle", "error: ${e.localizedMessage}")
    //            //Network error
    //            _historyResult.value = ResponseState.Error(NETWORK_ERROR)
    //        }


    protected fun <T> safeCallApi(
        action: suspend () -> Response<T>,
        response: (T) -> Unit,
        errorResponse: ((String) -> Unit)? = null,
    ) {
        viewModelScope.launch(exceptionHandler(errorResponse)) {
            _status.value = Event(Status.Loading)

            Log.e("safeCallApi", "loading...")
            val callResponse: Response<T> = action()

            if (callResponse.isSuccessful) {
                val code = callResponse.code()
                Log.e("BaseViewModel", "BaseViewModel safeCallApi - if: $code")
                // returns the value of the api call
                callResponse.body()?.let {
                    response(it)
                    _status.value = Event(Status.Success<T>("", it))
                }

            } else {
                Log.e(
                    "BaseViewModel",
                    "BaseViewModel safeCallApi - else: ${callResponse.code()} ${callResponse.message()}"
                )
                when (callResponse.code()) {
                    401 -> {
                        _status.value =
                            Event(Status.NotAuthorized("Session expired please login again"))
                        errorResponse?.invoke("")
                    }
                    404 -> {

                    }
                    405 -> {
                        _status.value = Event(Status.Error("Method not allowed"))
                        errorResponse?.invoke("")
                    }

                    408 -> {
                        _status.value = Event(Status.TimeOut("Time out please try again!"))
                        errorResponse?.invoke("Time out please try again!")
                    }

                    in 500..599 -> {
                        _status.value =
                            Event(Status.ServerError("Server is down please try again later"))
                        errorResponse?.invoke("Server is down please try again later")

                    }
                    else -> {

                    }
                }
            }
        }
    }
}