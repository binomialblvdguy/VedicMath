package com.vedicmath.app.ui.screens.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vedicmath.app.databinding.ItemSolutionStepBinding

class SolutionStepAdapter(
    private val steps: List<String>
) : RecyclerView.Adapter<SolutionStepAdapter.StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemSolutionStepBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) =
        holder.bind(position, steps[position])

    override fun getItemCount(): Int = steps.size

    class StepViewHolder(
        private val binding: ItemSolutionStepBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, step: String) {
            binding.tvStepNumber.text = (position + 1).toString()
            binding.tvStepText.text = step
        }
    }
}