package com.example.cisc683doodleapp

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.cisc683doodleapp.DoodleView.Companion.colorList
import com.example.cisc683doodleapp.DoodleView.Companion.currentBrush
import com.example.cisc683doodleapp.DoodleView.Companion.pathList


class MainActivity : ComponentActivity() {

    companion object{
        var path = Path()
        var paintBrush = Paint()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blueBtn=findViewById<ImageButton>(R.id.blueColor)
        val yellowBtn=findViewById<ImageButton>(R.id.yellowColor)
        val greenBtn=findViewById<ImageButton>(R.id.greenColor)
        val magentaBtn=findViewById<ImageButton>(R.id.magentaColor)
        val redBtn=findViewById<ImageButton>(R.id.redColor)


        val eraseBtn=findViewById<Button>(R.id.eraseButton)
        val opacityBtn = findViewById<Button>(R.id.opacityButton)
        val sizeBtn=findViewById<Button>(R.id.brushSizeButton)

        blueBtn.setOnClickListener{
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            //paintBrush.setColor(Color.BLUE)
            paintBrush.color = Color.BLUE
            currentColor(paintBrush.color)
        }
        yellowBtn.setOnClickListener{
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            //paintBrush.setColor(Color.YELLOW)
            paintBrush.color = Color.YELLOW
            currentColor(paintBrush.color)
        }
        greenBtn.setOnClickListener{
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            //paintBrush.setColor(Color.GREEN)
            paintBrush.color = Color.GREEN
            currentColor(paintBrush.color)
        }
        magentaBtn.setOnClickListener{
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            //paintBrush.setColor(Color.MAGENTA)
            paintBrush.color = Color.MAGENTA
            currentColor(paintBrush.color)
        }
        redBtn.setOnClickListener{
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            //paintBrush.setColor(Color.MAGENTA)
            paintBrush.color = Color.RED
            currentColor(paintBrush.color)
        }
        eraseBtn.setOnClickListener{
            pathList.clear()
            colorList.clear()
            path.reset()

        }
        // Set up Opacity Option
        opacityBtn.setOnClickListener {
            showOpacityDialog()
        }
        sizeBtn.setOnClickListener{
            showBrushSizeDialog()
        }
    }
    private fun currentColor(color: Int){
        currentBrush=color
        path=Path()
    }
    private fun showOpacityDialog() {
        val dialogView = layoutInflater.inflate(R.layout.seekbar_view, null)
        val seekBar = dialogView.findViewById<SeekBar>(R.id.seekbar)
        seekBar.max = 255 // Max opacity
        //seekBar.progress = paintBrush.alpha
        seekBar.progress = Color.alpha(currentBrush)

        AlertDialog.Builder(this)
            .setTitle("Select Opacity")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val opacity = seekBar.progress
                // Update current brush color with the new opacity
                currentBrush = Color.argb(opacity, Color.red(currentBrush), Color.green(currentBrush), Color.blue(currentBrush))
                paintBrush.color = currentBrush
                Toast.makeText(this, "Opacity set to $opacity", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun showBrushSizeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.seekbar_view, null)
        val seekBar = dialogView.findViewById<SeekBar>(R.id.seekbar)
        seekBar.max = 50 // Max brush size
        seekBar.progress = paintBrush.strokeWidth.toInt()

        AlertDialog.Builder(this)
            .setTitle("Select Brush Size")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val size = seekBar.progress.toFloat()
                paintBrush.strokeWidth = size
                Toast.makeText(this, "Brush size set to $size", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}