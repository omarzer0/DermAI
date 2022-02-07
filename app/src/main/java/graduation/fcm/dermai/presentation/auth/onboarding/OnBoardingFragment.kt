package graduation.fcm.dermai.presentation.auth.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.databinding.FragmentOnboardingBinding

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    //    val viewModel: BaseViewModel by viewModels()
    private lateinit var binding: FragmentOnboardingBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardingBinding.bind(view)
        handleClicks()

    }

    private fun handleClicks() {

    }


}