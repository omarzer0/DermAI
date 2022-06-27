package graduation.fcm.dermai.presentation.auth.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.common.extentions.hide
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.databinding.FragmentOnboardingBinding
import graduation.fcm.dermai.domain.model.auth.SliderItem
import graduation.fcm.dermai.presentation.auth.adapter.SliderAdapter

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    //    val viewModel: BaseViewModel by viewModels()
    private lateinit var binding: FragmentOnboardingBinding
    private val sliderAdapter = SliderAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardingBinding.bind(view)
        handleClicks()

        setUpSlider()

    }

    private fun setUpSlider() {
        val items = mutableListOf<SliderItem>().apply {
            add(SliderItem(R.drawable.upload, "Scan", "Scan or upload your photo"))
            add(
                SliderItem(
                    R.drawable.analysis,
                    "Analysis",
                    "Your photo will be analyzed and wait for the result"
                )
            )
            add(SliderItem(R.drawable.results, "Result", "Your result will appear"))
            add(
                SliderItem(
                    R.drawable.doctor,
                    "Doctor and Medicines",
                    "Ask for a doctor and you can get medicines"
                )
            )
            add(
                SliderItem(
                    R.drawable.confirm,
                    "Confirm",
                    "See the doctor and you can confirm our result"
                )
            )

            add(SliderItem(R.drawable.launch, "Let's start", ""))
        }

        sliderAdapter.changeItems(items)
        binding.viewPager2.adapter = sliderAdapter

        TabLayoutMediator(binding.tlTabDots, binding.viewPager2) { tab, position ->

        }.attach()

        binding.tlTabDots.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                if (tab.position == items.size - 1) binding.btnStart.show()
                else binding.btnStart.hide()

                Log.e("TAG", "setUpSlider: $tab.position ${items.size - 1}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun handleClicks() {
        binding.btnStart.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
        }
    }


}