package com.example.testcanvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.resulting_image);

        Button button = (Button) findViewById(R.id.btn_create_square);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSquare();
            }
        });
    }

    //Este é o método que efetivamente desenha o retângulo
    private void createSquare() {
        Bitmap bitmap = Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap = bitmap.copy(bitmap.getConfig(), true);

        Canvas canvas = new Canvas(bitmap);

        //configuracao do obj Paint com as características do retângulo
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        //desenhando...
        //canvas.drawRect(100f, 100f, 500f, 1000f, paint);
        //canvas.drawRect(mImageView.getLeft(), mImageView.getTop(), mImageView.getRight(), mImageView.getBottom(), paint);
        canvas.drawCircle(mImageView.getRight() / 2f, mImageView.getBottom() / 2f, 100, paint );

        mImageView.setImageBitmap(bitmap);
    }
}
