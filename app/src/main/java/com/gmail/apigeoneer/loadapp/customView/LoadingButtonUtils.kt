package com.gmail.apigeoneer.loadapp.customView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View

class LoadingButtonUtils(
        private val loadingButton: LoadingButton,
        private val width: Int
) {

    var progress = 0F
    var angle = 0F

    private var btnValueAnimator = ValueAnimator()
    private var circleValueAnimator = ValueAnimator()

    // animate the button
    fun btnAnimator() {
        btnValueAnimator = ValueAnimator.ofFloat(0F, 2500F).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { valueAnimator ->
                progress = valueAnimator.animatedValue as Float
               // valueAnimator.interpolator = LinearInterpolator()         // default, so not really needed
                loadingButton.invalidate()
            }
            // disable during animation
            btnValueAnimator.disableDuringAnimation(loadingButton)
            start()
        }
    }

    // animate the circle
    fun circleAnimator() {
        circleValueAnimator = ValueAnimator.ofFloat(0F, 360F).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { valueAnimator ->
                angle = valueAnimator.animatedValue as Float
                loadingButton.invalidate()
            }
            // disable during animation
            circleValueAnimator.disableDuringAnimation(loadingButton)
            start()
        }
    }

    fun stopAnimations() {
        // stop the animations
        btnValueAnimator.end()
        circleValueAnimator.end()
        loadingButton.invalidate()
    }


    private fun ValueAnimator.disableDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

}