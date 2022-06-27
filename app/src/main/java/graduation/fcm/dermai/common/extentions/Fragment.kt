package graduation.fcm.dermai.common.extentions


import androidx.fragment.app.Fragment
import graduation.fcm.dermai.common.extentions.hideKeyboard

fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}
