package com.nailnafir.liburan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class GetStarted1 extends AppCompatActivity {

    TextView btn_lanjut;
    TextView btn_lewati;
    Animation left2right, right2left;
    TextView intro_app1, subintro_app1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started1);

        // load animation
        left2right = AnimationUtils.loadAnimation(this, R.anim.left2right);
        right2left = AnimationUtils.loadAnimation(this, R.anim.right2left);

        btn_lanjut = findViewById(R.id.btn_lanjut);
        btn_lewati = findViewById(R.id.btn_lewati);

        intro_app1 = findViewById(R.id.intro_app1);
        subintro_app1 = findViewById(R.id.subintro_app1);

        // run animation
        intro_app1.startAnimation(left2right);
        subintro_app1.startAnimation(right2left);

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent goGs2 = new Intent(GetStarted1.this, GetStarted2.class);
//                startActivity(goGs2);

                Intent goGs2 = new Intent(GetStarted1.this, GetStarted2.class);// New activity
                goGs2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goGs2);
            }
        });

        btn_lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent goGs3 = new Intent(GetStarted1.this, GetStarted3.class);
//                startActivity(goGs3);

                Intent goGs3 = new Intent(GetStarted1.this, GetStarted3.class);// New activity
                goGs3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goGs3);
                finish();
            }
        });

    }
}
