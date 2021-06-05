package com.gmail.apigeoneer.loadapp.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.gmail.apigeoneer.loadapp.R
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "LoadingButton"
    }

    private var widthSize = 0
    private var heightSize = 0

    private var btnBgColor = 0
    private var btnTextColor = 0
    private var btnCircleColor = 0
    private var btnText = "DOWNLOAD"

    private val loadingButtonUtils = LoadingButtonUtils(this, width)

    // HOW TO DRAW : Paint
    private val paintRect = Paint()

    private val paintText = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 55.0F
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private val paintCircle = Paint()

    // Delegates.observable -> called when the button's state changes, but not when launching the app
    private var btnState by Delegates
            .observable<ButtonState>(ButtonState.Completed) { p, oldState, newState ->
                when (newState) {
                    ButtonState.Clicked -> {
                        Log.d(TAG, "ButtonState.Clicked: Download started")
                        Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
                        // do something
                    }

                    ButtonState.Loading -> {
                        Log.d(TAG, "ButtonState.Loading: Downloading...")
                        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
                        btnText = "Downloading..."
                        // start loading the animations
                        loadingButtonUtils.btnAnimator()
                        loadingButtonUtils.circleAnimator()
                    }

                    ButtonState.Completed -> {
                        Log.d(TAG, "ButtonState.Complete: Download completed")
                        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show()
                        btnText = "DOWNLOAD"
                        loadingButtonUtils.progress = 0F
                        loadingButtonUtils.angle = 0F
                        // stop the animations
                        loadingButtonUtils.stopAnimations()
                    }
                }
            }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0).apply {
            btnBgColor = getColor(R.styleable.LoadingButton_btnBgColor, 0)
            btnTextColor = getColor(R.styleable.LoadingButton_btnTextColor, 0)
            btnCircleColor = getColor(R.styleable.LoadingButton_btnCircleColor, 0)
        }

        paintRect.color = btnBgColor
        paintText.color = btnTextColor
        paintCircle.color = btnCircleColor
    }

    // onSizeChanged() -> NOT NEEDED, SINCE WE HAVE onMeasure()

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
        // WHAT TO DRAW: Canvas
        super.onDraw(canvas)

        // draw the download rectangle
        paintRect.color = Color.GRAY
        canvas!!.drawRect(0F, 0F, widthSize.toFloat(), heightSize.toFloat(), paintRect)

        // draw download text
        canvas.drawText(
                btnText,
                width.toFloat() / 3,
                height.toFloat() - 55,
                paintText
        )

        if (btnState == ButtonState.Loading) {
            // draw the download fill
            paintRect.color = getColor(context, R.color.colorAccent)
            canvas.drawRect(0F, 0F, loadingButtonUtils.progress, heightSize.toFloat(), paintRect)

            // draw download circle
            paintCircle.color = Color.YELLOW
            //canvas?.drawCircle(width.toFloat() * 2 / 3 + 40, height.toFloat() - 80, 40.0F, paintCircle)     // WRONG APPROACH
            canvas.drawArc(
                widthSize.toFloat() - 150f,
                heightSize.toFloat() / 2 - 50f,
                widthSize.toFloat()-50f,
                heightSize.toFloat() / 2 + 50f,
                0.0F,
                loadingButtonUtils.angle,
                true,
                paintCircle
            )    // copied

            // draw download text
            canvas.drawText(
                btnText,
                width.toFloat() / 3,
                height.toFloat() - 55,
                paintText
            )
        }
    }

    // Create a member function that sets the button state
    fun setButtonState(buttonState: ButtonState) {
        btnState = buttonState
    }
}