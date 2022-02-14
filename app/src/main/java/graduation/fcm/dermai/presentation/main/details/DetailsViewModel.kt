package graduation.fcm.dermai.presentation.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.Disease
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _disease = stateHandle.getLiveData<Disease>("disease")
    val disease: LiveData<Disease> = _disease

}

