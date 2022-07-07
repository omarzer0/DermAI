package graduation.fcm.dermai.presentation.auth.gender

import android.app.Activity
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentGenderBinding
import graduation.fcm.dermai.presentation.auth.adapter.ColorsAdapter
import graduation.fcm.dermai.presentation.auth.gender.GenderViewModel.Gender.FEMALE
import graduation.fcm.dermai.presentation.auth.gender.GenderViewModel.Gender.MALE


@AndroidEntryPoint
class GenderFragment : BaseFragment<FragmentGenderBinding>() {

    override val viewModel: GenderViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        handleSelectedGender()
        handleSelectedColor()
        observeRequest()
    }

    private fun observeRequest() {
        viewModel.userResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    binding.loadingPbView.loadingPb.gone()
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

                    sharedPreferenceManger.hasLoggedIn = true
                    navigateToHomeActivity()
                }
            }
        }
    }

    private fun handleSelectedColor() {
        viewModel.selectedColor.observe(viewLifecycleOwner) {
            binding.skinColorColorView.setBackgroundColor(Color.parseColor(it))
        }
    }

    private fun handleSelectedGender() {
        binding.apply {
            val selectedColor = ContextCompat.getColor(
                requireContext(),
                R.color.mainColor
            )

            val unSelectedColor = ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
            viewModel.selectedGender.observe(viewLifecycleOwner) { gender ->
                if (gender == null) return@observe
                when (gender) {
                    MALE -> {
                        maleIv.strokeColor = ColorStateList.valueOf(selectedColor)
                        maleTv.setTextColor(selectedColor)

                        femaleIv.strokeColor = ColorStateList.valueOf(unSelectedColor)
                        femaleTv.setTextColor(unSelectedColor)
                    }
                    FEMALE -> {
                        maleIv.strokeColor = ColorStateList.valueOf(unSelectedColor)
                        maleTv.setTextColor(unSelectedColor)

                        femaleIv.strokeColor = ColorStateList.valueOf(selectedColor)
                        femaleTv.setTextColor(selectedColor)
                    }
                }
            }

        }
    }

    private fun handleClicks() {
        binding.ivBackBtn.setOnClickListener { findNavController().navigateUp() }
        binding.femaleIv.setOnClickListener { viewModel.updateGender(FEMALE) }
        binding.maleIv.setOnClickListener { viewModel.updateGender(MALE) }
        binding.skinColorRoot.setOnClickListener { showColorDialog(requireActivity()) }
        binding.doneBtn.setOnClickListener {
            val age = binding.ageEd.text.toString()
            if (age.isEmpty()) {
                toastMy("Please enter an age")
                return@setOnClickListener
            }
            try {
                viewModel.updateUserInfo(age.toInt())
            } catch (e: Exception) {
                Log.e("TAG", "handleClicks: ${e.localizedMessage}")
            }
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentGenderBinding.inflate(inflater, container, false)

    private fun showColorDialog(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val colorsRv = dialog.findViewById<RecyclerView>(R.id.colors_rv)
        val colorsAdapter = ColorsAdapter {
            viewModel.updateSelectedColor(it)
            dialog.dismiss()
        }
        colorsRv.adapter = colorsAdapter
        colorsAdapter.changeItems(viewModel.colorsList)
        dialog.show()
    }
}

