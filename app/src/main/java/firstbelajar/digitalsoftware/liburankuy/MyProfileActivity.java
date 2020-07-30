package firstbelajar.digitalsoftware.liburankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {

    ImageView btn_kembali, pic_user;
    Button btn_edit_profile, btn_logout;
    TextView nama_lengkap, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getUsernameLocal();

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_kembali = findViewById(R.id.btn_kembali);
        btn_logout = findViewById(R.id.btn_logout);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        pic_user = findViewById(R.id.pic_user);

        // ambil data user yang login
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.with(MyProfileActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(pic_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoEditProfil = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(gotoEditProfil);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // menghapus isi/nilai/value dari username local
                // menyimpan data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                Intent gotoLogin = new Intent(MyProfileActivity.this, LoginActivity.class); // New activity
                gotoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoLogin);
                finishAffinity();
            }
        });



    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
