package graduation.fcm.dermai.presentation.main.doctor

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
import graduation.fcm.dermai.databinding.FragmentDoctorBinding
import graduation.fcm.dermai.presentation.main.adapter.DoctorAdapter

@AndroidEntryPoint
class DoctorFragment : BaseFragment<FragmentDoctorBinding>() {

    override val viewModel: DoctorViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false
    private val doctorAdapter = DoctorAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        observeData()

    }

    private fun observeData() {
        viewModel.doctorResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    binding.loadingPbView.loadingPb.gone()
                    if (it.hasBeenHandled) return@observe

                    it.hasBeenHandled = true
                    it.message?.let { msg -> toastMy(msg) }
                }
                is ResponseState.Loading -> {
                    binding.loadingPbView.loadingPb.show()
                }
                is ResponseState.NotAuthorized -> {
                    binding.loadingPbView.loadingPb.gone()
                    logOut()
                }
                is ResponseState.Success -> {
                    binding.loadingPbView.loadingPb.gone()
                    val data = it.data ?: return@observe
                    doctorAdapter.submitList(data.data.doctors)
                    Log.e("TAG", "observeData: ${data.data.doctors}")
                }
            }
        }
    }

    private fun handleClicks() {
        binding.doctorRv.adapter = doctorAdapter
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDoctorBinding.inflate(inflater, container, false)


}