package graduation.fcm.dermai.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.repository.RepositoryImpl
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

}

