package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vedicmath.app.databinding.FragmentHowItWorksScreenBinding

class HowItWorksScreenFragment : Fragment() {

    private var _binding: FragmentHowItWorksScreenBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_SECTION = "arg_section"

        const val SECTION_GENERAL = "general"
        const val SECTION_MULTIPLY = "multiply"
        const val SECTION_SQUARE = "square"
        const val SECTION_CUBE = "cube"

        fun newInstance(section: String = SECTION_GENERAL): HowItWorksScreenFragment {
            val fragment = HowItWorksScreenFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_SECTION, section)
            }
            return fragment
        }
    }

    private val section: String
        get() = arguments?.getString(ARG_SECTION, SECTION_GENERAL) ?: SECTION_GENERAL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHowItWorksScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applySectionFocus()

        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun applySectionFocus() {
        binding.tvFocusTitle.visibility = View.VISIBLE
        binding.tvFocusBody.visibility = View.VISIBLE

        when (section) {
            SECTION_MULTIPLY -> {
                binding.tvFocusTitle.text = "Multiply / Ratio Help"
                binding.tvFocusBody.text = """
                    Use this mode when working with two inputs.
                    
                    Start here:
                    • Enter both numbers
                    • Leave AUTO selected at first
                    • Tap SOLVE
                    • Then try a specific method to compare
                    
                    Good examples:
                    • 13 × 27 → try STANDARD
                    • 24 × 42 → try RECIPROCALS
                    • 23 × 27 → try SUM 9 / SAME TENS
                    • 24 × 64 → try SAME UNITS if the pattern fits
                    
                    Tip:
                    AUTO chooses a suitable shortcut when one matches your inputs.
                """.trimIndent()
            }

            SECTION_SQUARE -> {
                binding.tvFocusTitle.text = "Square Help"
                binding.tvFocusBody.text = """
                    Square mode uses one number only.
                    The second input is hidden automatically.
                    
                    Start here:
                    • Enter one number
                    • Leave AUTO selected at first
                    • Tap SOLVE
                    • Then compare with a square-specific method
                    
                    Good examples:
                    • 25² → try ENDS 5
                    • 21² or 34² → try ENDS 1/4 when it fits
                    • 76² or 89² → try ENDS 6/9 when it fits
                    • Try DUPLEX when you want a more general square method
                    
                    Tip:
                    If you are unsure which method fits, start with AUTO.
                """.trimIndent()
            }

            SECTION_CUBE -> {
                binding.tvFocusTitle.text = "Cube Help"
                binding.tvFocusBody.text = """
                    Cube mode uses one number only.
                    The second input is hidden automatically.
                    
                    Start here:
                    • Enter one number
                    • Leave AUTO selected at first
                    • Tap SOLVE
                    • Then compare with a cube-specific method
                    
                    Good examples:
                    • 12³ → compare AUTO, ONE-LINE 1|6|12|8, and BASE ROW 1|2|4|8
                    • Try ALGEBRAIC when you want the more expanded worked method
                    
                    Tip:
                    Cube methods are easiest to learn by solving the same example in more than one way.
                """.trimIndent()
            }

            else -> {
                binding.tvFocusTitle.text = "Start Here"
                binding.tvFocusBody.text = """
                    This app is a tutoring tool, not just a calculator.
                    
                    Best first steps:
                    • Pick a top tab
                    • Enter your number or numbers
                    • Leave AUTO selected
                    • Tap SOLVE
                    • Read the worked steps
                    • Then try another method manually
                    
                    Good starting examples:
                    • Multiply: 13 × 27
                    • Square: 25²
                    • Cube: 12³
                    
                    Tip:
                    Use HOW IT WORKS when you want the full overview, and use the HELP buttons for mode-focused guidance.
                """.trimIndent()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}