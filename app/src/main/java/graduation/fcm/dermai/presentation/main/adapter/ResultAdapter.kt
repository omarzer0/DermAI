package graduation.fcm.dermai.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.common.setImageUsingGlide
import graduation.fcm.dermai.databinding.ItemResultDiseaseBinding
import graduation.fcm.dermai.domain.model.home.Disease

class ResultAdapter : ListAdapter<Disease, ResultAdapter.ResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultDiseaseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class ResultViewHolder(val binding: ItemResultDiseaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Disease) {
            binding.apply {
                setImageUsingGlide(ivDiseaseImage, "")
                diseaseTitleTv.text = currentItem.name
                diseaseDescriptionTv.text = currentItem.description ?: "Don't return null yabny!!!!"
                tvConfirmed.text = when (currentItem.confirmation) {
                    "1" -> "Unconfirmed"
                    else -> "Confirm"
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Disease>() {
        override fun areItemsTheSame(oldItem: Disease, newItem: Disease): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Disease, newItem: Disease): Boolean =
            oldItem == newItem
    }
}