package graduation.fcm.dermai.presentation.main.home

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
import graduation.fcm.dermai.databinding.FragmentHomeBinding
import graduation.fcm.dermai.presentation.main.adapter.HistoryAdapter
import graduation.fcm.dermai.presentation.main.adapter.MedicineAdapter
import graduation.fcm.dermai.presentation.main.utils.FromScreen

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    private val medicinesAdapter = MedicineAdapter()

    private val historyAdapter = HistoryAdapter { diseaseId ->
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(
            FromScreen.HOME,
            "",
            diseaseId,
            ""
        )
        navigate(action)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScanHistory()
        viewModel.getMedicine()
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
//                    binding.historyRv.gone()
//                    binding.tvHistory.gone()
                }
                is ResponseState.NotAuthorized -> {
                    logOut()
                }
                is ResponseState.Success -> {
                    binding.historyRv.show()
                    binding.tvHistory.show()
                    val historyResponse = it.data ?: return@observe
                    val historyList = historyResponse.data.history
                    if (historyList.isEmpty()) {
                        binding.historyRv.gone()
                        binding.tvHistory.gone()
                    }
                    historyAdapter.submitList(historyList)
                }
            }
        }


        viewModel.medicineResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    if (it.hasBeenHandled) return@observe

                    it.hasBeenHandled = true
                    it.message?.let { msg -> toastMy(msg) }
                    binding.medicineRv.gone()
                    binding.tvMedicine.gone()
                }
                is ResponseState.Loading -> {}

                is ResponseState.NotAuthorized -> {
                    logOut()
                }
                is ResponseState.Success -> {
                    binding.medicineRv.show()
                    binding.tvMedicine.show()
                    val medicinesResponse = it.data ?: return@observe
                    val medicinesList = medicinesResponse.data.medicines
                    if (medicinesList.isEmpty()) {
                        binding.medicineRv.gone()
                        binding.tvMedicine.gone()
                    }

                    medicinesAdapter.submitList(medicinesList)
                }
            }
        }

    }

    private fun setUpRv() {
        binding.apply {
            historyRv.adapter = historyAdapter
            medicineRv.adapter = medicinesAdapter
        }
    }

    private fun handleClicks() {
        binding.apply {
            goToScanView.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToScanFragment()) }
        }
    }


}