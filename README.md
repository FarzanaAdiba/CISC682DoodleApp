**This is a simple doodling app with a canvas.** <br/>
The top panel is for drawing pictures with five different colors. 
The lower panel is created with Linear Layout where three buttons are created as 1) ERASE, 2) OPACITY, 3) BRUSH SIZE <br/>
1. **ERASE** button can remove everything from the canvas.
2. **OPACITY** button is used to change the brush stroke opacity
3. **BRUSH SIZE** button can reduce or increase the brush size.
<br/>

It is built using two Kotlin files:<br/>
1. **MainActivity.kt:**
   This activity is for handling all the interactions. There are three main functions for changing colors and controlling opacity or brush size.<br/>
   *currentColor()* function controls the coloring options after clicking any color button at the top toolbar. <br/>
   *showOpacityDialog()* function is used to adjust the opacity when the user clicks the *OPACITY* button at the bottom toolbar. <br/>
   *showBrushSizeDialog()* function is used to adjust the brush size when the user clicks the *BRUSH SIZE* button at the bottom toolbar. <br/>
2. **DoodleView.kt:**
   This activity is to handle all the logic for painting on canvas.<br/>
   *MotionEvent* is a class that controls the touch event (UP/DOWN) or the coordinates(x or y) while any *onTouchEvent()* function is called. <br/>
   <br/>
   <br/>
  The UI Design is developed with several XML files:
   1. **activity_main.xml:** The Top toolbar with the coloring button and bottom layout with ERASE, OPACITY, and BRUSH SIZE buttons are designed and implemented here. <br/>
   2. **seekbar_view.xml:** The seek bar for changing opacity and brush is designed in this layout. The sizes can be adjusted using seekbar widget.<br/>
  Apart from that, I also modified the **color.xml** and **strings.xml** files and built some resource files under the **Drawable** folder. 
  <br/><br/>

**References:**
  1. Create a Simple Paint Application in Android using Kotlin | GeeksforGeeks, Link: https://www.youtube.com/watch?v=8mjv_iDSLcw
  2. Relative Layout. Android Developers. https://developer.android.com/develop/ui/views/layout/relative#Example
