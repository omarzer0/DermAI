package graduation.fcm.dermai.presentation.main.scan

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.Event
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.ScanResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

    val currentImage = MutableLiveData<Uri>()


}

