package com.vedicmath.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vedicmath.app.databinding.ItemHelpSlideBinding
import com.vedicmath.app.domain.models.HelpSlide

class HelpPagerAdapter(private val slides: List<HelpSlide>) :
    RecyclerView.Adapter<HelpPagerAdapter.SlideViewHolder>() {

    inner class SlideViewHolder(val binding: ItemHelpSlideBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding = ItemHelpSlideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val slide = slides[position]
        holder.binding.apply {
            tvSlideTitle.text = slide.title
            tvAlgebraicFormula.text = slide.algebraicFormula
            tvVisualPattern.text = slide.patternDescription
            tvComparisonText.text = slide.efficiencyTip
        }
    }

    override fun getItemCount(): Int = slides.size
}