package graduation.fcm.dermai.presentation.auth.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.databinding.ItemColorBinding
import graduation.fcm.dermai.domain.model.auth.ColorItem

class ColorsAdapter(
    val onColorClick: (color: String) -> Unit
) : RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {

    private var items: List<ColorItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun changeItems(newItems: List<ColorItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val binding = ItemColorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ColorsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }


    override fun getItemCount() = items.size

    inner class ColorsViewHolder(private val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onColorClick(items[adapterPosition].color)
            }
        }

        fun bind(currentItem: ColorItem) {
            binding.root.setBackgroundColor(Color.parseColor(currentItem.color))
        }
    }

}