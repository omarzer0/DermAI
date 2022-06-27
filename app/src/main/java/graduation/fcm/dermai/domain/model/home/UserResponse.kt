package graduation.fcm.dermai.domain.model.home

import com.google.gson.annotations.SerializedName
import graduation.fcm.dermai.core.BaseResponse

data class UserResponse(
    val data: UserData
) : BaseResponse()

data class UserData(
    val name: String,
    val email: String,
    val gender: String,
    @SerializedName("skin_color")
    val skinColor: String?,
    val avatar: String
)