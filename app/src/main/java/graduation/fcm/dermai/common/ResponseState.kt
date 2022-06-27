package graduation.fcm.dermai.common

// Generic sealed class wrapper for network calls
// differentiate between success and failure responses
// and for handling loading data states
// takes the body (data) of nullable generic type and nullable String response message
sealed class ResponseState<T>(val data: T? = null, val message: String? = null) {

    // Success class returns a non nullable data which was fetched from retrofit
    // defines that the response was correct and data was sent
    class Success<T>(data: T) : ResponseState<T>(data)

    // Error class returns a non nullable message which defines what when wrong
    // optionally can take data as a pram if needed
    class Error<T>(message: String, data: T? = null, var hasBeenHandled: Boolean = false) :
        ResponseState<T>(data, message)

    class NotAuthorized<T>(message: String, data: T? = null, var hasBeenHandled: Boolean = false) :
        ResponseState<T>(data, message)

    // Loading class notifies when the call was done and response was received (success or error)
    class Loading<T> : ResponseState<T>()
}