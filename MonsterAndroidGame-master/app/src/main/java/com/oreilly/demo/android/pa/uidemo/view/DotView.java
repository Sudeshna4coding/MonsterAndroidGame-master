package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.Dots;

/*To view the Dot*/
public class DotView extends View {

    private volatile Dots Dots;


    public DotView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }


    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }



    public DotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }




    /* convert px to DP based on the density */

    public float pxToDp(float px) {
        return px / getContext().getResources().getDisplayMetrics().density;
    }

    int cellSize = 0;
    int number_of_rows = 0;
    int number_of_columns = 0;

    public int getNumberOfColumns() {
        return number_of_columns;
    }

    public int getNumberOfRows() {
        return number_of_rows;
    }


    public int getCellSize() {
        return cellSize;
    }

    /*@ set Dots.*/
    public void setDots(Dots Dots) {
        this.Dots = Dots;
    }

    /* This will create squares and Dots*/

    @Override
    protected void onDraw(Canvas canvas) {/* Canvas comes as a package from android and is used to draw graphics*/

        Paint paint = new Paint(); /* The Paint class holds the style and color information.Comes as a part of android*/


        paint.setStyle(Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(8);

        canvas.drawRect(0, 0, cellSize * number_of_columns, cellSize * number_of_rows, paint);
        cellSize = (int) pxToDp(300);
        number_of_rows = getHeight() / getCellSize();
        number_of_columns = getWidth() / getCellSize();


        /*It draws vertical lines*/
        for (int m = 1; m <=number_of_columns; m++) {
            canvas.drawLine(m * cellSize, 0, m * cellSize, getHeight(), paint);
        }


        /*It draws horizontal lines*/
        for (int n = 1; n <=number_of_rows; n++) {
            canvas.drawLine(0, n * cellSize, getWidth(), n * cellSize, paint);
        }


        if (null == Dots) {
            return;
        }

        paint.setStyle(Style.FILL);


        Bitmap protecteddot = BitmapFactory.decodeResource(getResources(), R.drawable.protecteddot);
        Bitmap unprotecteddot = BitmapFactory.decodeResource(getResources(), R.drawable.vulnerabledot);

        protecteddot = Bitmap.createScaledBitmap(protecteddot, cellSize, cellSize, true);
        unprotecteddot = Bitmap.createScaledBitmap(unprotecteddot, cellSize, cellSize, true);
        /*It draws Dots*/
        for (Dot Dot : Dots.getDots()) {
            if (Dot.getProtectedInfo())
                canvas.drawBitmap(protecteddot, Dot.getX(), Dot.getY(), null);
            else
                canvas.drawBitmap(unprotecteddot, Dot.getX(), Dot.getY(), null);
        }
    }
}
