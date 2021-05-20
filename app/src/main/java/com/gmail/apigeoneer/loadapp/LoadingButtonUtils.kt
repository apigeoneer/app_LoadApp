package com.gmail.apigeoneer.loadapp

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

class LoadingButtonUtils(
        private val loadingButton: LoadingButton,
        private val widthSize: Int
) {

    var progress = 0F
    var angle = 0F

    private var btnValueAnimator = ValueAnimator()
    private var circleValueAnimator = ValueAnimator()

    // animate the button
    fun btnAnimator() {
        btnValueAnimator = ValueAnimator.ofFloat(0F, widthSize.toFloat()).apply {
            duration = 1000
            addUpdateListener { valueAnimator ->
                progress = valueAnimator.animatedValue as Float
                valueAnimator.repeatCount = ValueAnimator.INFINITE
                valueAnimator.repeatMode = ValueAnimator.REVERSE
                valueAnimator.interpolator = LinearInterpolator()         // default, so not really needed
                loadingButton.invalidate()
            }
            start()
        }
    }

    // animate the circle
    fun circleAnimator() {
        circleValueAnimator = ValueAnimator.ofFloat(0F, 360F).apply {
            duration = 1000
            addUpdateListener { valueAnimator ->
                angle = valueAnimator.animatedValue as Float
                valueAnimator.repeatCount = ValueAnimator.INFINITE
                valueAnimator.repeatMode = ValueAnimator.RESTART
                loadingButton.invalidate()
            }
            // disable during animation
            start()
        }
    }

    fun stopAnimations() {
        // stop the animations
        progress = 0F
        angle = 0F
        btnValueAnimator.end()
        circleValueAnimator.end()
        loadingButton.invalidate()
    }

}