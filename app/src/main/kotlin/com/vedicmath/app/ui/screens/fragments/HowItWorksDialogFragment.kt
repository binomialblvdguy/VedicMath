package com.vedicmath.app.ui.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.vedicmath.app.databinding.FragmentHowItWorksDialogBinding

class HowItWorksDialogFragment : DialogFragment() {

    private var _binding: FragmentHowItWorksDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHowItWorksDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener { dismiss() }
    }

    // NEW: ensure the dialog uses full screen width minus margins for better readability
    override fun onStart() {
        super.onStart()
        // If you want full-bleed width with margins on sides, MATCH_PARENT works well.
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        // Optional: center the dialog content with a dim background if desired
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}