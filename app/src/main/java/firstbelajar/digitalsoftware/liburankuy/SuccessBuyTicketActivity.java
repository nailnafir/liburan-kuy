package firstbelajar.digitalsoftware.liburankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SuccessBuyTicketActivity extends AppCompatActivity {

    Button btn_ticket, btn_home;
    ImageView icon_success_buy;
    TextView success_title, success_subtitle;
    Animation ttb, app_splash, left2right_last, right2left_last;

    @Override
    public void onBackPressed() {
        Intent gotoHome = new Intent(SuccessBuyTicketActivity.this, HomeActivity.class);// New activity
        gotoHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoHome);
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        // load element
        icon_success_buy = findViewById(R.id.icon_success_buy);
        success_title = findViewById(R.id.success_title);
        success_subtitle = findViewById(R.id.success_subtitle);

        btn_ticket = findViewById(R.id.btn_ticket);
        btn_home = findViewById(R.id.btn_home);

        // load animasi
        left2right_last = AnimationUtils.loadAnimation(this, R.anim.left2right_last);
        right2left_last = AnimationUtils.loadAnimation(this, R.anim.right2left_last);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        // jalankan
        icon_success_buy.startAnimation(app_splash);
        success_title.startAnimation(ttb);
        success_subtitle.startAnimation(ttb);
        btn_ticket.startAnimation(left2right_last);
        btn_home.startAnimation(right2left_last);

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicket = new Intent(SuccessBuyTicketActivity.this, MyTicketActivity.class);// New activity
                gotoTicket.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoTicket);
                finishAffinity();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoHome = new Intent(SuccessBuyTicketActivity.this, HomeActivity.class);// New activity
                gotoHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoHome);
                finishAffinity();
            }
        });


    }
}
