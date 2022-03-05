package graduation.fcm.dermai.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.hide
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.databinding.ItemResultDiseaseBinding
import graduation.fcm.dermai.domain.model.home.Disease
import graduation.fcm.dermai.domain.model.home.DiseaseWithResult

class ResultAdapter(
    val onConfirmClick: (resultID: Int, diseaseID: Int) -> Unit,
    val onMoreClick: (disease: Disease) -> Unit
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
                getItem(adapterPosition).result?.id?.let { result ->
                    onConfirmClick(
                        result,
                        getItem(adapterPosition).disease.id
                    )
                }
            }

            binding.tvMoreDetails.setOnClickListener { onMoreClick(getItem(adapterPosition).disease) }
        }

        fun bind(currentItem: DiseaseWithResult) {
            binding.apply {
//                setUpImageSlider(binding, currentItem.disease)

                val adapter = ImageSliderAdapter()
                sliderRv.adapter = adapter

                val urlList = currentItem.disease.attachment.mapNotNull { it.url }
                adapter.changeItems(urlList)

                diseaseTitleTv.text = currentItem.disease.name
                diseaseDescriptionTv.text =
                    currentItem.disease.description ?: "Don't return null yabny!!!!"

                if (currentItem.result != null) {
                    val diseaseId = currentItem.result.disease_id
                    if (diseaseId == null) {
                        tvConfirmed.text = "Confirm"
                    } else {
                        if (currentItem.disease.id == diseaseId) tvConfirmed.text = "Unconfirmed"
                        else tvConfirmed.hide()
                    }
                } else {
                    tvConfirmed.hide()
                }
            }
        }
    }

//    private fun setUpImageSlider(binding: ItemResultDiseaseBinding, currentItem: Disease) {
//        val attachments = currentItem.attachment
//        val urlList = attachments.map { it.url }
//        binding.sliderTextContainer.isVisible = urlList.isNotEmpty()
//        val imageSliderAdapter = ImageSliderAdapter()
//        binding.imageViewPager.adapter = imageSliderAdapter
//        imageSliderAdapter.changeItems(urlList)
//
//        TabLayoutMediator(
//            binding.tabSliderMediatorTl,
//            binding.imageViewPager
//        ) { tab, position ->
//            Log.e("TAG", "bind: $position")
//            binding.sliderCounterTv.text = "1/${urlList.size}"
//        }.attach()
//
//        binding.tabSliderMediatorTl.addOnTabSelectedListener(object :
//            TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Log.e("TAG", "bind: ${tab?.text} ${tab?.position}")
//                val position = tab?.position ?: return
//                binding.sliderCounterTv.text = "${position + 1}/${urlList.size}"
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//        })
//    }

    class DiffCallback : DiffUtil.ItemCallback<DiseaseWithResult>() {
        override fun areItemsTheSame(oldItem: DiseaseWithResult, newItem: DiseaseWithResult):
                Boolean = oldItem.disease.id == newItem.disease.id

        override fun areContentsTheSame(oldItem: DiseaseWithResult, newItem: DiseaseWithResult):
                Boolean = oldItem == newItem
    }
}