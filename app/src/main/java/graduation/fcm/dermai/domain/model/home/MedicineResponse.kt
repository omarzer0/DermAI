package graduation.fcm.dermai.domain.model.home

import com.google.gson.annotations.SerializedName
import graduation.fcm.dermai.core.BaseResponse

data class MedicineResponse(
    val data: MedicineData
) : BaseResponse()

data class MedicineData(
    @SerializedName("disease_medicines")
    val diseaseMedicines: List<Medicine>,
    @SerializedName("category_medicines")
    val categoryMedicines: List<Medicine>
)

data class Medicine(
    val id: Int,
    val name: String,
    @SerializedName("image")
    val rating: Double,
    val price: Double,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("order_url")
    val orderUrl: String
)