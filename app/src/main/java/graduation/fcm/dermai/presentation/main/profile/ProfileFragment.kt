package graduation.fcm.dermai.presentation.main.profile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
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
                val user = it.data.user
                usernameTv.text = user.name
                emailTv.text = user.email
                genderTv.text = if (user.gender == 0) "Female" else "Male"
                setImageUsingGlide(
                    userImageIv,
                    user.avatar ?: if (user.gender == 0) R.drawable.female else R.drawable.male
                )
                skinColorIv.setBackgroundColor(Color.parseColor(user.skinColor))
                ageTv.text = "${user.age}"
            }
        }

    }

    private fun handleClick() {
        binding.logoutCl.setOnClickListener { logOut() }
    }

}