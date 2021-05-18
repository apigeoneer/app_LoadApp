package com.gmail.apigeoneer.loadapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.properties.Delegates

class DownloadButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0

    private val progress = 0F
    private val angle = 0F

    private val btnAnimator = ValueAnimator()
    private val circleAnimator = ValueAnimator()

    // WHAT TO DRAW: Canvas

    // HOW TO DRAW : Paint
    private val paintRect = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.FILL
    }

    private val paintText = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 60.0F
    }

    private val paintCircle = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
    }

    private var buttonState by Delegates
            .observable<ButtonState>(ButtonState.Completed) { p, old, new ->
                when (new) {
                    ButtonState.Clicked -> {
                        // do something
                    }

                    ButtonState.Loading -> {
                        // start loading the animations
                        btnAnimator()
                        circleAnimator()
                    }

                    ButtonState.Completed -> {
                        // stop the animations
                        stopAnimations()
                    }
                }
            }


    // onSizeChanged() -> NOT NEEDED, SINCE WE HAVE onMeasure()

    // If you need finer control over your view's layout parameters
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Try for a width based on our minimum
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)

        // Whatever the width ends up being, ask for a height that would let the view get as big as it can
        // val minh: Int = View.MeasureSpec.getSize(w) - textWidth.toInt() + paddingBottom + paddingTop
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h

        /**
         * Since we’re going to handle the measurement of the view we don’t need the call to super.onMeasure:
         * when we decided to override onMeasure it becomes our duty to call setMeasuredDimension(int width, int height)
         * so we don’t need the default implementation of the View class.
         */
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        // draw the download rectangle
        canvas?.drawRect(0.0F, height.toFloat() - 160, width.toFloat(), height.toFloat(), paintRect)
        // draw download text
        canvas?.drawText(ButtonText(buttonState), width.toFloat() / 3, height.toFloat() - 55, paintText)
        // draw download circle
        canvas?.drawCircle(width.toFloat() * 2 / 3 + 40, height.toFloat() - 80, 40.0F, paintCircle)

    }

    private fun btnAnimator() {
        // animate the button
    }

    private fun circleAnimator() {
        // animate the circle
    }

    private fun stopAnimations() {
        // stop the animations
    }
}