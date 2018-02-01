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
    data class CircleToPolygon(var x:Float,var y:Float,var r:Float,var n:Int) {
        fun draw(canvas:Canvas,paint:Paint) {
            if(n >= 3) {
                paint.color = Color.BLUE
                val deg = 360f/n
                val yr = r*Math.sin((deg/2)*Math.PI/180).toFloat()
                canvas.save()
                canvas.translate(x,y)
                for(i in 0..n-1) {
                    canvas.save()
                    canvas.rotate(deg*i)
                    val path = Path()
                    for(j in 0..deg.toInt()) {
                        val px = r*Math.cos(j*Math.PI/180).toFloat()
                        val py = yr*Math.sin(j*Math.PI/180).toFloat()
                        if(j == 0) {
                            path.moveTo(px,py)
                        }
                        else {
                            path.lineTo(px,py)
                        }
                    }
                    canvas.drawPath(path,paint)
                    canvas.restore()
                }
                canvas.restore()
            }
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:()->Unit) {

        }
    }
}