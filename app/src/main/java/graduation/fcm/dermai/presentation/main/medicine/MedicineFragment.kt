package graduation.fcm.dermai.presentation.main.medicine

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
import graduation.fcm.dermai.databinding.FragmentMedicineBinding
import graduation.fcm.dermai.presentation.main.adapter.MedicineAdapter

@AndroidEntryPoint
class MedicineFragment : BaseFragment<FragmentMedicineBinding>() {

    override val viewModel: MedicineViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false

    private val category1Adapter = MedicineAdapter()
    private val category2Adapter = MedicineAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        handleClicks()
        observeData()

    }

    private fun observeData() {
        viewModel.medicineResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    if (it.hasBeenHandled) return@observe

                    it.hasBeenHandled = true
                    it.message?.let { it1 -> toastMy(it1) }
                    binding.loadingPbView.loadingPb.gone()
                    binding.successGroup.gone()

                }
                is ResponseState.Loading -> {
                    binding.loadingPbView.loadingPb.show()
                    binding.successGroup.gone()
                }
                is ResponseState.NotAuthorized -> logOut()
                is ResponseState.Success -> {
                    binding.loadingPbView.loadingPb.gone()
                    val data = it.data?.data ?: return@observe
                    binding.successGroup.show()

                    Log.e("TAGMedicineFragment", "observeData: $data")
                    category1Adapter.submitList(data.diseaseMedicines)
                    category2Adapter.submitList(data.categoryMedicines)
                }
            }
        }
    }

    private fun setUpRv() {
        binding.medicineRvCat1.adapter = category1Adapter
        binding.medicineRvCat2.adapter = category2Adapter
    }

    private fun handleClicks() {

    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMedicineBinding.inflate(inflater, container, false)


}