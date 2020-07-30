package firstbelajar.digitalsoftware.liburankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation btt, ttb;
    ImageView app_logo;
    TextView app_subtitle;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load animation
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        // load element
        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_subtitle);

        // run animation
        app_logo.startAnimation(ttb);
        app_subtitle.startAnimation(btt);

        getUsernameLocal();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

        if (username_key_new.isEmpty()) {
            // setting timer 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // mengubah activity ke activity lain
                    Intent goGs1 = new Intent(MainActivity.this, GetStarted1.class);
                    startActivity(goGs1);
                    finish();
                }
            }, 3000); // 3000ms = 3 detik
        } else {
            // setting timer 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // mengubah activity ke activity lain
                    Intent goLogin = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(goLogin);
                    finish();
                }
            }, 3000); // 3000ms = 3 detik
        }
    }
}
