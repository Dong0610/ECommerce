package dong.duan.ecommerce.library

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation


fun fade_in(view: View, duration: Long=2000) {
    val animation = AlphaAnimation(0f, 1f)
    animation.duration = duration
    view.startAnimation(animation)
}
fun fade_out(view: View, duration: Long=2000) {
    val animation = AlphaAnimation(0f, 1f)
    animation.duration = duration
    view.startAnimation(animation)
}
fun zoom_in(view: View, duration: Long=2000, fromScale: Float=1f, toScale: Float=1.5f) {
    val zoomInAnimation = ScaleAnimation(fromScale, toScale, fromScale, toScale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    zoomInAnimation.duration = duration
    zoomInAnimation.fillAfter = true
    view.startAnimation(zoomInAnimation)
}

fun zoom_out(view: View, duration: Long=2000, fromScale: Float=1f, toScale: Float=0.5f) {
    val zoomOutAnimation = ScaleAnimation(fromScale, toScale, fromScale, toScale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    zoomOutAnimation.duration = duration
    zoomOutAnimation.fillAfter = true
    view.startAnimation(zoomOutAnimation)
}
fun rotate(view: View, duration: Long=2000, fromDegrees: Float=0f, toDegrees: Float=360f) {
    val rotateAnimation = RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    rotateAnimation.duration = duration
    rotateAnimation.fillAfter = true
    view.startAnimation(rotateAnimation)
}

fun move_left(view: View, duration: Long=2000) {
    val moveLeftAnimation = TranslateAnimation(0f, -view.width.toFloat(), 0f, 0f)
    moveLeftAnimation.duration = duration
    moveLeftAnimation.fillAfter = true
    view.startAnimation(moveLeftAnimation)
}

fun moveRight(view: View, duration: Long=2000) {
    val moveRightAnimation = TranslateAnimation(0f, view.width.toFloat(), 0f, 0f)
    moveRightAnimation.duration = duration
    moveRightAnimation.fillAfter = true
    view.startAnimation(moveRightAnimation)
}

fun moveUp(view: View, duration: Long=2000) {
    val moveUpAnimation = TranslateAnimation(0f, 0f, 0f, -view.height.toFloat())
    moveUpAnimation.duration = duration
    moveUpAnimation.fillAfter = true
    view.startAnimation(moveUpAnimation)
}

fun moveDown(view: View, duration: Long=2000) {
    val moveDownAnimation = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
    moveDownAnimation.duration = duration
    moveDownAnimation.fillAfter = true
    view.startAnimation(moveDownAnimation)
}


fun slideLeft(view: View, duration: Long=2000) {
    val slideLeftAnimation = TranslateAnimation(0f, -view.width.toFloat(), 0f, 0f)
    slideLeftAnimation.duration = duration
    slideLeftAnimation.fillAfter = true
    view.startAnimation(slideLeftAnimation)
}

fun slideRight(view: View, duration: Long=2000) {
    val slideRightAnimation = TranslateAnimation(0f, view.width.toFloat(), 0f, 0f)
    slideRightAnimation.duration = duration
    slideRightAnimation.fillAfter = true
    view.startAnimation(slideRightAnimation)
}

fun slideUp(view: View, duration: Long=2000) {
    val slideUpAnimation = TranslateAnimation(0f, 0f, 0f, -view.height.toFloat())
    slideUpAnimation.duration = duration
    slideUpAnimation.fillAfter = true
    view.startAnimation(slideUpAnimation)
}

fun slideDown(view: View, duration: Long=2000) {
    val slideDownAnimation = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
    slideDownAnimation.duration = duration
    slideDownAnimation.fillAfter = true
    view.startAnimation(slideDownAnimation)
}

fun bounce(view: View, duration: Long=2000) {
    val bounceAnimation = AnimationSet(true)

    // Scale animation for bounce effect
    val scaleAnimation = ScaleAnimation(1f, 1.2f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    scaleAnimation.duration = duration / 2
    scaleAnimation.fillAfter = true

    // Translate animation for returning to original position
    val translateAnimation = TranslateAnimation(0f, 0f, 0f, -0.1f * view.height)
    translateAnimation.duration = duration / 2
    translateAnimation.startOffset = duration / 2
    translateAnimation.fillAfter = true

    bounceAnimation.addAnimation(scaleAnimation)
    bounceAnimation.addAnimation(translateAnimation)

    view.startAnimation(bounceAnimation)
}
fun blink(view: View, duration: Long=2000) {
    val blinkAnimation = AlphaAnimation(1f, 0f)
    blinkAnimation.duration = duration
    blinkAnimation.fillAfter = true
    blinkAnimation.repeatMode = Animation.REVERSE
    blinkAnimation.repeatCount = Animation.INFINITE
    view.startAnimation(blinkAnimation)
}

