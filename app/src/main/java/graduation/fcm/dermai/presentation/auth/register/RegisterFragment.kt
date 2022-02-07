package graduation.fcm.dermai.presentation.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.EMAIL_REG
import graduation.fcm.dermai.common.SharedPreferenceManger
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentRegisterBinding
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var sharedPreferenceManger: SharedPreferenceManger

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRegisterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        observeData()
    }

    private fun observeData() {
        viewModel.registerLD.observeIfNotHandled {
            binding.loadingPbView.loadingPb.gone()
            if (it.error.isNotEmpty()) {
                toastMy(it.error)
                return@observeIfNotHandled
            }
            Log.e("observeData", "observeData: $it")
            sharedPreferenceManger.token = it.data.token
            sharedPreferenceManger.hasLoggedIn = true
            navigateToHomeActivity()
        }
    }

    private fun handleClicks() {
        binding.apply {
            loginTv.setOnClickListener { navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()) }

            binding.singUpBtn.setOnClickListener { getDataFromViews() }
        }
    }

    private fun getDataFromViews() {
        val name = binding.usernameEd.text.toString()
        val email = binding.emailEd.text.toString()
        val password = binding.passwordEd.text.toString()
        val confirmPassword = binding.confirmPasswordEd.text.toString()
        val error = when {
            name.isEmpty() -> "Name can't be empty"
            email.isEmpty() -> "Email can't be empty"
            !email.matches(Regex(EMAIL_REG)) -> "Please enter a valid email"
            password != confirmPassword -> "Password and confirm password doesn't match"
            else -> ""
        }
        if (error != "") {
            toastMy(error)
            return
        }
        binding.loadingPbView.loadingPb.show()
        viewModel.register(name, email, password)
    }

}