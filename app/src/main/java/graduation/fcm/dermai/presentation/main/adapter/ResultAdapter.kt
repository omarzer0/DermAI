package graduation.fcm.dermai.presentation.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import graduation.fcm.dermai.common.extentions.hide
import graduation.fcm.dermai.databinding.ItemResultDiseaseBinding
import graduation.fcm.dermai.domain.model.home.Disease
import graduation.fcm.dermai.domain.model.home.DiseaseWithResult

class ResultAdapter(
    val onConfirmClick: (resultID: Int, diseaseID: Int) -> Unit
) :
    ListAdapter<DiseaseWithResult, ResultAdapter.ResultViewHolder>(DiffCallback()) {

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

        init {
            binding.tvConfirmed.setOnClickListener {
                onConfirmClick(
                    getItem(adapterPosition).result.id,
                    getItem(adapterPosition).disease.id
                )
            }
        }

        fun bind(currentItem: DiseaseWithResult) {
            binding.apply {
                setUpImageSlider(binding, currentItem.disease)

                diseaseTitleTv.text = currentItem.disease.name
                diseaseDescriptionTv.text =
                    currentItem.disease.description ?: "Don't return null yabny!!!!"
                val diseaseId = currentItem.result.disease_id
                if (diseaseId == null) {
                    tvConfirmed.text = "Confirm"
                } else {
                    if (currentItem.disease.id == diseaseId) tvConfirmed.text = "Unconfirmed"
                    else tvConfirmed.hide()
                }
            }
        }
    }

    private fun setUpImageSlider(binding: ItemResultDiseaseBinding, currentItem: Disease) {
        val attachments = currentItem.attachment
        val urlList = attachments.map { it.url }
        binding.sliderTextContainer.isVisible = urlList.isNotEmpty()
        val imageSliderAdapter = ImageSliderAdapter()
        binding.imageViewPager.adapter = imageSliderAdapter
        imageSliderAdapter.changeItems(urlList)

        TabLayoutMediator(
            binding.tabSliderMediatorTl,
            binding.imageViewPager
        ) { tab, position ->
            Log.e("TAG", "bind: $position")
            binding.sliderCounterTv.text = "1/${urlList.size}"
        }.attach()

        binding.tabSliderMediatorTl.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.e("TAG", "bind: ${tab?.text} ${tab?.position}")
                val position = tab?.position ?: return
                binding.sliderCounterTv.text = "${position + 1}/${urlList.size}"
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    class DiffCallback : DiffUtil.ItemCallback<DiseaseWithResult>() {
        override fun areItemsTheSame(oldItem: DiseaseWithResult, newItem: DiseaseWithResult):
                Boolean = oldItem.disease.id == newItem.disease.id

        override fun areContentsTheSame(oldItem: DiseaseWithResult, newItem: DiseaseWithResult):
                Boolean = oldItem == newItem
    }
}