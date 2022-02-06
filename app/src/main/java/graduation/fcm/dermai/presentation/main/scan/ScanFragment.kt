package graduation.fcm.dermai.presentation.main.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.databinding.FragmentScanBinding

@AndroidEntryPoint
class ScanFragment : Fragment(R.layout.fragment_scan) {

    val viewModel: BaseViewModel by viewModels()
    private lateinit var binding: FragmentScanBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScanBinding.bind(view)
        handleClicks()

    }

    private fun handleClicks() {

    }


}