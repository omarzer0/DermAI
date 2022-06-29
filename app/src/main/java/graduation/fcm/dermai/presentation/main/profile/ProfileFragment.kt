package graduation.fcm.dermai.presentation.main.profile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.setImageUsingGlide
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = true
    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProfileBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        observeData()
        handleClick()
    }

    private fun observeData() {
        viewModel.userResult.observe(viewLifecycleOwner) {
            if (it.error.isNotEmpty()) {
                toastMy(it.error)
                return@observe
            }

            Log.e("ProfileFragment", "$it")
            binding.apply {
                usernameTv.text = it.data.user.name
                emailTv.text = it.data.user.email
                setImageUsingGlide(userImageIv, it.data.user.avatar)
                skinTypeTv.text = "Normal"
                skinColorIv.setBackgroundColor(Color.parseColor("#E8BEAC"))
                ageTv.text = "21"
            }
        }

    }

    private fun handleClick() {
        binding.logoutCl.setOnClickListener { logOut() }
    }

}