package graduation.fcm.dermai.domain.model.home

import com.google.gson.annotations.SerializedName
import graduation.fcm.dermai.core.BaseResponse

data class MedicineResponse(
    val data: MedicineData
) : BaseResponse()

data class MedicineData(
    @SerializedName("medicines")
    val medicines: List<Medicine>

//    @SerializedName("medicines")
//    val diseaseMedicines: List<Medicine>,
//    @SerializedName("medicines")
//    val categoryMedicines: List<Medicine>
)

data class Medicine(
    val id: Int,
    val name: String,
    @SerializedName("image")
    val rating: Double,
    val price: Double,
//    @SerializedName("image_url")
//    val imageUrl: String,
    @SerializedName("order_url")
    val orderUrl: String,
    val attachment: List<Attachment>
)