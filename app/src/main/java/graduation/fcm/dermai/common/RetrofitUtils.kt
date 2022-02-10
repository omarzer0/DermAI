package graduation.fcm.dermai.common

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitUtils {

    fun <T> safeCall(
        request: Call<T>,
        onSuccess: (T) -> Unit,
        onFailure: (String, ResponseType) -> Unit
    ) {
        request.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when {
                    response.isSuccessful -> {
                        val body = response.body() ?: return
                        onSuccess(body)
                    }
                    response.code() == 401 -> {
                        Log.e("RetrofitUtils", "onResponse: Un Authorized")
                        onFailure("", ResponseType.AU_AUTHORIZED)
                    }
                    else -> {
                        Log.e("RetrofitUtils", "onResponse: Unknown")
                        onFailure("Something went wrong please try again!", ResponseType.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                Log.e("RetrofitUtils", "onResponse: failed ${throwable.localizedMessage}")
                val throwableMessage = throwable.localizedMessage
                if (throwableMessage != null && throwableMessage.contains("No address associated with hostname")) {
                    onFailure("Please Check Internet connection!", ResponseType.FAIL)
                } else {
                    Log.e("RetrofitUtils", "onResponse: Unknown")
                    onFailure("Something went wrong please try again!", ResponseType.FAIL)
                }
            }
        })
    }

}

enum class ResponseType {
    FAIL, AU_AUTHORIZED
}