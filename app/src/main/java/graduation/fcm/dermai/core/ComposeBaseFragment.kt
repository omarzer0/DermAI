//package graduation.fcm.dermai.core
//
//import android.content.Context
//import android.view.View
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.ComposeView
//import androidx.fragment.app.Fragment
//
//open class ComposeBaseFragment : Fragment() {
//
//    fun setFragmentContent(content: @Composable () -> Unit): View {
//        return setCompContent(requireContext()) {
//            content()
//        }
//    }
//
//}
//
//fun setCompContent(context: Context, content: @Composable () -> Unit): View {
//    return ComposeView(context).apply {
//        setContent {
//            content()
//        }
//    }
//}