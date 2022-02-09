package graduation.fcm.dermai.common

sealed class Status {
    data class Success<T>(val message: String, val data: T) : Status()
    data class Error(val message: String?) : Status()
    object Loading : Status()
    data class NotAuthorized(val message: String?) : Status()
    data class TimeOut(val message: String?) : Status()
    data class ServerError(val message: String?) : Status()
    object Empty : Status()
}