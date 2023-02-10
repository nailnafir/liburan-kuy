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

public class GetStarted3 extends AppCompatActivity {

    ImageView btn_kembali;
    TextView intro_app3;
    Button btn_login, btn_buatAkun;
    Animation buttom2top_last, left2right_last, right2left_last;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started3);

        // load animation and program
        btn_kembali = findViewById(R.id.btn_kembali);
        btn_login = findViewById(R.id.btn_login);
        btn_buatAkun = findViewById(R.id.btn_buatAkun);
        intro_app3 = findViewById(R.id.intro_app3);

        // load animation
        left2right_last = AnimationUtils.loadAnimation(this, R.anim.left2right_last);
        right2left_last = AnimationUtils.loadAnimation(this, R.anim.right2left_last);
        buttom2top_last = AnimationUtils.loadAnimation(this, R.anim.buttom2top_last);

        // run animation
        intro_app3.startAnimation(buttom2top_last);
        btn_login.startAnimation(left2right_last);
        btn_buatAkun.startAnimation(right2left_last);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoLogin = new Intent(GetStarted3.this, LoginActivity.class);// New activity
                gotoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoLogin);
                finish();
            }
        });

        btn_buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoBuatAkun = new Intent(GetStarted3.this, RegisterOneActivity.class);// New activity
                gotoBuatAkun.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoBuatAkun);
                finish();
            }
        });
    }
}
