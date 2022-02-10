package graduation.fcm.dermai.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        setUpRv()
        observeData()
    }

    private fun observeData() {

    }

    private fun setUpRv() {

    }

    private fun handleClicks() {
        binding.apply {
            newCaseCv.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToScanFragment()) }
        }
    }


}