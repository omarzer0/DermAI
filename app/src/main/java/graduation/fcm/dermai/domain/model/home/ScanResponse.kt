package graduation.fcm.dermai.domain.model.home

import graduation.fcm.dermai.core.BaseResponse

data class ScanResponse(
    val data: ScanData
) : BaseResponse()

data class ScanData(
    val disease: List<Disease>,
    val result: Result
)

data class Result(
    val id: Int,
    val disease_id: Int?,
    val image: String,
    val confirmed: Boolean
)

data class Disease(
    val id: Int,
    val name: String,
    val description: String?,
//    val confirmation: String,
    val attachment: List<Attachment>
)

data class DiseaseWithResult(
    val disease: Disease,
    val result: Result
)

data class Attachment(
    val url: String
)