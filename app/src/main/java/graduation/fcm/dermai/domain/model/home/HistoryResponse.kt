package graduation.fcm.dermai.domain.model.home

import com.google.gson.annotations.SerializedName
import graduation.fcm.dermai.core.BaseResponse

data class HistoryResponse(
    val data: HistoryData
) : BaseResponse()

data class HistoryData(
    @SerializedName("results")
    val history: List<HistoryDisease>
)

data class HistoryDisease(
    val id: Int,
    val disease_id: Int?,
    val image: String,
    val confirmed: Boolean,
    val created_at: String
) {
    fun convertTime(): String {
        return try {
            var date = created_at.split("T")[0]
            date = date.replace("-", "/")

            "Captured in $date"
        } catch (e: Exception) {
            ""
        }
    }
}