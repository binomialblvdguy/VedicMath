package com.vedicmath.app.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class VedicPatternView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 1. Data storage for the digits
    private var multiplicand: String = ""
    private var multiplier: String = ""

    // Paint for the Star/Cross lines - set to High-Contrast Yellow
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFEB3B")
        strokeWidth = 8f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    // Paint for the number dots - set to White for distinction
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    // Paint for the digit text
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFEB3B")
        textSize = 40f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    /**
     * 2. The missing function that clears the Fragment error
     */
    fun setDigits(m1: String, m2: String) {
        this.multiplicand = m1
        this.multiplier = m2
        invalidate() // Forces the view to redraw with new numbers
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Ensure the View background itself is black for the "Architect" theme
        canvas.drawColor(Color.BLACK)

        // Your existing logic for drawing the V&X lines goes here.
        // It will use 'multiplicand' and 'multiplier' to decide where to draw lines.
    }
}