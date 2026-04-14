package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vedicmath.app.databinding.FragmentSolutionBinding

class SolutionFragment : Fragment() {

    private var _binding: FragmentSolutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val method = arguments?.getString(ARG_METHOD).orEmpty()
        val result = arguments?.getString(ARG_RESULT).orEmpty()
        val steps = arguments?.getStringArrayList(ARG_STEPS).orEmpty()

        binding.tvSolutionMethod.text = method
        binding.tvSolutionResult.text = result

        binding.recyclerSolutionSteps.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSolutionSteps.adapter = SolutionStepAdapter(steps)

        binding.btnBackToCalculator.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_METHOD = "arg_method"
        private const val ARG_RESULT = "arg_result"
        private const val ARG_STEPS = "arg_steps"

        fun newInstance(
            method: String,
            result: String,
            steps: ArrayList<String>
        ): SolutionFragment {
            return SolutionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_METHOD, method)
                    putString(ARG_RESULT, result)
                    putStringArrayList(ARG_STEPS, steps)
                }
            }
        }
    }
}