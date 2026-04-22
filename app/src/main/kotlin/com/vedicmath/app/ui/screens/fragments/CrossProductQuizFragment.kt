package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vedicmath.app.crossproduct.advanced.AdvancedCrossProductQuizSession
import com.vedicmath.app.crossproduct.advanced.AdvancedQuizItem
import com.vedicmath.app.databinding.FragmentCrossProductQuizBinding
import com.vedicmath.app.domain.models.CrossProductQuizSession
import com.vedicmath.app.models.CrossProductQuizItem

class CrossProductQuizFragment : Fragment() {

    private var _binding: FragmentCrossProductQuizBinding? = null
    private val binding get() = _binding!!

    private var isAdvancedMode: Boolean = false

    private val standardSession = CrossProductQuizSession()
    private val advancedSession = AdvancedCrossProductQuizSession()

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
            resetQuiz()
        }

        binding.btnBackToCalculator.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.advancedModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            isAdvancedMode = isChecked
            startNewQuiz(size = 5)
        }

        isAdvancedMode = false
        binding.advancedModeSwitch.isChecked = false
        startNewQuiz(size = 5)
    }

    private fun startNewQuiz(size: Int) {
        if (isAdvancedMode) {
            advancedSession.newSession(size)
        } else {
            standardSession.newSession(size)
        }
        updateScoreUi()
        displayCurrentQuestion()
    }

    private fun displayCurrentQuestion() {
        val advItem: AdvancedQuizItem? =
            if (isAdvancedMode) advancedSession.currentItem() else null

        val stdItem: CrossProductQuizItem? =
            if (!isAdvancedMode) standardSession.currentItem() else null

        when {
            advItem != null -> {
                binding.tvQuizType.text = advItem.ruleName
                binding.tvQuizPrompt.text =
                    "Find only the cross term for ${advItem.question.left} × ${advItem.question.right}"
                binding.tvQuizExplanationPreview.text =
                    "Use the rule mentally, then enter only the cross term."
            }

            stdItem != null -> {
                binding.tvQuizType.text = stdItem.typeLabel
                binding.tvQuizPrompt.text = stdItem.prompt
                binding.tvQuizExplanationPreview.text =
                    "Use the rule mentally, then enter only the cross term."
            }

            else -> {
                binding.tvQuizType.text = ""
                binding.tvQuizPrompt.text = ""
                binding.tvQuizExplanationPreview.text = ""
            }
        }

        binding.etCrossAnswer.text?.clear()
        binding.tvQuizFeedback.text = ""
    }

    private fun submitAnswer() {
        val userAnswer = binding.etCrossAnswer.text?.toString()?.trim()?.toIntOrNull()
        if (userAnswer == null) {
            binding.etCrossAnswer.error = "Enter a whole-number cross term"
            return
        }

        binding.etCrossAnswer.error = null

        val currentAdvancedItem = if (isAdvancedMode) advancedSession.currentItem() else null
        val currentStandardItem = if (!isAdvancedMode) standardSession.currentItem() else null

        val isCorrect = if (isAdvancedMode) {
            advancedSession.submitAnswer(userAnswer)
        } else {
            standardSession.submitAnswer(userAnswer)
        }

        updateScoreUi()

        val feedbackText = if (isAdvancedMode) {
            val explanation = currentAdvancedItem?.ruleDescription.orEmpty()
            if (isCorrect) {
                "Correct ✅\n$explanation"
            } else {
                val correctAnswer =
                    currentAdvancedItem?.let { it.question.left * it.question.right }?.toString().orEmpty()
                "Not quite.\nCorrect answer: $correctAnswer\n$explanation"
            }
        } else {
            val explanation = currentStandardItem?.ruleDescription.orEmpty()
            if (isCorrect) {
                "Correct ✅\n$explanation"
            } else {
                val correctAnswer = currentStandardItem?.expectedCrossTerm?.toString().orEmpty()
                "Not quite.\nCorrect cross term: $correctAnswer\n$explanation"
            }
        }

        binding.tvQuizFeedback.text = feedbackText
    }

    private fun loadNextQuestion() {
        val finished = if (isAdvancedMode) {
            advancedSession.isFinished()
        } else {
            standardSession.isFinished()
        }

        if (finished) {
            Toast.makeText(requireContext(), "Quiz finished", Toast.LENGTH_SHORT).show()
        } else {
            displayCurrentQuestion()
        }
    }

    private fun resetQuiz() {
        startNewQuiz(size = 5)
        Toast.makeText(requireContext(), "Quiz reset", Toast.LENGTH_SHORT).show()
    }

    private fun updateScoreUi() {
        if (isAdvancedMode) {
            val score = advancedSession.score
            val total = 5
            val accuracy = if (total == 0) 0 else (score * 100) / total
            binding.tvQuizScore.text = "Score: $score/$total   Accuracy: $accuracy%"
        } else {
            val score = standardSession.currentScore()
            binding.tvQuizScore.text =
                "Score: ${score.correctAnswers}/${score.totalAsked}   Accuracy: ${score.accuracyPercent}%"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}