package graduation.fcm.dermai.core

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import es.dmoral.toasty.Toasty
import graduation.fcm.dermai.common.Event
import graduation.fcm.dermai.common.IS_DEBUG
import graduation.fcm.dermai.common.Status
import graduation.fcm.dermai.presentation.auth.AuthActivity
import graduation.fcm.dermai.presentation.main.MainActivity
import kotlinx.coroutines.flow.collectLatest

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onCreateBinding(inflater, container)
        observeState()
        return binding.root
    }

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    protected fun <T> LiveData<Event<T>>.observeIfNotHandled(result: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                result(it)
            }
        }
    }

    fun toastMy(
        message: String,
        success: Boolean = false,
        hideInRelease: Boolean = false
    ) {
        if (hideInRelease && !IS_DEBUG) return
        if (success) {
            Toasty.success(
                requireContext(), message, Toasty.LENGTH_SHORT, true
            ).show()
        } else {
            Toasty.error(
                requireContext(), message, Toasty.LENGTH_SHORT, true
            ).show()
        }
    }

    fun navigateToHomeActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    fun logOut() {
        startActivity(Intent(requireActivity(), AuthActivity::class.java))
        requireActivity().finish()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.status.collectLatest { state ->
                state.getContentIfNotHandled()?.let {
                    when (it) {
                        is Status.Error -> {
                            if (it.message == null) return@let
                            toastMy(it.message.toString())
                        }
                        is Status.NotAuthorized -> {
                            it.message?.let { msg -> toastMy(msg) }
                            logOut()
                        }
                        is Status.TimeOut -> {
                            it.message?.let { msg -> toastMy(msg) }
                        }

                        is Status.ServerError -> {
                            it.message?.let { msg -> toastMy(msg) }
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}