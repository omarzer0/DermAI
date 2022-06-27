package graduation.fcm.dermai.common

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import es.dmoral.toasty.Toasty
import graduation.fcm.dermai.R
import graduation.fcm.dermai.core.BaseResponse
import graduation.fcm.dermai.di.ApplicationScope
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

fun toastMy(
    context: Context,
    message: String,
    success: Boolean = false,
    hideInRelease: Boolean = false
) {
    if (hideInRelease && !IS_DEBUG) return
    if (success) {
        Toasty.success(
            context, message, Toasty.LENGTH_SHORT, true
        ).show()
    } else {
        Toasty.error(
            context, message, Toasty.LENGTH_SHORT, true
        ).show()
    }
}

fun getImageAsMultipartBodyPart(
    context: Context?,
    uri: Uri,
    name: String
): MultipartBody.Part {
    val path: String = RealPathUtil.getRealPath(context, uri)
    val file = File(path)
    val reqFileSelect = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, file.name, reqFileSelect)
}

fun getShimmerDrawable(): ShimmerDrawable {
    val shimmer =
        Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

    return ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}

fun setImageUsingGlide(view: ImageView, imageUrl: String?) {
    try {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(getShimmerDrawable())
            .error(R.drawable.ic_no_image)
            .into(view)
    } catch (e: Exception) {
        Log.e("Utils glide", "setImageUsingGlide ${e.localizedMessage}")
    }
}

fun pickImage(context: Context, action: (Uri) -> Unit) {
    TedImagePicker.with(context)
        .title("Choose image")
        .backButton(R.drawable.ic_arrow_back_black_24dp)
        .showCameraTile(true)
        .buttonBackground(R.drawable.btn_done_button)
        .buttonTextColor(R.color.white)
        .buttonText("Choose image")
        .errorListener { throwable ->
            Log.e(
                "pickImage",
                throwable.localizedMessage ?: "pickImage"
            )
        }
        .start { uri ->
            action(uri)
        }
}

fun <T : BaseResponse> networkCallGlobal(
    scope: CoroutineScope,
    action: suspend () -> Response<T>,
    onReply: ((ResponseState<T>) -> Unit)? = null,
    showLoading: Boolean = true
) {
    scope.launch {
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


