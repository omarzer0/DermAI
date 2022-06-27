package graduation.fcm.dermai.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

}

