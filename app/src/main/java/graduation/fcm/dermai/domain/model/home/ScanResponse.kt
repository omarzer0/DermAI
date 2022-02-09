package graduation.fcm.dermai.domain.model.home

import graduation.fcm.dermai.core.BaseResponse

data class ScanResponse(
    val data: ScanData
) : BaseResponse()

data class ScanData(
    val disease: List<Disease>,
//    val precautions: List<Disease>,
//    val symptoms: List<Disease>,
)

data class Disease(
    val id: Int,
    val name: String,
    val description: String,
    val confirmation: Int,
)