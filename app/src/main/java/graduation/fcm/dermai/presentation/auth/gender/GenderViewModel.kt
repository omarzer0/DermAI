package graduation.fcm.dermai.presentation.auth.gender

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.auth.ColorItem
import graduation.fcm.dermai.domain.model.home.UserResponse
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : BaseViewModel() {

    val colorsList = listOf(
        ColorItem("#f3d0bc"),
        ColorItem("#e4b59b"),
        ColorItem("#cd9f87"),
        ColorItem("#b5795f"),
        ColorItem("#a05f3f"),
        ColorItem("#3c2424")
    )

    private val _selectedGender = MutableLiveData(Gender.MALE)
    val selectedGender: LiveData<Gender> = _selectedGender

    private val _selectedColor = MutableLiveData(colorsList[0].color)
    val selectedColor: LiveData<String> = _selectedColor

    enum class Gender {
        MALE,
        FEMALE
    }

    fun updateGender(gender: Gender) {
        _selectedGender.value = gender
    }

    fun updateSelectedColor(color: String) {
        _selectedColor.value = color
    }

    private val _userResult = MutableLiveData<ResponseState<UserResponse>>()
    val userResult: LiveData<ResponseState<UserResponse>> = _userResult

    fun updateUserInfo(age: Int) {
        val color = _selectedColor.value ?: colorsList[0].color
        val gender = if (_selectedGender.value == Gender.MALE) 1 else 0
        networkCall({ repo.updateUserInfo(color, age, gender) }, {
            _userResult.value = it
        })
    }


}