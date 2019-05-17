package androidx.constraintlayout.motion.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class MultiStateMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private var lastTouchEvent: MotionEvent? = null

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (this.mScene != null) {
            if (this.mScene == null || !this.mScene.moveWhenScrollAtTop || this.mTransitionPosition != 1.0f || !target.canScrollVertically(
                    -1
                )
            ) {
                if (currentState == endState || currentState == startState) {
                    // Taken from motion scene code to handle multi state transitions for scrolling motion layout
                    // This will handle the same for nested scroll views
                    val transition = mScene.bestTransitionFor(currentState, dx.toFloat(), -dy.toFloat(), lastTouchEvent)
                    if (transition != null && transition != mScene.mCurrentTransition) {
                        setTransition(transition)

                        Log.d(
                            "MultiStateMotionLayout",
                            "Transition state changed from nested scroll : ${transition.startConstraintSetId} - ${transition.endConstraintSetId}"
                        )
                    }
                }
            }
        }

        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        lastTouchEvent = event
        return super.onTouchEvent(event)
    }
}