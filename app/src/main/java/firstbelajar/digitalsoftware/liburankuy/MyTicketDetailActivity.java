package firstbelajar.digitalsoftware.liburankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.Random;

public class MyTicketDetailActivity extends AppCompatActivity {

    ImageView btn_kembali, xbarcode, dialog_barcode;
    TextView xnama_wisata, xlokasi, xtime_wisata, xdate_wisata, xketentuan;
    LinearLayout btn_refund;
    ProgressBar progressBarBarcode;

    Integer value_balance, saldo_baru, value_total_bayar, value_jumlah_tiket;

    DatabaseReference reference, reference1, reference2;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        getUsernameLocal();

        xnama_wisata = findViewById(R.id.xnama_wisata);
        xlokasi = findViewById(R.id.xlokasi);
        xtime_wisata = findViewById(R.id.xtime_wisata);
        xdate_wisata = findViewById(R.id.xdate_wisata);
        xketentuan = findViewById(R.id.xketentuan);
        xbarcode = findViewById(R.id.xbarcode);
        dialog_barcode = findViewById(R.id.dialog_barcode);

        progressBarBarcode = findViewById(R.id.progressBarBarcode);

        btn_kembali = findViewById(R.id.btn_kembali);
        btn_refund = findViewById(R.id.btn_refund);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");
        final String jumlah_tiket = bundle.getString("jumlah_tiket");
        final String id_ticket = bundle.getString("id_ticket");
        final String total_bayar = bundle.getString("total_bayar");

        // define
        value_jumlah_tiket = Integer.parseInt(jumlah_tiket);
        value_total_bayar = Integer.parseInt(total_bayar);

        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference1 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(id_ticket);
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        progressBarBarcode.setVisibility(View.VISIBLE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                xlokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                xtime_wisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
                xdate_wisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                xketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                Picasso.with(MyTicketDetailActivity.this).load(dataSnapshot.child("icon_barcode").getValue().toString()).centerCrop().fit().into(xbarcode);
                progressBarBarcode.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value_balance = Integer.parseInt(dataSnapshot.child("user_balance").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        xbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                reference1.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Picasso.with(MyTicketDetailActivity.this).load(dataSnapshot.child("icon_qrcode").getValue().toString()).centerCrop().fit().into(dialog_barcode);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                AlertDialog.Builder builder = new AlertDialog.Builder(MyTicketDetailActivity.this);
                LayoutInflater factory = LayoutInflater.from(MyTicketDetailActivity.this);
                final View barcode = factory.inflate(R.layout.activity_dialog_barcode, null);
                builder.setView(barcode);
                builder.setCancelable(false);
                builder.setNeutralButton("Ok Siap!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //kosong
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
                neutralButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
                neutralButton.setLayoutParams(neutralButtonLL);
            }
        });

        btn_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyTicketDetailActivity.this);
                builder.setMessage("Tiket ini ada " + value_jumlah_tiket + ". Tiket ini akan dihapus dan uang kamu akan dikembalikan sebesar Rp" + value_total_bayar + " ke saldo kamu");
                builder.setTitle("Refund Tiket");
                builder.setCancelable(false);
                builder.setPositiveButton("Ya, Refund!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saldo_baru = value_balance + value_total_bayar;

                        // kembalikan saldo
                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("user_balance").setValue(saldo_baru.toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        // hapus tiket
                        reference1.removeValue();

                        // menampilkan pesan berhasil
                        Toast.makeText(getApplicationContext(), "Tiket berhasil dihapus dan uang kamu berhasil dikembalikan", Toast.LENGTH_LONG).show();

                        // pindah activity
                        Intent gotoMyTicket = new Intent(MyTicketDetailActivity.this, MyTicketActivity.class);// New activity
                        gotoMyTicket.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(gotoMyTicket);
                        finish();

                    }
                });
                builder.setNegativeButton("Jangan!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
