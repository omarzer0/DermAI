package graduation.fcm.dermai.presentation.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentDetailsBinding
import graduation.fcm.dermai.domain.model.home.Disease
import graduation.fcm.dermai.presentation.main.adapter.ImageDetailsSliderAdapter

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    override val viewModel: DetailsViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false
    private val imageSliderAdapter = ImageDetailsSliderAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        observeData()

    }

    private fun observeData() {
        viewModel.disease.observe(viewLifecycleOwner) {
            binding.apply {
                titleTv.text = it.name
                descriptionDiseaseTv.text = it.description
                it.symptoms.map { "${it.name}:\n- ${it.description}" }
                    .forEachIndexed { index, it ->
                        if (index == 0)
                            symptomsDescriptionTv.text = "${it.capitalize()}"
                        else
                            symptomsDescriptionTv.text =
                                "${symptomsDescriptionTv.text}\n\n${it.capitalize()}"
                    }

                it.precautions.map { "${it.title}:\n- ${it.description}" }
                    .forEachIndexed { index, it ->
                        if (index == 0)
                            precautionsDescriptionTv.text = "${it.capitalize()}"
                        else
                            precautionsDescriptionTv.text =
                                "${precautionsDescriptionTv.text}\n\n${it.capitalize()}"
                    }

                setUpImageSlider(binding, it)

            }
        }
    }

    private fun handleClicks() {
        binding.getMedicineBtn.setOnClickListener {
            val diseaseId = viewModel.disease.value?.id ?: return@setOnClickListener
            navigate(DetailsFragmentDirections.actionDetailsFragmentToMedicineFragment(diseaseId))
        }

        binding.getDoctorBtn.setOnClickListener {
            navigate(DetailsFragmentDirections.actionDetailsFragmentToDoctorFragment())
        }
    }

    private fun setUpImageSlider(binding: FragmentDetailsBinding, currentItem: Disease) {
        val attachments = currentItem.attachment
        val urlList = attachments.mapNotNull { it.url }
        binding.sliderTextContainer.isVisible = urlList.isNotEmpty()
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


    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailsBinding.inflate(inflater, container, false)


}