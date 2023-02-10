package com.nailnafir.liburan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutActivity extends AppCompatActivity {

    ImageView btn_kembali, notif_danger;
    LinearLayout btn_bayar;
    Button btn_minus, btn_plus;
    TextView text_btn_bayar, text_jumlah_beli, text_total_uang, text_total_bayar, text_harga_tiket, nama_wisata, lokasi, ketentuan;
    Integer value_jumlah_beli = 1;
    Integer total_uang = 0;
    Integer value_total_bayar = 0;
    Integer value_harga_tiket = 0;
    Integer sisa_uang = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    // generate nomor transaksi secara random
    // karena ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        text_jumlah_beli = findViewById(R.id.text_jumlah_beli);
        text_harga_tiket = findViewById(R.id.text_harga_tiket);
        text_total_uang = findViewById(R.id.text_total_uang);
        text_total_bayar = findViewById(R.id.text_total_bayar);
        notif_danger = findViewById(R.id.notif_danger);

        btn_kembali = findViewById(R.id.btn_kembali);
        text_btn_bayar = findViewById(R.id.text_btn_bayar);
        btn_bayar = findViewById(R.id.btn_bayar);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // setting value awal baru untuk beberapa komponen
        text_jumlah_beli.setText(value_jumlah_beli.toString());

        // secara default awal, kita sembunyikan tombol minus
        btn_minus.animate().alpha(0).setDuration(0).start();
        btn_minus.setEnabled(false);
        notif_danger.setVisibility(View.GONE);

        // mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_uang = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                text_total_uang.setText("Rp " + total_uang + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari Firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                value_harga_tiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                value_total_bayar = value_harga_tiket * value_jumlah_beli;

                text_harga_tiket.setText("Rp " + value_harga_tiket.toString());
                text_total_bayar.setText("Rp " + value_total_bayar + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value_jumlah_beli-=1;
                text_jumlah_beli.setText(value_jumlah_beli.toString());
                if (value_jumlah_beli < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                if (value_jumlah_beli < 10){
                    btn_plus.animate().alpha(1).setDuration(300).start();
                    btn_plus.setEnabled(true);
                }
                value_total_bayar = value_harga_tiket * value_jumlah_beli;
                text_total_bayar.setText("Rp " + value_total_bayar + "");
                if (value_total_bayar < total_uang) {
                    btn_bayar.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_bayar.setEnabled(true);
                    text_total_uang.setTextColor(Color.parseColor("#203DD1"));
                    notif_danger.setVisibility(View.GONE);
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value_jumlah_beli+=1;
                text_jumlah_beli.setText(value_jumlah_beli.toString());
                if (value_jumlah_beli > 1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                if (value_jumlah_beli > 9){
                    btn_plus.animate().alpha(0).setDuration(300).start();
                    btn_plus.setEnabled(false);
                }
                value_total_bayar = value_harga_tiket * value_jumlah_beli;
                text_total_bayar.setText("Rp " + value_total_bayar + "");
                if (value_total_bayar > total_uang) {
                    btn_bayar.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_bayar.setEnabled(false);
                    text_total_uang.setTextColor(Color.parseColor("#D1206B"));
                    notif_danger.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // menyimpan data user kepada firebase dan membuat tabel baru
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(value_jumlah_beli.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("total_bayar").setValue(value_total_bayar.toString());

                        // pindah activity
                        Intent gotoSuccessPay = new Intent(TicketCheckoutActivity.this, SuccessBuyTicketActivity.class);// New activity
                        gotoSuccessPay.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(gotoSuccessPay);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // update data saldo kepada users (yang saat ini login)
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_uang = total_uang - value_total_bayar;
                        reference4.getRef().child("user_balance").setValue(sisa_uang);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
