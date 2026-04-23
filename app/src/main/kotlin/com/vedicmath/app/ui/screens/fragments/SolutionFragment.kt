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

        val args = arguments
        val execution = args?.getString(ARG_EXECUTION).orEmpty()
            .ifEmpty { args?.getString(ARG_METHOD_LEGACY).orEmpty() }
        val observation = args?.getString(ARG_OBSERVATION).orEmpty()
            .ifEmpty { execution }
        val result = args?.getString(ARG_RESULT).orEmpty()
        val steps = args?.getStringArrayList(ARG_STEPS).orEmpty()

        binding.tvSolutionObservation.text = observation
        binding.tvSolutionMethod.text = execution
        binding.tvSolutionResult.text = result

        binding.recyclerSolutionSteps.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SolutionStepAdapter(steps)
        }

        binding.btnBackToCalculator.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_OBSERVATION = "arg_observation"
        private const val ARG_EXECUTION = "arg_execution"
        private const val ARG_METHOD_LEGACY = "arg_method"
        private const val ARG_RESULT = "arg_result"
        private const val ARG_STEPS = "arg_steps"

        fun newInstance(
            observation: String,
            execution: String,
            result: String,
            steps: ArrayList<String>
        ): SolutionFragment = SolutionFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_OBSERVATION, observation)
                putString(ARG_EXECUTION, execution)
                putString(ARG_RESULT, result)
                putStringArrayList(ARG_STEPS, steps)
            }
        }

        fun newInstance(
            method: String,
            result: String,
            steps: ArrayList<String>
        ): SolutionFragment = SolutionFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_METHOD_LEGACY, method)
                putString(ARG_RESULT, result)
                putStringArrayList(ARG_STEPS, steps)
            }
        }
    }
}