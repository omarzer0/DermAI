package graduation.fcm.dermai.presentation.main.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentResultBinding

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override val viewModel: ResultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadingPbView.loadingPb.show()
        handleClicks()
        observeData()
    }

    private fun observeData() {
        viewModel.scanResult.observeIfNotHandled {
            binding.loadingPbView.loadingPb.gone()
            Log.e("ResultFragment", "observeData: $it ")
            if (it.error.isNotEmpty()) {
                toastMy(it.error, true)
                Log.e("ResultFragment", "observeData: ${it.error} ")
                return@observeIfNotHandled
            }
            toastMy("${it.data}", true)
        }
    }


    private fun handleClicks() {

    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentResultBinding.inflate(inflater, container, false)

}