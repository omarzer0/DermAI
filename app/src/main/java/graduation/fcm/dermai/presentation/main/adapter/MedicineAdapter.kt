package graduation.fcm.dermai.presentation.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.common.setImageUsingGlide
import graduation.fcm.dermai.databinding.ItemMedicineBinding
import graduation.fcm.dermai.domain.model.home.Medicine

class MedicineAdapter :
    ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class MedicineViewHolder(val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Medicine) {
            binding.apply {
                val attachment =
                    if (currentItem.attachment.isEmpty()) "" else currentItem.attachment[0].path
                setImageUsingGlide(medicineIv, attachment)
                medicineNameTv.text = currentItem.name
                priceTv.text = "${currentItem.price} EGP"
                try {
                    medicineRb.numStars = (currentItem.rating.toInt() % 5)
                } catch (e: Exception) {
                    Log.e("MedicineAdapter", "bind: ${e.localizedMessage}")
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean =
            oldItem == newItem
    }
}