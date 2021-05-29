package com.redesocial.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.redesocial.datastructures.Graph;

public class DrawGraph extends View {

    private Bitmap bitmap;
    private Canvas myCanvas;
    private Paint paintVertex;
    private Paint paintText;
    private Paint paintLine;
    private ScaleGestureDetector scaleGestureDetector;
    private int altura; // Altura da tela do dispositivo
    private int largura; // Largura da tela do dispositivo
    private Graph graph;
    private float cameraPositionX = 0;
    private float cameraPositionY = 0;
    private float xTouch = 0;
    private float yTouch = 0;
    private final int INCREMENT_CAMERA_POSITION = 100;
    private final int RADIUS_INCREMENT = 10;
    private int relativeDistance = 0;
    private int radius = 100;

    public DrawGraph(Context context) {
        this( context, null);
    }

    public DrawGraph(Context context, AttributeSet attributeSet)
    {
        super( context, attributeSet );

        configPaint();
        configZoom();
    }

    /* Configura o grafo */
    public void setGraph( Graph graph )
            throws NullPointerException
    {
        if( graph == null )
            throw new NullPointerException("Graph is null");
        this.graph = graph;
    }

    public void configPaint()
    {
        paintVertex = new Paint( Paint.ANTI_ALIAS_FLAG );
        paintVertex.setColor( Color.WHITE );

        paintText = new Paint( Paint.ANTI_ALIAS_FLAG );
        paintText.setColor( Color.RED );
        paintText.setElegantTextHeight( true );
        paintText.setTextSize(75);

        paintLine = new Paint( Paint.ANTI_ALIAS_FLAG ) ;
        paintLine.setColor( Color.rgb(64, 109, 191) );
        paintLine.setStrokeWidth(5);
        paintLine.setStyle(Paint.Style.STROKE);
    }

    /* Método a ser chamado no momento da criação do objeto */
    public void initialConfig(DisplayMetrics displayMetrics )
            throws NullPointerException
    {
        if( displayMetrics == null )
            throw new NullPointerException("DisplayMetrics is null");

        altura = displayMetrics.heightPixels;
        largura = displayMetrics.widthPixels;

        cameraPositionX = largura / 2f;
        cameraPositionY = altura / 2f;

        bitmap = Bitmap.createBitmap( largura, altura, Bitmap.Config.ARGB_8888 );

        myCanvas = new Canvas( bitmap );

    }

    @Override
    public void onDraw( Canvas canvas )
    {
        canvas.save();
        myCanvas.drawColor( Color.rgb(33,33,33) );

        /* Desenhando o gráfo */
        drawGraph();

        canvas.drawBitmap( bitmap, 0, 0, paintVertex);
        canvas.restore();
    }

    public void drawGraph()
    {
        /* Posição absoluta do vértice */
        int nodePositionX = 0;
        int nodePositionY = 0;

        /* Desenha Arestas */
        for( int indexVertex = 0; indexVertex < graph.length(); indexVertex++ )
        {
            nodePositionX = graph.getVertexX( indexVertex );
            nodePositionY = graph.getVertexY( indexVertex );

            /* Percorre as arestas do vértice */
            for( int indexEdge = 0; indexEdge < graph.getVertexLength( indexVertex ); indexEdge++ )
            {
                /* Desenha a aresta */
                myCanvas.drawLine( nodePositionX + cameraPositionX, nodePositionY + cameraPositionY, graph.getEdgeX( indexVertex, indexEdge ) + cameraPositionX, graph.getEdgeY( indexVertex, indexEdge) + cameraPositionY, paintLine);
            }
        }

        /* Desenha vértices */
        for( int indexVertex = 0; indexVertex < graph.length(); indexVertex++ )
        {
            nodePositionX = graph.getVertexX( indexVertex );
            nodePositionY = graph.getVertexY( indexVertex );

            /* Desenha o vértice */
            if( nodePositionX < largura / 2 )
                myCanvas.drawCircle( nodePositionX + cameraPositionX - relativeDistance,  nodePositionY + cameraPositionY, radius, paintVertex);
            else
                myCanvas.drawCircle( nodePositionX + cameraPositionX + relativeDistance,  nodePositionY + cameraPositionY, radius, paintVertex);

        }

        /* Desenha Nomes */
        for( int indexVertex = 0; indexVertex < graph.length(); indexVertex++ )
        {
            nodePositionX = graph.getVertexX( indexVertex );
            nodePositionY = graph.getVertexY( indexVertex );

            /* Desenha o nome do usuário */
            myCanvas.drawText( graph.getVertexData( indexVertex ).getName(), nodePositionX - radius + cameraPositionX, nodePositionY +  radius + 60 + cameraPositionY, paintText );

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        scaleGestureDetector.onTouchEvent( event );

        switch ( event.getAction() )
        {
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float eventY = event.getY();

                if( Math.abs(eventX - xTouch) >= Math.abs(eventY - yTouch))
                {
                    if ( eventX > xTouch )
                        cameraPositionX += INCREMENT_CAMERA_POSITION;
                    else
                    if(eventX < xTouch)
                        cameraPositionX -= INCREMENT_CAMERA_POSITION;
                }
                else
                {
                    if (eventY > yTouch )
                        cameraPositionY += INCREMENT_CAMERA_POSITION;
                    else
                    if((eventY < yTouch ))
                        cameraPositionY -= INCREMENT_CAMERA_POSITION;
                }

                xTouch = event.getX();
                yTouch = event.getY();
                invalidate();
                return true;

            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_UP:
                return  true;
        }

        return super.onTouchEvent(event);
    }

    public void configZoom()
    {
        scaleGestureDetector = new ScaleGestureDetector( getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                if( detector.getScaleFactor() > 1 )
                {
                    radius += RADIUS_INCREMENT;
                    relativeDistance += RADIUS_INCREMENT;
                }

                else
                if( radius > 10 )
                {
                    radius -= RADIUS_INCREMENT;
                    relativeDistance -= RADIUS_INCREMENT;
                }

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

}
