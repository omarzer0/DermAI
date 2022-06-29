package graduation.fcm.dermai.domain.model.home

import com.google.gson.annotations.SerializedName
import graduation.fcm.dermai.core.BaseResponse

data class DoctorResponse(
    val data: DoctorData
) : BaseResponse()

data class DoctorData(
    val doctors: List<Doctor>
)

data class Doctor(
    val id: Int,
    val name: String,
    @SerializedName("about_me")
    val aboutMe: String,
    val location: String,
    @SerializedName("veezeta_url")
    val avatar_url: String,
    val contact_url: String,
    val feedback: Double,
    val price: Double,
    @SerializedName("qualification_name")
    val qualificationName:String
)
