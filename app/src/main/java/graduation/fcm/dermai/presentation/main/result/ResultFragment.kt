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
import graduation.fcm.dermai.presentation.main.adapter.ResultAdapter

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentResultBinding.inflate(inflater, container, false)

    override fun selfHandleObserveState(): Boolean = true

    override val viewModel: ResultViewModel by viewModels()
    private val resultAdapter = ResultAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        handleClicks()
        observeData()
        showOrHide(loading = true, success = false)
    }


    private fun setUpRv() {
        binding.resultsRv.adapter = resultAdapter
    }

    private fun observeData() {
        Log.e("ResultFragment", "observeData:")
        viewModel.scanResult.observe(viewLifecycleOwner) {
            if (it.error.isNotEmpty()) {
                if (!viewModel.errorHandled) toastMy(it.error, true)
                showOrHide(loading = false, success = false)
                viewModel.errorHandled = true
                return@observe
            }
            Log.e("TAG", "the list is: ${it.data.disease} ")
            showOrHide(loading = false, success = true)
            resultAdapter.submitList(it.data.disease)
        }
    }


    private fun handleClicks() {

    }

    private fun showOrHide(loading: Boolean, success: Boolean) {
        when {
            loading -> {
                Log.e("ResultFragment", "loading...")
                binding.loadingGroup.show()
                binding.successGroup.gone()
                binding.failGroup.gone()
            }
            success -> {
                Log.e("ResultFragment", "success...")
                binding.loadingGroup.gone()
                binding.successGroup.show()
                binding.failGroup.gone()
            }
            else -> {
                Log.e("ResultFragment", "failed...")
                binding.loadingGroup.gone()
                binding.successGroup.gone()
                binding.failGroup.show()
            }
        }
    }

}