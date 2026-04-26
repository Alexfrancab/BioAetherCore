package com.bioaether.core;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sin barra de título, pantalla completa oscura
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#050b12"));
        window.setNavigationBarColor(Color.parseColor("#050b12"));

        setContentView(R.layout.activity_splash);

        TextView title = findViewById(R.id.splash_title);
        TextView subtitle = findViewById(R.id.splash_subtitle);
        TextView version = findViewById(R.id.splash_version);

        // Animación fade + scale en el título
        AnimationSet anim = new AnimationSet(true);

        AlphaAnimation fade = new AlphaAnimation(0f, 1f);
        fade.setDuration(900);

        ScaleAnimation scale = new ScaleAnimation(
            0.85f, 1f, 0.85f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(900);

        anim.addAnimation(fade);
        anim.addAnimation(scale);
        title.startAnimation(anim);

        // Subtítulo aparece con delay
        AlphaAnimation fadeSubtitle = new AlphaAnimation(0f, 1f);
        fadeSubtitle.setDuration(700);
        fadeSubtitle.setStartOffset(600);
        fadeSubtitle.setFillAfter(true);
        subtitle.startAnimation(fadeSubtitle);

        AlphaAnimation fadeVersion = new AlphaAnimation(0f, 1f);
        fadeVersion.setDuration(700);
        fadeVersion.setStartOffset(900);
        fadeVersion.setFillAfter(true);
        version.startAnimation(fadeVersion);

        // Lanzar MainActivity después de 2.2s
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 2200);
    }
}
