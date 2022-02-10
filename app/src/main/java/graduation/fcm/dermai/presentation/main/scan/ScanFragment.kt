package graduation.fcm.dermai.presentation.main.scan

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.common.*
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentScanBinding
import javax.inject.Inject


@AndroidEntryPoint
class ScanFragment : BaseFragment<FragmentScanBinding>() {


    override fun selfHandleObserveState(): Boolean = false

    override val viewModel: ScanViewModel by viewModels()
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentScanBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()

        observeData()

    }

    private fun observeData() {
        viewModel.currentImage.observe(viewLifecycleOwner) {
            setImageUsingGlide(binding.chosenImageIv, it.toString())
            binding.uploadImageBtn.show()
            binding.takeImageBtn.text = getString(R.string.select_another_image)
        }
    }

    private fun handleClicks() {
        binding.takeImageBtn.setOnClickListener {
            checkMyPermissions()
        }

        binding.uploadImageBtn.setOnClickListener {
            val uri = viewModel.currentImage.value ?: return@setOnClickListener
            sharedPreferenceManger.imageUri = uri.toString()
            navigate(ScanFragmentDirections.actionScanFragmentToQuestionsFragment())
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val failedToGrant = permissions.entries.any { it.value == false }
            if (failedToGrant) {
                toastMy(getString(R.string.not_granted))
                return@registerForActivityResult
            }

            pickImage(requireContext()) { uri ->
                viewModel.currentImage.value = uri
            }
        }

    private fun checkMyPermissions() {
        activityResultLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

}