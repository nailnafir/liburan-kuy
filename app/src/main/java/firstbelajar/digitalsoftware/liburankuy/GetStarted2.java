package firstbelajar.digitalsoftware.liburankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStarted2 extends AppCompatActivity {

    ImageView btn_kembali;
    TextView btn_lewati;
    TextView btn_lanjut;
    Animation left2right, right2left;
    TextView intro_app2, subintro_app2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started2);

        // load animation
        left2right = AnimationUtils.loadAnimation(this, R.anim.left2right);
        right2left = AnimationUtils.loadAnimation(this, R.anim.right2left);

        intro_app2 = findViewById(R.id.intro_app2);
        subintro_app2 = findViewById(R.id.subintro_app2);

        btn_kembali = findViewById(R.id.btn_kembali);
        btn_lewati = findViewById(R.id.btn_lewati);
        btn_lanjut = findViewById(R.id.btn_lanjut);

        // run animation
        intro_app2.startAnimation(left2right);
        subintro_app2.startAnimation(right2left);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent goGs3 = new Intent(GetStarted2.this, GetStarted3.class);
//                startActivity(goGs3);

                Intent goGs3 = new Intent(GetStarted2.this, GetStarted3.class);// New activity
                goGs3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goGs3);
            }
        });
    }
}
