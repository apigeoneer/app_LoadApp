package com.gmail.apigeoneer.loadapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View

class DownloadButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0

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
        canvas?.drawText("DOWNLOAD", width.toFloat() / 3, height.toFloat() - 55, paintText)
        // draw download circle
        canvas?.drawCircle(width.toFloat() * 2 / 3 + 40, height.toFloat() - 80, 40.0F, paintCircle)

    }
}