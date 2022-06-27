package graduation.fcm.dermai.domain.model.home

import android.os.Parcelable
import graduation.fcm.dermai.core.BaseResponse
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class ScanResponse(
    val data: ScanData
) : BaseResponse()

data class ScanData(
    val disease: List<Disease>?,
    val diseases: List<Disease>?,
    val history: List<Disease>?,
    val result: Result?
)

data class Result(
    val id: Int,
    val disease_id: Int?,
    val image: String,
    val confirmed: Boolean
)

@Parcelize
data class Disease(
    val id: Int,
    val name: String,
    val description: String?,
    val attachment: @RawValue List<Attachment>,
    val symptoms: @RawValue List<Symptoms>,
    val precautions: @RawValue List<Precautions>
) : Parcelable

data class DiseaseWithResult(
    val disease: Disease,
    val result: Result?
)

data class SearchResult(
    val id: Int,
    val name: String,
    val image: String,
    val disease: Disease
)

@Parcelize
data class Attachment(
    val url: String? = ""
) : Parcelable

@Parcelize
data class Symptoms(
    val id: Int,
    val name: String,
    val description: String
) : Parcelable

@Parcelize
data class Precautions(
    val id: Int,
    val title: String,
    val description: String
) : Parcelable