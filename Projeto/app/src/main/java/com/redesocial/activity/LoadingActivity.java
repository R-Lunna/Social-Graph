package com.redesocial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.redesocial.R;

// Tela de loading
public class LoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new fullscreen(MainActivity.this);
        setContentView(R.layout.activity_main);
        run();
    }

    /*
        método que instancia um objeto do tipo handler pra usar a função postDelayed pra adicionar
        pra tela de loading
     */
    private void run() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            // Chama a tela de login e fecha a de loading
            public void run() {
                startActivity(new Intent(LoadingActivity.this, Login.class));
                finish();
            }

            // Adiciona o tempo que a tela vai ficar, nesse caso 2s
        }, 2000);
    }
}