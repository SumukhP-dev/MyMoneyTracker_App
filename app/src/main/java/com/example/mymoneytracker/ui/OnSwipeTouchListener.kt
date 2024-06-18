package com.example.mymoneytracker.ui

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

/**
 * Detects left and right swipes across a view.
 */
open class OnSwipeTouchListener(context: Context?) : OnTouchListener {
    private val gestureDetector = GestureDetector(context, GestureListener())

    open fun onSwipeLeft() {}
    open fun onSwipeRight() {}

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_DISTANCE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val distanceX: Float = e2.x - (e1?.x ?: 0).toFloat()
            val distanceY: Float = e2.y - (e1?.y ?: 0).toFloat()
            return if (abs(distanceX) > abs(distanceY) && abs(distanceX)
                > SWIPE_DISTANCE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (distanceX > 0) onSwipeRight() else onSwipeLeft()
                true
            } else {
                false
            }
        }
    }
}
