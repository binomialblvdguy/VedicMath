package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vedicmath.app.databinding.FragmentCrossProductQuizBinding
import com.vedicmath.app.models.CrossProductQuizItem
import com.vedicmath.app.models.CrossProductQuizSession

class CrossProductQuizFragment : Fragment() {

    private var _binding: FragmentCrossProductQuizBinding? = null
    private val binding get() = _binding!!

    private val session = CrossProductQuizSession()
    private var currentItem: CrossProductQuizItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrossProductQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmitAnswer.setOnClickListener {
            submitAnswer()
        }

        binding.btnNextQuestion.setOnClickListener {
            loadNextQuestion()
        }

        binding.btnResetQuiz.setOnClickListener {
            session.reset()
            binding.etCrossAnswer.text?.clear()
            binding.tvQuizFeedback.text = ""
            updateScoreUi()
            loadNextQuestion()
            Toast.makeText(requireContext(), "Quiz reset", Toast.LENGTH_SHORT).show()
        }

        updateScoreUi()
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        currentItem = session.nextRandomItem()
        binding.etCrossAnswer.text?.clear()
        binding.tvQuizFeedback.text = ""
        binding.tvQuizType.text = currentItem?.typeLabel.orEmpty()
        binding.tvQuizPrompt.text = currentItem?.prompt.orEmpty()
        binding.tvQuizExplanationPreview.text = "Solve mentally, then enter only the cross term."
    }

    private fun submitAnswer() {
        val item = currentItem ?: return
        val answer = binding.etCrossAnswer.text?.toString()?.trim()?.toIntOrNull()

        if (answer == null) {
            binding.etCrossAnswer.error = "Enter a whole-number cross term"
            return
        }

        binding.etCrossAnswer.error = null

        val correct = session.recordAnswer(item, answer)
        updateScoreUi()

        binding.tvQuizFeedback.text = if (correct) {
            "Correct ✅\n${item.explanation}"
        } else {
            "Not quite.\nCorrect cross term: ${item.expectedCrossTerm}\n${item.explanation}"
        }
    }

    private fun updateScoreUi() {
        val score = session.currentScore()
        binding.tvQuizScore.text =
            "Score: ${score.correctAnswers}/${score.totalAsked}   Accuracy: ${score.accuracyPercent}%"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}