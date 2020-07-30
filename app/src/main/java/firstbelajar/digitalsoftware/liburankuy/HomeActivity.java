package firstbelajar.digitalsoftware.liburankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    LinearLayout btn_ticket, btn_balance, btn_gunung, btn_curug, btn_danau, btn_pantai;
    CircleView  btn_to_profile;
    TextView user_balance, user_ticket, nama_lengkap, bio, btn_terbaru, btn_terlaris;
    ImageView notification, search, btn_bali, btn_jogja, btn_jakarta, btn_pisa, btn_paris, pic_user;
    ProgressBar progressBarPhoto, progressBarText, progressBarSaldo, progressBarTiket;

    DatabaseReference reference, reference1;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        notification = findViewById(R.id.notification);
        search = findViewById(R.id.search);

        progressBarPhoto = findViewById(R.id.progressBarPhoto);
        progressBarText = findViewById(R.id.progressBarText);
        progressBarSaldo = findViewById(R.id.progressBarSaldo);
        progressBarTiket = findViewById(R.id.progressBarTiket);

        user_balance = findViewById(R.id.user_balance);
        user_ticket = findViewById(R.id.user_ticket);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        pic_user = findViewById(R.id.pic_user);

        btn_ticket = findViewById(R.id.btn_ticket);
        btn_balance = findViewById(R.id.btn_balance);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        btn_terbaru = findViewById(R.id.btn_terbaru);
        btn_terlaris = findViewById(R.id.btn_terlaris);

        btn_bali = findViewById(R.id.btn_bali);
        btn_jogja = findViewById(R.id.btn_jogja);
        btn_jakarta = findViewById(R.id.btn_jakarta);
        btn_pisa = findViewById(R.id.btn_pisa);
        btn_paris = findViewById(R.id.btn_paris);

        btn_gunung = findViewById(R.id.btn_gunung);
        btn_curug = findViewById(R.id.btn_curug);
        btn_danau = findViewById(R.id.btn_danau);
        btn_pantai = findViewById(R.id.btn_pantai);

        // ambil data user yang login
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference1 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new);

        progressBarPhoto.setVisibility(View.VISIBLE);
        progressBarText.setVisibility(View.VISIBLE);
        progressBarSaldo.setVisibility(View.VISIBLE);
        progressBarTiket.setVisibility(View.VISIBLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText("Hai, " + dataSnapshot.child("nama_lengkap").getValue().toString() + "!");
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("Rp " + dataSnapshot.child("user_balance").getValue().toString()); // saldo sekarang
                Picasso.with(HomeActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(pic_user);
                progressBarPhoto.setVisibility(View.GONE);
                progressBarText.setVisibility(View.GONE);
                progressBarSaldo.setVisibility(View.GONE);
                progressBarTiket.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_ticket.setText("Ada "+ dataSnapshot.getChildrenCount() + " Tiket");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Maaf, Fitur Sedang Tahap Pengembangan :(", Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Maaf, Fitur Sedang Tahap Pengembangan :(", Toast.LENGTH_SHORT).show();
            }
        });

        btn_terbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Tunggu Update Selanjutnya, ya!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_terlaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Tunggu Update Selanjutnya, ya!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gunung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetailNew = new Intent(HomeActivity.this, TicketDetailActivity.class);
                // meletakkan data kepada intent
                gotoTicketDetailNew.putExtra("jenis_tiket", "Gunung Bromo");
                startActivity(gotoTicketDetailNew);
            }
        });

        btn_curug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetailNew = new Intent(HomeActivity.this, TicketDetailActivity.class);
                // meletakkan data kepada intent
                gotoTicketDetailNew.putExtra("jenis_tiket", "Curug Cikaso");
                startActivity(gotoTicketDetailNew);
            }
        });

        btn_danau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetailNew = new Intent(HomeActivity.this, TicketDetailActivity.class);
                // meletakkan data kepada intent
                gotoTicketDetailNew.putExtra("jenis_tiket", "Danau Toba");
                startActivity(gotoTicketDetailNew);
            }
        });

        btn_pantai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetailNew = new Intent(HomeActivity.this, TicketDetailActivity.class);
                // meletakkan data kepada intent
                gotoTicketDetailNew.putExtra("jenis_tiket", "Pantai Anyer");
                startActivity(gotoTicketDetailNew);
            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoProfile = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(gotoProfile);
            }
        });

        btn_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoBalance = new Intent(HomeActivity.this, MyBalanceActivity.class);
                startActivity(gotoBalance);
            }
        });

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicket = new Intent(HomeActivity.this, MyTicketActivity.class);
                startActivity(gotoTicket);
            }
        });

        btn_bali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetail = new Intent(HomeActivity.this, TicketDetailActivity.class);
                // meletakkan data kepada intent
                gotoTicketDetail.putExtra("jenis_tiket", "Bali");
                startActivity(gotoTicketDetail);
            }
        });

        btn_jogja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetail = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotoTicketDetail.putExtra("jenis_tiket", "Yogyakarta");
                startActivity(gotoTicketDetail);
            }
        });

        btn_jakarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetail = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotoTicketDetail.putExtra("jenis_tiket", "Jakarta");
                startActivity(gotoTicketDetail);
            }
        });

        btn_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetail = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotoTicketDetail.putExtra("jenis_tiket", "Pisa");
                startActivity(gotoTicketDetail);
            }
        });

        btn_paris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTicketDetail = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotoTicketDetail.putExtra("jenis_tiket", "Paris");
                startActivity(gotoTicketDetail);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Kamu Yakin Mau Keluar?");
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setPositiveButton("Yakin Banget!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("Eh Gak Jadi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
