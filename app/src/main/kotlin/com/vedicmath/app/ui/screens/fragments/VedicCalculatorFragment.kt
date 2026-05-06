package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.vedicmath.app.R
import com.vedicmath.app.databinding.FragmentMainScreenBinding
import com.vedicmath.app.models.MethodChoice
import com.vedicmath.app.ui.screens.MainActivity

class VedicCalculatorFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private enum class CalcMode {
        MULTIPLY, SQUARE, CUBE
    }

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
        setupHowItWorksButton()

        binding.btnSolve.setOnClickListener {
            clearInputErrors()

            val n1 = getN1()
            val n2 = if (currentMode == CalcMode.MULTIPLY) getN2() else null

            when {
                n1 == null -> {
                    binding.etInputOne.error = "Enter a whole number from 0 to 9999"
                    Toast.makeText(requireContext(), "First number is invalid.", Toast.LENGTH_SHORT).show()
                }

                n1 < 0 -> {
                    binding.etInputOne.error = "Must be 0 or greater"
                    Toast.makeText(requireContext(), "First number must be 0 or greater.", Toast.LENGTH_SHORT).show()
                }

                n1 > 9999 -> {
                    binding.etInputOne.error = "Must be 9999 or less"
                    Toast.makeText(requireContext(), "First number must be 9999 or less.", Toast.LENGTH_SHORT).show()
                }

                currentMode == CalcMode.MULTIPLY && n2 == null -> {
                    binding.etInputTwo.error = "Enter a whole number from 0 to 9999"
                    Toast.makeText(requireContext(), "Second number is invalid.", Toast.LENGTH_SHORT).show()
                }

                currentMode == CalcMode.MULTIPLY && n2 != null && n2 < 0 -> {
                    binding.etInputTwo.error = "Must be 0 or greater"
                    Toast.makeText(requireContext(), "Second number must be 0 or greater.", Toast.LENGTH_SHORT).show()
                }

                currentMode == CalcMode.MULTIPLY && n2 != null && n2 > 9999 -> {
                    binding.etInputTwo.error = "Must be 9999 or less"
                    Toast.makeText(requireContext(), "Second number must be 9999 or less.", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val calc = when (currentMode) {
                        CalcMode.MULTIPLY -> com.vedicmath.app.models.VedicMath.solveMultiplication(
                            n1,
                            n2!!,
                            selectedMethod
                        )

                        CalcMode.SQUARE -> com.vedicmath.app.models.VedicMath.solveSquare(
                            n1,
                            selectedMethod
                        )

                        CalcMode.CUBE -> com.vedicmath.app.models.VedicMath.solveCube(
                            n1,
                            selectedMethod
                        )
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

    private fun getN1(): Int? {
        return binding.etInputOne.text?.toString()?.toIntOrNull()
    }

    private fun getN2(): Int? {
        return binding.etInputTwo.text?.toString()?.toIntOrNull()
    }

    private fun clearInputErrors() {
        binding.etInputOne.error = null
        binding.etInputTwo.error = null
    }

    private fun setupModeToggle() {
        binding.toggleGroup.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, checkedId: Int, isChecked: Boolean ->
            if (!isChecked) return@addOnButtonCheckedListener

            currentMode = when (checkedId) {
                R.id.btnModeSquare -> CalcMode.SQUARE
                R.id.btnModeCube -> CalcMode.CUBE
                else -> CalcMode.MULTIPLY
            }

            if (currentMode != CalcMode.MULTIPLY) {
                binding.etInputTwo.text?.clear()
                binding.etInputTwo.error = null
            }

            selectedMethod = MethodChoice.AUTO
            refreshMethodChips()
            updateModeUi()
        }
    }

    private fun updateModeUi() {
        when (currentMode) {
            CalcMode.MULTIPLY -> {
                binding.tvRatioHint.text = "Mode: Ratio / Multiply • Method: ${selectedMethod.label}"
                binding.etInputOne.hint = "First"
                binding.etInputTwo.visibility = View.VISIBLE
                binding.etInputTwo.isEnabled = true
                binding.etInputTwo.hint = "Second"
            }

            CalcMode.SQUARE -> {
                binding.tvRatioHint.text = "Mode: Square • Method: ${selectedMethod.label} • Enter one number"
                binding.etInputOne.hint = "Number"
                binding.etInputTwo.text?.clear()
                binding.etInputTwo.error = null
                binding.etInputTwo.isEnabled = false
                binding.etInputTwo.visibility = View.GONE
            }

            CalcMode.CUBE -> {
                binding.tvRatioHint.text = "Mode: Cube • Method: ${selectedMethod.label} • Enter one number"
                binding.etInputOne.hint = "Number"
                binding.etInputTwo.text?.clear()
                binding.etInputTwo.error = null
                binding.etInputTwo.isEnabled = false
                binding.etInputTwo.visibility = View.GONE
            }
        }
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
                requireContext(),
                android.R.color.holo_orange_light
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

            CalcMode.SQUARE -> listOf(
                MethodChoice.AUTO,
                MethodChoice.SQUARE_DUPLEX,
                MethodChoice.SQUARE_ENDS_14,
                MethodChoice.SQUARE_ENDS_5,
                MethodChoice.SQUARE_ENDS_69
            )

            CalcMode.CUBE -> listOf(
                MethodChoice.AUTO,
                MethodChoice.CUBE_1248,
                MethodChoice.CUBE_RATIO,
                MethodChoice.CUBE_ALGEBRAIC
            )
        }
    }

    private fun setupHowItWorksButton() {
        binding.btnHowItWorks.setOnClickListener {
            showHowItWorksScreen(HowItWorksScreenFragment.SECTION_GENERAL)
        }
    }

    private fun showHowItWorksScreen(section: String) {
        (activity as? MainActivity)?.replaceFragment(
            HowItWorksScreenFragment.newInstance(section)
        )
    }

    private fun buildObservationText(methodName: String): String {
        return "Observation: $methodName"
    }

    private fun setupHelpButtons() {
        binding.btnHelpRatio.setOnClickListener {
            showHowItWorksScreen(HowItWorksScreenFragment.SECTION_MULTIPLY)
        }

        binding.btnHelpSquare.setOnClickListener {
            showHowItWorksScreen(HowItWorksScreenFragment.SECTION_SQUARE)
        }

        binding.btnHelpCube.setOnClickListener {
            showHowItWorksScreen(HowItWorksScreenFragment.SECTION_CUBE)
        }

        binding.btnHelpMult.setOnClickListener {
            showHowItWorksScreen(HowItWorksScreenFragment.SECTION_MULTIPLY)
        }
    }

    private fun setupQuizButton() {
        binding.btnCrossQuiz.setOnClickListener {
            when (currentMode) {
                CalcMode.MULTIPLY -> {
                    (activity as? MainActivity)?.replaceFragment(CrossProductQuizFragment())
                }

                CalcMode.SQUARE, CalcMode.CUBE -> {
                    Toast.makeText(
                        requireContext(),
                        "Quiz is currently available in Multiply mode.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}