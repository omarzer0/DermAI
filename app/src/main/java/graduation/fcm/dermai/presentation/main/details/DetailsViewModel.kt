package graduation.fcm.dermai.presentation.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.Disease
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
    private val stateHandle: SavedStateHandle
) : BaseViewModel() {

    private val shouldSave = stateHandle.get<Boolean>("shouldSave") ?: false
    private val _disease = stateHandle.getLiveData<Disease>("disease")
    val disease: LiveData<Disease> = _disease


    private fun updateSearchHistory(id: Int) {
        networkCall({ repo.updateSearchHistory(id) })
    }

    init {
        if (shouldSave) {
            val id = disease.value?.id?.let {
                updateSearchHistory(it)
            }
        }
    }

}

