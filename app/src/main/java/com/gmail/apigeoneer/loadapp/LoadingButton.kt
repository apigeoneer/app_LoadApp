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
    private val paint = Paint(ANTI_ALIAS_FLAG).apply {

      //  textSize = textSize
    }

    // onSizeChanged() -> NOT NEEDED, SINCE WE HAVE onMeasure()

    /**
     * If you need finer control over your view's layout parameters
     */
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

//    /**
//     * onDraw() -> implemented after we have defined our obj creation & measuring code
//     */
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        canvas.apply {
//           // this!!.drawText("Download", textX, textY, textPaint)
//        }
//    }
}