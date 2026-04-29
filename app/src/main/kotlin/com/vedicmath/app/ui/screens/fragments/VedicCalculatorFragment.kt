package com.vedicmath.app.ui.screens.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
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
        setupQuizButton()
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
            isChecked = selected
            isCheckedIconVisible = false
            setEnsureMinTouchTargetSize(false)

            chipMinHeight = 30f
            chipStrokeWidth = 1f
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 9f)

            chipStartPadding = 8f
            chipEndPadding = 8f
            textStartPadding = 0f
            textEndPadding = 0f
            closeIconStartPadding = 0f
            closeIconEndPadding = 0f

            chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#FFEB3B"))
            chipBackgroundColor = ColorStateList.valueOf(
                if (selected) Color.parseColor("#FFEB3B") else Color.parseColor("#1E1E1E")
            )
            setTextColor(
                if (selected) Color.parseColor("#121212") else Color.parseColor("#FFEB3B")
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

    private fun setupHelpButtons() {
        binding.btnHelpRatio.setOnClickListener {
            showMultiplyHelpTopicsDialog()
        }

        binding.btnHelpMult.setOnClickListener {
            showCrossProductDrillTopicsDialog()
        }

        binding.btnHelpSquare.setOnClickListener {
            showSquareHelpTopicsDialog()
        }

        binding.btnHelpCube.setOnClickListener {
            showCubeHelpTopicsDialog()
        }
    }

    private fun showMultiplyHelpTopicsDialog() {
        val topics = arrayOf(
            "AUTO",
            "BY 1 MORE",
            "SUM 9 SAME TENS",
            "SAME UNITS",
            "RECIPROCALS",
            "1-DIGIT GROUP / 2-DIGIT GROUP",
            "VERTICAL & CROSSWISE",
            "NEAR-BASE",
            "SERIES",
            "CROSS PRODUCT DRILLS"
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Multiply Help Topics")
            .setItems(topics) { _, which ->
                when (which) {
                    0 -> showHelpDetailDialog(
                        title = "AUTO",
                        message = """
                            AUTO is the observation-first path. The app looks for a recognizable pattern and then picks the clearest execution method.

                            Good for:
                            • general use
                            • comparing methods automatically
                            • seeing which pattern the app detected

                            Example:
                            • 23 × 47 → Vertical and Crosswise
                            • 97 × 96 → Near-Base
                        """.trimIndent()
                    )

                    1 -> showHelpDetailDialog(
                        title = "BY 1 MORE",
                        message = """
                            Use this when both numbers share the same tens digit and the units digits add to 10 or more.

                            Core idea:
                            • left block = a × (a + 1)
                            • right block = units product
                            • if units exceed 10, include the extra shift

                            Example:
                            • 58 × 52
                        """.trimIndent()
                    )

                    2 -> showHelpDetailDialog(
                        title = "SUM 9 SAME TENS",
                        message = """
                            Use this when the tens digits match and the units digits add exactly to 9.

                            Core idea:
                            • start from the by-1-more head
                            • then subtract the tens digit
                            • finish with the units product

                            Example:
                            • 26 × 23
                        """.trimIndent()
                    )

                    3 -> showHelpDetailDialog(
                        title = "SAME UNITS",
                        message = """
                            Use this when both numbers end in the same digit.

                            Core idea:
                            • left block = tens × tens
                            • middle block = units × (sum of tens digits)
                            • right block = units²

                            Example:
                            • 27 × 97
                            • 15 × 95
                        """.trimIndent()
                    )

                    4 -> showHelpDetailDialog(
                        title = "RECIPROCALS",
                        message = """
                            Use this for reverse-digit pairs.

                            Example:
                            • 24 × 42

                            Core idea:
                            • same outer product on both sides
                            • middle = sum of the squares of the digits
                        """.trimIndent()
                    )

                    5 -> showHelpDetailDialog(
                        title = "GROUPING",
                        message = """
                            Grouping methods break a larger multiplication into easier chunks.

                            1-DIGIT GROUP:
                            • useful for something like 123 × 4

                            2-DIGIT GROUP:
                            • useful for something like 123 × 24

                            These methods reduce cognitive load by splitting the problem into place-value blocks.
                        """.trimIndent()
                    )

                    6 -> showHelpDetailDialog(
                        title = "VERTICAL & CROSSWISE",
                        message = """
                            This is the general 2-digit multiplication method.

                            Flow:
                            • right product
                            • cross product
                            • left product
                            • carry forward as needed

                            Example:
                            • 23 × 47

                            The app can now also surface Cross Product Drill insights inside the cross step when a shortcut family matches.
                        """.trimIndent()
                    )

                    7 -> showHelpDetailDialog(
                        title = "NEAR-BASE",
                        message = """
                            Use this when both numbers are close to a convenient base such as 10 or 100.

                            Example:
                            • 97 × 96

                            Core idea:
                            • work from deficiencies or excesses relative to the base
                            • combine left part and right part cleanly
                        """.trimIndent()
                    )

                    8 -> showHelpDetailDialog(
                        title = "SERIES",
                        message = """
                            Use this when one number follows a clear digit pattern.

                            Example:
                            • 123 × 246

                            This is more observational and works best when the number structure is easy to recognize.
                        """.trimIndent()
                    )

                    9 -> showCrossProductDrillTopicsDialog()
                }
            }
            .setNegativeButton("Close", null)
            .create()

        dialog.show()
        applyDialogButtonStyling(dialog)
    }

    private fun showCrossProductDrillTopicsDialog() {
        val topics = arrayOf(
            "What are Cross Product Drills?",
            "Units Sum 10 / Tens Differ by 1",
            "Units Sum 10 / Tens Differ by 2",
            "Units Sum 5 / Tens Differ by 1",
            "Units Sum 5 / Tens Differ by 2",
            "Units Sum 15 / Tens Differ by 1",
            "Digits in 1:2 Ratio",
            "Digits in 2:1 Ratio",
            "Practice in Cross Product Quiz"
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Cross Product Drills")
            .setItems(topics) { _, which ->
                when (which) {
                    0 -> showHelpDetailDialog(
                        title = "What are Cross Product Drills?",
                        message = """
                            Cross Product Drills are shortcut families for the middle term in Vertical & Crosswise multiplication.

                            Instead of always doing:
                            • (left tens × right units) + (left units × right tens)

                            you may spot a structure that makes the cross product faster.

                            These drills now appear in the quiz and can also appear inside Step 5 of Vertical & Crosswise solutions.
                        """.trimIndent()
                    )

                    1 -> showHelpDetailDialog(
                        title = "Units Sum 10 / Tens Differ by 1",
                        message = """
                            Rule:
                            • cross product = the smaller number

                            Example:
                            • 37 × 43
                            • cross product = 37
                        """.trimIndent()
                    )

                    2 -> showHelpDetailDialog(
                        title = "Units Sum 10 / Tens Differ by 2",
                        message = """
                            Rule:
                            • cross product = smaller number + its units digit

                            Example:
                            • 23 × 47
                            • cross product = 23 + 3 = 26
                        """.trimIndent()
                    )

                    3 -> showHelpDetailDialog(
                        title = "Units Sum 5 / Tens Differ by 1",
                        message = """
                            Rule:
                            • cross product = half of the tens-base + the smaller units digit

                            Example:
                            • 43 × 52
                            • half of 40 is 20
                            • 20 + 3 = 23
                        """.trimIndent()
                    )

                    4 -> showHelpDetailDialog(
                        title = "Units Sum 5 / Tens Differ by 2",
                        message = """
                            Rule:
                            • cross product = 5 × smaller tens + smaller units + smaller units

                            Example:
                            • 23 × 42
                            • 10 + 3 + 3 = 16
                        """.trimIndent()
                    )

                    5 -> showHelpDetailDialog(
                        title = "Units Sum 15 / Tens Differ by 1",
                        message = """
                            Rule:
                            • cross product = smaller number + half of its tens-base

                            Example:
                            • 58 × 67
                            • 58 + half of 50
                            • 58 + 25 = 83
                        """.trimIndent()
                    )

                    6 -> showHelpDetailDialog(
                        title = "Digits in 1:2 Ratio",
                        message = """
                            If both numbers have digits in a 1:2 ratio, the cross product equals the right vertical product.

                            Example pattern:
                            • 12 × 24
                            • cross product = 2 × 4
                        """.trimIndent()
                    )

                    7 -> showHelpDetailDialog(
                        title = "Digits in 2:1 Ratio",
                        message = """
                            If both numbers have digits in a 2:1 ratio, the cross product equals the left vertical product.

                            Example pattern:
                            • 21 × 42
                            • cross product = 2 × 4
                        """.trimIndent()
                    )

                    8 -> showHelpDetailDialog(
                        title = "Practice in Cross Product Quiz",
                        message = """
                            Use the Cross Product Quiz button on the main screen to practice these middle-term families directly.

                            Good next steps:
                            • practice one family repeatedly
                            • then watch for the same pattern in Vertical & Crosswise Step 5
                        """.trimIndent()
                    )
                }
            }
            .setNegativeButton("Close", null)
            .create()

        dialog.show()
        applyDialogButtonStyling(dialog)
    }

    private fun showSquareHelpTopicsDialog() {
        val topics = arrayOf(
            "AUTO",
            "ENDS 1 / 4",
            "ENDS 5",
            "ENDS 6 / 9",
            "DUPLEX"
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Square Help Topics")
            .setItems(topics) { _, which ->
                when (which) {
                    0 -> showHelpDetailDialog(
                        title = "AUTO",
                        message = """
                            AUTO chooses the clearest square method based on the ending digit and size of the number.

                            This is the best default when you want the app to teach the most natural pattern.
                        """.trimIndent()
                    )

                    1 -> showHelpDetailDialog(
                        title = "ENDS 1 / 4",
                        message = """
                            These one-line patterns are useful when the number ends in 1 or 4.

                            They reduce the square to a compact base-and-shift form.
                        """.trimIndent()
                    )

                    2 -> showHelpDetailDialog(
                        title = "ENDS 5",
                        message = """
                            Classic shortcut:
                            • multiply the leading part by one more than itself
                            • then append 25

                            Example:
                            • 35² = 3 × 4 | 25 = 1225
                        """.trimIndent()
                    )

                    3 -> showHelpDetailDialog(
                        title = "ENDS 6 / 9",
                        message = """
                            Numbers ending in 6 or 9 have strong one-line patterns that adjust from a nearby base expression.
                        """.trimIndent()
                    )

                    4 -> showHelpDetailDialog(
                        title = "DUPLEX",
                        message = """
                            Duplex is the more universal squaring method.

                            It is broader than the special ending tricks and helps when no short ending pattern stands out.
                        """.trimIndent()
                    )
                }
            }
            .setNegativeButton("Close", null)
            .create()

        dialog.show()
        applyDialogButtonStyling(dialog)
    }

    private fun showCubeHelpTopicsDialog() {
        val topics = arrayOf(
            "AUTO",
            "ONE-LINE 1|6|12|8",
            "BASE ROW 1|2|4|8",
            "ALGEBRAIC"
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Cube Help Topics")
            .setItems(topics) { _, which ->
                when (which) {
                    0 -> showHelpDetailDialog(
                        title = "AUTO",
                        message = """
                            AUTO selects the clearest cube method for the entered number.

                            Use this when you want the app to choose the most teachable path.
                        """.trimIndent()
                    )

                    1 -> showHelpDetailDialog(
                        title = "ONE-LINE 1|6|12|8",
                        message = """
                            This pattern-based cube method uses the familiar coefficient row:
                            • 1 | 3 | 3 | 1
                            and related one-line simplifications.

                            Best for smaller, pattern-friendly cube examples.
                        """.trimIndent()
                    )

                    2 -> showHelpDetailDialog(
                        title = "BASE ROW 1|2|4|8",
                        message = """
                            This is the ratio-style cube view.

                            It emphasizes structured rows and scaling ideas rather than only direct expansion.
                        """.trimIndent()
                    )

                    3 -> showHelpDetailDialog(
                        title = "ALGEBRAIC",
                        message = """
                            The algebraic method expands the cube in a more formal way.

                            It is useful when you want to see the underlying structure directly.
                        """.trimIndent()
                    )
                }
            }
            .setNegativeButton("Close", null)
            .create()

        dialog.show()
        applyDialogButtonStyling(dialog)
    }

    private fun showHelpDetailDialog(title: String, message: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Close", null)
            .create()

        dialog.show()
        applyDialogButtonStyling(dialog)
    }

    private fun applyDialogButtonStyling(dialog: AlertDialog) {
        val highContrastText = Color.parseColor("#121212")
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(highContrastText)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(highContrastText)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(highContrastText)
    }

    private fun setupQuizButton() {
        binding.btnCrossQuiz.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(CrossProductQuizFragment())
        }
    }

    private fun updateModeUi() {
        val showSecondInput = currentMode == CalcMode.MULTIPLY
        binding.etInputTwo.visibility = if (showSecondInput) View.VISIBLE else View.GONE
        binding.etInputOne.hint = if (showSecondInput) "First" else "Number"
        binding.etInputTwo.hint = "Second"

        if (!showSecondInput) {
            binding.etInputTwo.text?.clear()
            binding.etInputTwo.error = null
        }
    }

    private fun buildObservationText(executionMethodName: String): String {
        return when (selectedMethod) {
            MethodChoice.AUTO -> when (currentMode) {
                CalcMode.MULTIPLY -> "Auto detected pattern: $executionMethodName"
                CalcMode.SQUARE -> "Auto detected square pattern: $executionMethodName"
                CalcMode.CUBE -> "Auto detected cube pattern: $executionMethodName"
            }

            else -> "User selected: ${selectedMethod.label}"
        }
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