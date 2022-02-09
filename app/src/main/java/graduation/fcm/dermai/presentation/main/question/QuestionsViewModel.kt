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
        add(Question("q1", "Are Yui deaf?", listOf("yes", "no"), QuestionType.POLAR)) //0
        add(Question("q2", "Are?", listOf("yes", "no", "be"), QuestionType.MULTI_CHOICE))//1
        add(Question("q1", "Are Yui deaf?", listOf("yes", "no"), QuestionType.POLAR)) //2
        add(
            Question(
                "q2",
                "Are?",
                listOf("yes", "no", "be", "hello"),
                QuestionType.MULTI_CHOICE
            )
        )//3
    },
    val currentPosition: Int = 0,
    val shouldNavigateToAction: Boolean = false
)

