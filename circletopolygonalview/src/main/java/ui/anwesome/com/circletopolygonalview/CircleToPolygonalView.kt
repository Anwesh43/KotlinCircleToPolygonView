package ui.anwesome.com.circletopolygonalview

/**
 * Created by anweshmishra on 01/02/18.
 */
import android.graphics.*
import android.content.*
import android.view.*
class CircleToPolygonalView(ctx:Context,var n:Int = 3):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}