package graduation.fcm.dermai.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.ScanResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

    private val _searchHistoryResult = MutableLiveData<ResponseState<ScanResponse>>()
    val searchHistoryResult: LiveData<ResponseState<ScanResponse>> = _searchHistoryResult

    fun getSearchHistory() {
        networkCall({ repo.getSearchHistory() }, {
            _searchHistoryResult.value = it
        })
    }

    fun deleteSingleSearchHistory(id: Int) {
        networkCall({ repo.deleteSingleSearchHistory(id)},{
            _searchHistoryResult.value = it
        })
    }


}

