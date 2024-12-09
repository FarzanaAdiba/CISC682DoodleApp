package com.example.cisc683doodleapp

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cisc683doodleapp.DoodleView.Companion.colorList
import com.example.cisc683doodleapp.DoodleView.Companion.currentBrush
import com.example.cisc683doodleapp.DoodleView.Companion.pathList
import java.io.File
import java.io.FileOutputStream


class MainActivity : ComponentActivity() {

    companion object{
        var path = Path()
        var paintBrush = Paint()
    }
    // Activity Result Contract for picking an image
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            loadCanvasFromImage(uri) // Call the method to load the selected image
        }
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
        val saveBtn = findViewById<Button>(R.id.saveButton)
        val loadBtn = findViewById<Button>(R.id.loadButton)


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
        saveBtn.setOnClickListener{
            saveCanvasToStorage()

        }

        loadBtn.setOnClickListener{
            pickImage.launch("image/*")
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
    private fun saveCanvasToStorage() {
        val doodleView = findViewById<DoodleView>(R.id.doodleView)
        val bitmap = doodleView.getBitmap()

        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DoodleApp")
        if (!directory.exists() && !directory.mkdirs()) {
            Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show()
            return
        }


        val fileName = "Doodle_${System.currentTimeMillis()}.png"
        val file = File(directory, fileName)

        try {
            val outputStream = FileOutputStream(file)
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)) {
                Toast.makeText(this, "Bitmap compression failed", Toast.LENGTH_SHORT).show()
                return
            }
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Drawing saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving drawing: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadCanvasFromImage(uri: Uri) {
        try {
            // Open the InputStream from the selected URI
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream) // Decode into a Bitmap
            inputStream?.close()

            // Pass the Bitmap to DoodleView
            val doodleView = findViewById<DoodleView>(R.id.doodleView)
            doodleView.setBitmap(bitmap) // Display the image on the canvas
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }

}