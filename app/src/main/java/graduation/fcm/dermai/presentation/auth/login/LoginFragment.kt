package graduation.fcm.dermai.presentation.auth.login

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
import graduation.fcm.dermai.databinding.FragmentLoginBinding
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var sharedPreferenceManger: SharedPreferenceManger

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        observeData()

    }

    private fun observeData() {
        viewModel.loginLD.observeIfNotHandled {
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
            signUpTv.setOnClickListener { navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()) }

            loginBtn.setOnClickListener {
                getDataFromViews()
            }
        }

    }

    private fun getDataFromViews() {
        val email = binding.emailEd.text.toString()
        val password = binding.passwordEd.text.toString()
        val error = when {
            email.isEmpty() -> "Email can't be empty"
            !email.matches(Regex(EMAIL_REG)) -> "Please enter a valid email"
            password.isEmpty() -> "Password can't be empty"
            else -> ""
        }
        if (error != "") {
            toastMy(error)
            return
        }
        binding.loadingPbView.loadingPb.show()
        viewModel.login(email, password)
    }


}