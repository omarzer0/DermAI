package graduation.fcm.dermai.presentation.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.common.setImageUsingGlide
import graduation.fcm.dermai.databinding.ItemDoctorBinding
import graduation.fcm.dermai.domain.model.home.Doctor

class DoctorAdapter :
    ListAdapter<Doctor, DoctorAdapter.DoctorViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class DoctorViewHolder(val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(currentItem: Doctor) {
            binding.apply {
                val image =
                    if (currentItem.attachment.isEmpty()) "" else currentItem.attachment[0].path
                setImageUsingGlide(doctorIv, image)
                doctorNameTv.text = currentItem.name
                qualificationNameTv.text = currentItem.qualificationName
                doctorDescription.text = currentItem.aboutMe
                doctorLocation.text = currentItem.location
                doctorPrice.text = "${currentItem.price} EGP"
                try {
                    doctorRate.numStars = currentItem.feedback.toInt()
                } catch (e: Exception) {
                    Log.e("DoctorAdapter", "bind: ${e.localizedMessage}")
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Doctor>() {
        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean =
            oldItem == newItem
    }
}