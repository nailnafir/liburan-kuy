package com.nailnafir.liburan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView btn_buatAkun;
    EditText xusername, xpassword;
    Button btn_login;

    DatabaseReference reference;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ubah state menjadi loading
                btn_login.setEnabled(false);
                btn_login.setText("Harap Tunggu....");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    // ubah state menjadi normal lagi
                    btn_login.setEnabled(true);
                    btn_login.setText("Masuk");
                } else {
                    if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                        // ubah state menjadi normal lagi
                        btn_login.setEnabled(true);
                        btn_login.setText("Masuk");
                    } else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // ambil data password dari database
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                                    // validasi password dengan firebase
                                    if (password.equals(passwordFromFirebase)) {
                                        // menyimpan data kepada local storage (handphone)
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();
                                        // berpindah activity
                                        Intent gotoHome = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(gotoHome);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Salah!", Toast.LENGTH_SHORT).show();
                                        // ubah state menjadi normal lagi
                                        btn_login.setEnabled(true);
                                        btn_login.setText("Masuk");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username Tidak Ada!", Toast.LENGTH_SHORT).show();
                                    // ubah state menjadi loading
                                    btn_login.setEnabled(true);
                                    btn_login.setText("Masuk");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        btn_buatAkun = findViewById(R.id.btn_buatAkun);
        btn_buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goReg1 = new Intent(LoginActivity.this, RegisterOneActivity.class);
                startActivity(goReg1);
            }
        });
    }

    // cara 1 exit
//    boolean doubleBackToExitPressedOnce = false;
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }

    // cara 1 selesai

    // cara 2 mulai
    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }
    // cara 2 selesai
}
