package ui.anwesome.com.circletopolygonalview

/**
 * Created by anweshmishra on 01/02/18.
 */
import android.app.Activity
import android.graphics.*
import android.content.*
import android.view.*
class CircleToPolygonalView(ctx:Context,var n:Int = 3):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = CircleToPolygonRenderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class CircleToPolygon(var x:Float,var y:Float,var r:Float,var n:Int) {
        val state = CircleToPolygonState()
        fun draw(canvas:Canvas,paint:Paint) {
            if(n >= 3) {
                paint.color = Color.parseColor("#311B92")
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = r/20
                paint.strokeCap = Paint.Cap.ROUND
                val deg = 360f/n
                val yr = r*Math.sin(((180-deg)/2)*Math.PI/180).toFloat()
                canvas.save()
                canvas.translate(x,y)
                val gap = deg.toInt()
                val offset = 90 - gap/2
                for(i in 0..n-1) {
                    canvas.save()
                    canvas.rotate(deg*i)
                    val path = Path()
                    for(j in offset..(offset+gap)) {
                        val px = r*Math.cos(j*Math.PI/180).toFloat()
                        val py = yr*state.scale + r*(1-state.scale)*Math.sin(j*Math.PI/180).toFloat()
                        if(j == offset) {
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
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
        }
    }
    data class CircleToPolygonState(var scale:Float = 0f, var dir:Float = 0f, var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                dir = 1-2*scale
                startcb()
            }
        }
    }
    data class CircleToPolygonAnimator(var view:View,var animated:Boolean = false) {
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun update(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class CircleToPolygonRenderer(var view:CircleToPolygonalView,var time:Int = 0) {
        val animator = CircleToPolygonAnimator(view)
        var circleToPolygon:CircleToPolygon ?= null
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                circleToPolygon = CircleToPolygon(w/2,h/2,Math.min(w,h)*0.4f,view.n)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            circleToPolygon?.draw(canvas,paint)
            time++
            animator.update {
                circleToPolygon?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            circleToPolygon?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity:Activity,r:Int,vararg sides:Int):CircleToPolygonalView {
            val view = CircleToPolygonalView(activity)
            var size = (r*5/2)
            if(sides.size == 1 && sides[0] >= 3) {
                view.n = sides[0]
            }
            activity.addContentView(view,ViewGroup.LayoutParams(size,size))
            return view
        }
    }
}