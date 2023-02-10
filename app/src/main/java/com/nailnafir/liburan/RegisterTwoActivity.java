package com.nailnafir.liburan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoActivity extends AppCompatActivity {

    LinearLayout btn_kembali;
    Button btn_lanjut, btn_add_photo;
    ImageView xpic_user;
    EditText xnama_lengkap, xbio;

    Uri photo_location;
    Integer photo_max = 1;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();

        btn_add_photo = findViewById(R.id.btn_add_photo);
        xpic_user = findViewById(R.id.xpic_user);
        xnama_lengkap = findViewById(R.id.xnama_lengkap);
        xbio = findViewById(R.id.xbio);

        btn_lanjut = findViewById(R.id.btn_lanjut);
        btn_kembali = findViewById(R.id.btn_kembali);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ubah state menjadi loading
                btn_lanjut.setEnabled(false);
                btn_lanjut.setText("Harap Tunggu....");

                final String nama_lengkap = xnama_lengkap.getText().toString();
                final String bio = xbio.getText().toString();

                if (nama_lengkap.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    // ubah state menjadi normal lagi
                    btn_lanjut.setEnabled(true);
                    btn_lanjut.setText("Lanjut");
                } else {
                    if (bio.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Bio Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                        // ubah state menjadi normal lagi
                        btn_lanjut.setEnabled(true);
                        btn_lanjut.setText("Lanjut");
                    } else {
                        if (photo_location == null) {
                            Toast.makeText(getApplicationContext(), "Foto Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                            // ubah state menjadi normal lagi
                            btn_lanjut.setEnabled(true);
                            btn_lanjut.setText("Lanjut");
                        } else {
                            // menyimpan kepada firebase
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                            storage = FirebaseStorage.getInstance().getReference().child("Photo Users").child(username_key_new);

                            // validasi utnuk file, apakah ada?
                            if (photo_location != null) {
                                final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                                storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uri_photo = uri.toString();
                                                reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                                reference.getRef().child("nama_lengkap").setValue(xnama_lengkap.getText().toString());
                                                reference.getRef().child("bio").setValue(xbio.getText().toString());
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                // berpindah activity
                                                Intent gotoSuccess = new Intent(RegisterTwoActivity.this, SuccessRegisterActivity.class);
                                                startActivity(gotoSuccess);
                                            }
                                        });
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Lengkapi data kamu dulu, ya!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(xpic_user);
        }
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Lengkapi data kamu dulu, ya!", Toast.LENGTH_SHORT).show();
        //        super.onBackPressed();
    }
}
