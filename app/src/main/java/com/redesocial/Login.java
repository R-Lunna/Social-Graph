package com.redesocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new fullscreen(Login.this);
        setContentView(R.layout.activity_login);

        register = (Button) findViewById(R.id.register);
        setRegister();
    }

    private void setRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, register.class));
                finish();
            }
        });
    }
}