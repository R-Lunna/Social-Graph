package com.redesocial.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.redesocial.R;

public class DrawGraph extends AppCompatActivity {

    private ImageView imgGraph;
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private float x = 0;
    private float y = 0;
    private float xTouch = 0;
    private float yTouch = 0;
    private final int INCREMENT = 25;
    private int radius = 100;
    private final int RADIUS_INCREMENT = 10;
    private ScaleGestureDetector scaleGestureDetector;


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.fragment_graph );

        imgGraph = (ImageView) findViewById( R.id.imgGraph );

        configZoom();
        configPainter();
    }

    */

    public void configZoom()
    {
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                if( detector.getScaleFactor() > 1 )
                    radius += RADIUS_INCREMENT;
                else
                if( radius > 10)
                    radius -= RADIUS_INCREMENT;
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
    }


    public void configPainter()
    {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        x = size.x / 2f;
        y = size.y / 2f;

        //Log.i("Width: ", Float.toString(x));
        //Log.i("Height: ", Float.toString(y));

        paint = new Paint( Paint.ANTI_ALIAS_FLAG );
        paint.setColor( Color.RED );
        paint.setStyle( Paint.Style.FILL_AND_STROKE );

        bitmap =  Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);

        bitmap = bitmap.copy( bitmap.getConfig(), true );

        canvas = new Canvas( bitmap );

        drawGraph();
    }

    private void drawGraph()
    {
        canvas.drawColor( Color.WHITE );
        canvas.drawCircle( x, y, radius, paint );

        imgGraph.setImageBitmap( bitmap );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        scaleGestureDetector.onTouchEvent( event );

        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            float eventX = event.getX();
            float eventY = event.getY();

            if( Math.abs(eventX - xTouch) >= Math.abs(eventY - yTouch))
            {
                if ( eventX > xTouch )
                    x += INCREMENT;
                else
                if(eventX < xTouch)
                    x -= INCREMENT;
            }
            else
            {
                if (eventY > yTouch )
                    y += INCREMENT;
                else
                if((eventY < yTouch ))
                    y -= INCREMENT;
            }

            xTouch = event.getX();
            yTouch = event.getY();

        }

        drawGraph();
        return super.onTouchEvent(event);
    }

}
