package com.example.cisc683doodleapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Path
import android.view.MotionEvent
import android.view.ViewGroup
import com.example.cisc683doodleapp.MainActivity.Companion.paintBrush
import com.example.cisc683doodleapp.MainActivity.Companion.path

class DoodleView : View {

    var params : ViewGroup.LayoutParams? = null

    companion object{
        var pathList= ArrayList<Path>() //will store all the path of the buttons
        var colorList=ArrayList<Int>()
        var currentBrush=Color.BLACK;

    }
    private var loadedBitmap: Bitmap? = null
    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()

    }
    private fun init(){
        paintBrush.isAntiAlias=true
        paintBrush.color=currentBrush
        paintBrush.style= Paint.Style.STROKE
        paintBrush.strokeJoin= Paint.Join.ROUND
        paintBrush.strokeWidth=8f

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y)
                pathList.add(Path(path))
                colorList.add(currentBrush)

            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas) {
        // Draw the loaded bitmap first (if present)
        loadedBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }
        for(i in pathList.indices){
            paintBrush.color=colorList[i]
            canvas.drawPath(pathList[i],paintBrush)
        }
        invalidate()
    }
    // Method to capture the current canvas as a bitmap
    fun getBitmap(): Bitmap {
        // Create a blank bitmap with the same dimensions as the view
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw the background (optional - can be white or transparent)
        canvas.drawColor(Color.WHITE)

        // Draw all the paths stored in pathList
        for (i in pathList.indices) {
            paintBrush.color = colorList[i]
            canvas.drawPath(pathList[i], paintBrush)
        }

        return bitmap
    }
    // Method to set a loaded bitmap and refresh the view
    fun setBitmap(bitmap: Bitmap) {
        loadedBitmap = bitmap // Store the loaded bitmap
        pathList.clear() // Clear existing paths
        colorList.clear()
        invalidate() // Redraw the view
    }



}