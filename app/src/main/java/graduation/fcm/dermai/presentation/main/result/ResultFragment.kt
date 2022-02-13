package graduation.fcm.dermai.presentation.main.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentResultBinding
import graduation.fcm.dermai.domain.model.home.DiseaseWithResult
import graduation.fcm.dermai.presentation.main.adapter.ResultAdapter

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentResultBinding.inflate(inflater, container, false)

    override fun selfHandleObserveState(): Boolean = true

    override val viewModel: ResultViewModel by viewModels()
    private val resultAdapter = ResultAdapter(onConfirmClick = { resultID, diseaseID ->
        viewModel.confirmOrUnConfirm(resultID, diseaseID)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        handleClicks()
        observeData()
    }


    private fun setUpRv() {
        binding.resultsRv.adapter = resultAdapter
    }

    private fun observeData() {
        viewModel.scanResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    if (it.hasBeenHandled) return@observe

                    it.hasBeenHandled = true
                    showOrHide(loading = false, success = false)
                }
                is ResponseState.Loading -> {
                    showOrHide(true, success = false)
                }
                is ResponseState.NotAuthorized -> {
                    logOut()
                }
                is ResponseState.Success -> {
                    showOrHide(loading = false, success = true)
                    val data = it.data ?: return@observe
                    Log.e("TAG", "observeData: $it")
                    val diseaseWithResult = data.data.disease.map { disease ->
                        DiseaseWithResult(disease, data.data.result)
                    }
                    resultAdapter.submitList(diseaseWithResult)
                }
            }
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