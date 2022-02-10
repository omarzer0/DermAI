package graduation.fcm.dermai.presentation.main.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import graduation.fcm.dermai.core.BaseViewModel
import graduation.fcm.dermai.domain.model.home.Question
import graduation.fcm.dermai.domain.model.home.QuestionType
import graduation.fcm.dermai.repository.HomeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
    private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableLiveData<QuestionsState>()
    val state: LiveData<QuestionsState> = _state
    var answers: MutableList<String> = mutableListOf()

    fun preformClick(answer: String) {
        answers.add(answer)
        var position = _state.value?.currentPosition ?: return
        val questionsSize = _state.value?.question?.size ?: return
        if (position < questionsSize - 1) {
            _state.value = _state.value?.copy(
                currentPosition = ++position
            )
        } else if (position == questionsSize - 1) {
            _state.value = _state.value?.copy(
                shouldNavigateToAction = true
            )
        }
    }

    fun getCurrentPosition() = _state.value?.currentPosition ?: 0

    fun getLastQuestion() {
        var position = _state.value?.currentPosition ?: return
        _state.value = _state.value?.copy(
            currentPosition = --position
        )
    }


    init {
        _state.value = QuestionsState()
    }
}

data class QuestionsState(
    val question: MutableList<Question> = mutableListOf<Question>().apply {
        add(
            Question(
                "q1",
                "Does your skin appear normal and healthy?",
                listOf("yes", "no"),
                QuestionType.POLAR
            )
        ) //0
        add(
            Question(
                "q2",
                "Does the disease affect a large part of the body, a large part of multiple parts?",
                listOf("Single Lesion", "Limited Area", "Widespread"),
                QuestionType.MULTI_CHOICE
            )
        )//1
        add(
            Question(
                "q3",
                "How long have you had this condition?",
                listOf("Days", "Weeks", "Months", "Years"),
                QuestionType.MULTI_CHOICE
            )
        ) //2
        add(
            Question(
                "q4",
                "Do you need to itch?",
                listOf("yes", "no"),
                QuestionType.POLAR
            )
        )//3

        add(
            Question(
                "q5",
                "Do you have a fever?",
                listOf("yes", "no"),
                QuestionType.POLAR
            )
        )
    },
    val currentPosition: Int = 0,
    val shouldNavigateToAction: Boolean = false
)

