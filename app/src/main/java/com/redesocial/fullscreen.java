package com.redesocial;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class fullscreen {

    public fullscreen(@NonNull Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // Pega o tamanho da tela do celular do usuário e seta na variável displayMetrics
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Seta como fullscreen
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View view = activity.getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN + View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
