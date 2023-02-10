package com.nailnafir.liburan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailActivity extends AppCompatActivity {

    ImageView btn_kembali, header_ticket_detail;
    LinearLayout btn_beli;
    TextView photo_spot_ticket, wifi_ticket, festival_ticket, short_desc;
    ProgressBar progressBarHeader;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        short_desc = findViewById(R.id.short_desc);

        btn_kembali = findViewById(R.id.btn_kembali);
        btn_beli = findViewById(R.id.btn_beli);

        progressBarHeader = findViewById(R.id.progressBarHeader);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        progressBarHeader.setVisibility(View.VISIBLE);

        //mengambil data dari Firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data baru
                Picasso.with(TicketDetailActivity.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_ticket_detail);
                photo_spot_ticket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(dataSnapshot.child("is_festival").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());
                progressBarHeader.setVisibility(View.GONE);
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

        btn_beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoBayar = new Intent(TicketDetailActivity.this, TicketCheckoutActivity.class);

                // meletakkan data kepada intent
                gotoBayar.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotoBayar);
            }
        });
    }
}
