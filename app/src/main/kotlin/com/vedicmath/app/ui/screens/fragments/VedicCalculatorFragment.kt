package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vedicmath.app.R
import com.vedicmath.app.databinding.FragmentMainScreenBinding
import com.vedicmath.app.models.MethodChoice
import com.vedicmath.app.ui.screens.MainActivity

class VedicCalculatorFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private enum class CalcMode { MULTIPLY, SQUARE, CUBE }

    private var currentMode = CalcMode.MULTIPLY
    private var selectedMethod = MethodChoice.AUTO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupModeToggle()
        setupHelpButtons()
        setupQuizButton()
        refreshMethodChips()
        updateModeUi()

        // Onboarding as a screen or dialog (choose one approach)
        setupHowItWorksButton()

        binding.btnSolve.setOnClickListener {
            clearInputErrors()

            val n1 = getN1()
            val n2 = if (currentMode == CalcMode.MULTIPLY) getN2() else null

            when {
                n1 == null -> {
                    binding.etInputOne?.error = "Enter a whole number from 0 to 9999"
                    Toast.makeText(requireContext(), "First number is invalid.", Toast.LENGTH_SHORT).show()
                }
                n1 < 0 -> {
                    binding.etInputOne?.error = "Must be 0 or greater"
                    Toast.makeText(requireContext(), "First number must be 0 or greater.", Toast.LENGTH_SHORT).show()
                }
                n1 > 9999 -> {
                    binding.etInputOne?.error = "Must be 9999 or less"
                    Toast.makeText(requireContext(), "First number must be 9999 or less.", Toast.LENGTH_SHORT).show()
                }
                currentMode == CalcMode.MULTIPLY && n2 == null -> {
                    binding.etInputTwo?.error = "Enter a whole number from 0 to 9999"
                    Toast.makeText(requireContext(), "Second number is invalid.", Toast.LENGTH_SHORT).show()
                }
                currentMode == CalcMode.MULTIPLY && n2 != null && n2 < 0 -> {
                    binding.etInputTwo?.error = "Must be 0 or greater"
                    Toast.makeText(requireContext(), "Second number must be 0 or greater.", Toast.LENGTH_SHORT).show()
                }
                currentMode == CalcMode.MULTIPLY && n2 != null && n2 > 9999 -> {
                    binding.etInputTwo?.error = "Must be 9999 or less"
                    Toast.makeText(requireContext(), "Second number must be 9999 or less.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val calc = when (currentMode) {
                        CalcMode.MULTIPLY -> com.vedicmath.app.models.VedicMath.solveMultiplication(n1, n2!!, selectedMethod)
                        CalcMode.SQUARE -> com.vedicmath.app.models.VedicMath.solveSquare(n1, selectedMethod)
                        CalcMode.CUBE -> com.vedicmath.app.models.VedicMath.solveCube(n1, selectedMethod)
                    }

                    val observation = buildObservationText(calc.methodName)
                    val execution = calc.methodName

                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.text = "Execution: $execution\nResult: ${calc.result}"

                    val solutionFragment = SolutionFragment.newInstance(
                        observation = observation,
                        execution = execution,
                        result = calc.result,
                        steps = ArrayList(calc.steps)
                    )

                    (activity as? MainActivity)?.replaceFragment(solutionFragment)
                }
            }
        }

        binding.btnClear.setOnClickListener {
            binding.etInputOne.text?.clear()
            binding.etInputTwo.text?.clear()
            clearInputErrors()
            binding.tvResult.text = ""
            binding.tvResult.visibility = View.GONE
            Toast.makeText(requireContext(), "Cleared", Toast.LENGTH_SHORT).show()
        }
    }

    // Input helpers
    private fun getN1(): Int? {
        val s = binding.etInputOne?.text?.toString()
        return s?.toIntOrNull()
    }

    private fun getN2(): Int? {
        val s = binding.etInputTwo?.text?.toString()
        return s?.toIntOrNull()
    }

    private fun clearInputErrors() {
        binding.etInputOne?.error = null
        binding.etInputTwo?.error = null
    }

    // UI wiring
    private fun setupModeToggle() {
        binding.toggleGroup.addOnButtonCheckedListener { _: com.google.android.material.button.MaterialButtonToggleGroup,
                                                         checkedId: Int, isChecked: Boolean ->
            if (!isChecked) return@addOnButtonCheckedListener

            currentMode = when (checkedId) {
                R.id.btnModeSquare -> CalcMode.SQUARE
                R.id.btnModeCube -> CalcMode.CUBE
                else -> CalcMode.MULTIPLY
            }

            selectedMethod = MethodChoice.AUTO
            refreshMethodChips()
            updateModeUi()
        }
    }

    private fun updateModeUi() {
        val modeLabel = when (currentMode) {
            CalcMode.MULTIPLY -> "RATIO / MULT"
            CalcMode.SQUARE -> "SQUARE"
            CalcMode.CUBE -> "CUBE"
        }
        binding.tvRatioHint.text = "Mode: $modeLabel • Method: ${selectedMethod.label}"
    }

    private fun refreshMethodChips() {
        val methods = methodsForCurrentMode()

        if (selectedMethod !in methods) {
            selectedMethod = methods.first()
        }

        binding.chipGroupExperiments.removeAllViews()

        methods.forEach { method ->
            binding.chipGroupExperiments.addView(createMethodChip(method))
        }
    }

    private fun createMethodChip(method: MethodChoice): Chip {
        val selected = method == selectedMethod

        return Chip(requireContext()).apply {
            text = method.label
            isCheckable = true
            isChecked = selected
            isCheckedIconVisible = false
            setEnsureMinTouchTargetSize(false)

            chipMinHeight = 30f
            chipStrokeWidth = 1f
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 9f)

            chipStartPadding = 8f
            chipEndPadding = 8f
            textStartPadding = 0f
            textEndPadding = 0f
            closeIconStartPadding = 0f
            closeIconEndPadding = 0f

            chipStrokeColor = androidx.appcompat.content.res.AppCompatResources.getColorStateList(
                requireContext(), android.R.color.holo_orange_light
            )
            chipBackgroundColor = android.content.res.ColorStateList.valueOf(
                if (selected) android.graphics.Color.parseColor("#FFEB3B")
                else android.graphics.Color.parseColor("#1E1E1E")
            )
            setTextColor(
                if (selected) android.graphics.Color.parseColor("#121212")
                else android.graphics.Color.parseColor("#FFEB3B")
            )

            setOnClickListener {
                selectedMethod = method
                refreshMethodChips()
                updateModeUi()
            }
        }
    }

    private fun methodsForCurrentMode(): List<MethodChoice> {
        return when (currentMode) {
            CalcMode.MULTIPLY -> listOf(
                MethodChoice.AUTO,
                MethodChoice.MULT_BY_ONE_MORE,
                MethodChoice.MULT_SUM_9,
                MethodChoice.MULT_SAME_UNITS,
                MethodChoice.MULT_RECIPROCAL,
                MethodChoice.MULT_GROUP_1,
                MethodChoice.MULT_GROUP_2,
                MethodChoice.MULT_VERTICAL,
                MethodChoice.MULT_NEAR_BASE,
                MethodChoice.MULT_SERIES
            )
            CalcMode.SQUARE -> listOf(MethodChoice.AUTO)
            CalcMode.CUBE -> listOf(MethodChoice.AUTO)
        }
    }

    // HOW IT WORKS onboarding -> switch to screen-based flow
    private fun setupHowItWorksButton() {
        binding.btnHowItWorks?.setOnClickListener {
            showHowItWorksScreen()
        }
    }

    private fun showHowItWorksScreen() {
        HowItWorksScreenFragment().let { (activity as? MainActivity)?.replaceFragment(it) }
    }

    // Simple helper for observed text
    private fun buildObservationText(methodName: String): String {
        return "Observation: ${methodName}"
    }

    // Help/Quiz setup (keep as-is wiring elsewhere)
    private fun setupHelpButtons() {
        binding.btnHelpRatio?.setOnClickListener { showHowItWorksScreen() }
        binding.btnHelpSquare?.setOnClickListener { showHowItWorksScreen() }
        binding.btnHelpCube?.setOnClickListener { showHowItWorksScreen() }
        binding.btnHelpMult?.setOnClickListener { showHowItWorksScreen() }
    }

    private fun setupQuizButton() {
        binding.btnCrossQuiz?.setOnClickListener {
            val quizFragment = CrossProductQuizFragment()
            (activity as? MainActivity)?.replaceFragment(quizFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}