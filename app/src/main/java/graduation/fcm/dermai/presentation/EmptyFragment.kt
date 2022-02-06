package graduation.fcm.dermai.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.databinding.FragmentEmptyBinding

@AndroidEntryPoint
class EmptyFragment : Fragment(R.layout.fragment_empty) {

    val viewModel: BaseViewModel by viewModels()
    private lateinit var binding: FragmentEmptyBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmptyBinding.bind(view)
        handleClicks()

    }

    private fun handleClicks() {

    }


}