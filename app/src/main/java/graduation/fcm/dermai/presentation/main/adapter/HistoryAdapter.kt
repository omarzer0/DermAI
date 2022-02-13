package graduation.fcm.dermai.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.common.setImageUsingGlide
import graduation.fcm.dermai.databinding.ItemHomeDiseaseBinding
import graduation.fcm.dermai.domain.model.home.HistoryDisease

class HistoryAdapter(
    val onHistoryClick: (diseaseId:Int) -> Unit
) :
    ListAdapter<HistoryDisease, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHomeDiseaseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class HistoryViewHolder(val binding: ItemHomeDiseaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onHistoryClick(getItem(adapterPosition).id)
            }
        }

        fun bind(currentItem: HistoryDisease) {
            binding.apply {
                setImageUsingGlide(diseaseImageIv, currentItem.image)
                confirmGroup.isVisible = currentItem.confirmed
                capturedAtTv.text = currentItem.convertTime()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<HistoryDisease>() {
        override fun areItemsTheSame(oldItem: HistoryDisease, newItem: HistoryDisease): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HistoryDisease, newItem: HistoryDisease): Boolean =
            oldItem == newItem
    }
}