package com.vedicmath.app.ui.screens.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.vedicmath.app.R
import com.vedicmath.app.databinding.FragmentMainScreenBinding
import com.vedicmath.app.models.MethodChoice
import com.vedicmath.app.models.VedicMath
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
        refreshMethodChips()
        updateModeUi()

        binding.btnSolve.setOnClickListener {
            clearInputErrors()

            val n1 = getN1()
            val n2 = getN2()

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
                        CalcMode.MULTIPLY -> VedicMath.solveMultiplication(n1, n2!!, selectedMethod)
                        CalcMode.SQUARE -> VedicMath.solveSquare(n1, selectedMethod)
                        CalcMode.CUBE -> VedicMath.solveCube(n1, selectedMethod)
                    }

                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.text = "Method: ${calc.methodName}\nResult: ${calc.result}"

                    val solutionFragment = SolutionFragment.newInstance(
                        method = calc.methodName,
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

    private fun setupModeToggle() {
        binding.toggleGroup.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, checkedId: Int, isChecked: Boolean ->
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
            isCheckedIconVisible = false
            chipStrokeWidth = 2f
            setEnsureMinTouchTargetSize(false)
            chipMinHeight = 40f

            chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#FFEB3B"))
            chipBackgroundColor = ColorStateList.valueOf(
                if (selected) Color.parseColor("#FFEB3B") else Color.parseColor("#1E1E1E")
            )
            setTextColor(if (selected) Color.parseColor("#121212") else Color.parseColor("#FFEB3B"))

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

    private fun setupHelpButtons() {
        binding.btnHelpRatio.setOnClickListener {
            showHelpDialog(
                title = "Multiply Method Guide",
                message = """
                    Choose a method that matches the number pattern:

                    • AUTO
                      Lets the app choose the clearest method.

                    • BY 1 MORE
                      Same tens digit, units total 10 or more.

                    • SUM 9 SAME TENS
                      Same tens digit, units total exactly 9.

                    • SAME UNITS
                      Both numbers end in the same digit.

                    • RECIPROCALS
                      Reverse-digit pairs such as 24 × 42.

                    • 1-DIGIT GROUP / 2-DIGIT GROUP
                      Break a large multiplication into grouped parts.

                    • VERTICAL
                      Good general method for 2-digit multiplication.

                    • NEAR-BASE
                      Best when numbers are close to 10 or 100.

                    • SERIES
                      Useful when one number has a clear digit pattern.
                """.trimIndent()
            )
        }

        binding.btnHelpMult.setOnClickListener {
            showHelpDialog(
                title = "Multiply Examples",
                message = """
                    Quick examples for the multiply methods:

                    • SUM 9 SAME TENS
                      26 × 23

                    • BY 1 MORE
                      56 × 54 or 58 × 52

                    • SAME UNITS
                      27 × 97 or 15 × 95

                    • RECIPROCALS
                      24 × 42

                    • 1-DIGIT GROUP
                      123 × 4

                    • 2-DIGIT GROUP
                      123 × 24

                    • VERTICAL
                      23 × 47

                    • NEAR-BASE
                      97 × 96

                    • SERIES
                      123 × 246
                """.trimIndent()
            )
        }

        binding.btnHelpSquare.setOnClickListener {
            showHelpDialog(
                title = "Square Help",
                message = """
                    Square methods available:

                    • AUTO
                      Lets the app choose the clearest method.

                    • ENDS 1/4
                      Useful for numbers ending in 1 or 4.

                    • ENDS 5
                      Classic shortcut for numbers ending in 5.

                    • ENDS 6/9
                      Useful for numbers ending in 6 or 9.

                    • DUPLEX
                      A more general squaring method.
                """.trimIndent()
            )
        }

        binding.btnHelpCube.setOnClickListener {
            showHelpDialog(
                title = "Cube Help",
                message = """
                    Cube methods available:

                    • AUTO
                      Lets the app choose the clearest method.

                    • ONE-LINE 1|6|12|8
                      Pattern-based shortcut for some cubes.

                    • BASE ROW 1|2|4|8
                      Uses ratio-style cube structure.

                    • ALGEBRAIC
                      Uses algebraic expansion ideas.
                """.trimIndent()
            )
        }
    }

    private fun updateModeUi() {
        val showSecondInput = currentMode == CalcMode.MULTIPLY
        binding.etInputTwo.visibility = if (showSecondInput) View.VISIBLE else View.GONE
        binding.etInputOne.hint = if (showSecondInput) "First Number" else "Number"
    }

    private fun showHelpDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun getN1(): Int? = parseInput(binding.etInputOne.text?.toString())

    private fun getN2(): Int? = parseInput(binding.etInputTwo.text?.toString())

    private fun parseInput(value: String?): Int? {
        return value?.trim()?.takeIf { it.isNotEmpty() }?.toIntOrNull()
    }

    private fun clearInputErrors() {
        binding.etInputOne.error = null
        binding.etInputTwo.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}