package graduation.fcm.dermai.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.databinding.FragmentEmptyBinding

@AndroidEntryPoint
class EmptyFragment : BaseFragment<FragmentEmptyBinding>() {

    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()

    }

    private fun handleClicks() {

    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEmptyBinding.inflate(inflater, container, false)

}