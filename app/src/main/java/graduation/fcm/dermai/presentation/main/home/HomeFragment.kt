package graduation.fcm.dermai.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentHomeBinding
import graduation.fcm.dermai.presentation.main.adapter.HistoryAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    private val historyAdapter = HistoryAdapter { diseaseId ->
        val action = HomeFragmentDirections.actionHomeFragmentToScanFragment(diseaseId)
        navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScanHistory()
        handleClicks()
        setUpRv()
        observeData()
    }

    private fun observeData() {
        viewModel.historyResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    if (it.hasBeenHandled) return@observe

                    it.hasBeenHandled = true
                    it.message?.let { msg -> toastMy(msg) }
                    binding.historyRv.gone()
                    binding.tvHistory.gone()
                }
                is ResponseState.Loading -> {
                }
                is ResponseState.NotAuthorized -> {
                    logOut()
                }
                is ResponseState.Success -> {
                    binding.historyRv.show()
                    binding.tvHistory.show()
                    val historyResponse = it.data ?: return@observe
                    val historyList = historyResponse.data.history
                    historyAdapter.submitList(historyList)
                }
            }
        }

    }

    private fun setUpRv() {
        binding.historyRv.adapter = historyAdapter
    }

    private fun handleClicks() {
        binding.apply {
//            newCaseCv.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToScanFragment()) }
            goToScanBtn.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToScanFragment()) }
        }
    }


}