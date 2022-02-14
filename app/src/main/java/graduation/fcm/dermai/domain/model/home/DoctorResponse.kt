package graduation.fcm.dermai.domain.model.home

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
    val specialization: String,
    val location: String,
    val avatar_url: String,
    val contact_url: String,
    val feedback: Double,
    val price: Double,
)
