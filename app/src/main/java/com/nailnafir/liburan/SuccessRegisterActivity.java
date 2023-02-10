package com.nailnafir.liburan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterActivity extends AppCompatActivity {

    Button btn_jelajah;
    ImageView icon_success_register;
    TextView success_title, success_subtitle;
    Animation btt, ttb, app_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        // load element
        btn_jelajah = findViewById(R.id.btn_jelajah);
        icon_success_register = findViewById(R.id.icon_success_register);
        success_title = findViewById(R.id.success_title);
        success_subtitle = findViewById(R.id.success_subtitle);

        // load animasi
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        // jalankan animasi
        icon_success_register.startAnimation(app_splash);
        success_title.startAnimation(ttb);
        success_subtitle.startAnimation(ttb);
        btn_jelajah.startAnimation(btt);

        // jalankan fungsi tombol
        btn_jelajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoHome = new Intent(SuccessRegisterActivity.this, HomeActivity.class);
                startActivity(gotoHome);
            }
        });
    }
}
