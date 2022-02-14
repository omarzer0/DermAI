package graduation.fcm.dermai.presentation.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.common.setImageUsingGlide
import graduation.fcm.dermai.databinding.ItemSearchDiseaseBinding
import graduation.fcm.dermai.domain.model.home.SearchResult

class SearchHistoryAdapter :
    ListAdapter<SearchResult, SearchHistoryAdapter.SearchHistoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val binding = ItemSearchDiseaseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class SearchHistoryViewHolder(val binding: ItemSearchDiseaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: SearchResult) {
            binding.apply {
                Log.e("searchResults", "bind: $currentItem")
                // TODO use the real image
                setImageUsingGlide(diseaseImageIv, "")
                diseaseNameTv.text = currentItem.name
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
            oldItem == newItem
    }
}