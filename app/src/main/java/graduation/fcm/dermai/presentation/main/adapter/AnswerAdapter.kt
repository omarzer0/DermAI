package graduation.fcm.dermai.presentation.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.databinding.ItemAnswerBinding

class AnswerAdapter(
    val onAnswerChoice: (String) -> Unit
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    private var items: List<String> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun changeItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    inner class AnswerViewHolder(private val binding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onAnswerChoice(items[adapterPosition])
            }
        }

        fun bind(currentItem: String) {
            binding.tvAnswer.text = currentItem
        }
    }

    override fun getItemCount() = items.size
}