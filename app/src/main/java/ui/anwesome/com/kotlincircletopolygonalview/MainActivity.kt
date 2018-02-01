package ui.anwesome.com.kotlincircletopolygonalview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circletopolygonalview.CircleToPolygonalView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val square = CircleToPolygonalView.create(this,100,4)
        val triangle =  CircleToPolygonalView.create(this,100)
        val pentagon = CircleToPolygonalView.create(this,100,5)
        val hexagon = CircleToPolygonalView.create(this,100,6)
        square.x = 0f
        triangle.x = 300f
        triangle.y = 300f
        pentagon.x = 300f
        pentagon.y = 0f
        hexagon.x = 600f
    }
}
