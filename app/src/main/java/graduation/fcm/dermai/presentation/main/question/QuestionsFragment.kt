package graduation.fcm.dermai.presentation.main.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentQuestionsBinding
import graduation.fcm.dermai.domain.model.home.Question
import graduation.fcm.dermai.presentation.main.adapter.AnswerAdapter

@AndroidEntryPoint
class QuestionsFragment : BaseFragment<FragmentQuestionsBinding>() {

    override val viewModel: QuestionsViewModel by viewModels()
    private val answerAdapter = AnswerAdapter { answer ->
        viewModel.preformClick(answer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
        observeQuestionState()
    }

    private fun observeQuestionState() {
        viewModel.state.observe(viewLifecycleOwner) { questionState ->
            handleQuestions(questionState.currentPosition, questionState.question)
            if (questionState.shouldNavigateToAction)
                navigate(QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment())

        }
    }

    private fun handleQuestions(position: Int, questions: List<Question>) {
        binding.rvAnswers.adapter = answerAdapter
        val layoutManager = when (questions[position].answer.size) {
            2 -> LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            3 -> LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            else -> GridLayoutManager(requireContext(), 2)
        }
        binding.rvAnswers.layoutManager = layoutManager
        answerAdapter.changeItems(questions[position].answer)
        binding.tvQuestion.text = questions[position].questionText
    }

    private fun handleClicks() {
        binding.ivBackBtn.setOnClickListener {
            when (viewModel.getCurrentPosition()) {
                0 -> findNavController().navigateUp()
                else -> viewModel.getLastQuestion()
            }
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentQuestionsBinding.inflate(inflater, container, false)

}