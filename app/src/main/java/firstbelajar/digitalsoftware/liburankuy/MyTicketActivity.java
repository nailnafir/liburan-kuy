package firstbelajar.digitalsoftware.liburankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class MyTicketActivity extends AppCompatActivity {

    ImageView btn_kembali;
    LinearLayout item_my_ticket;
    TextView kosong;
    ProgressBar progressBarList;

    DatabaseReference reference2;

    String USERNAME_KEY = "usernameKey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView myticket_place;
    ArrayList<MyTicket>list;
    TicketAdapter ticketAdapter;

    @Override
    public void onBackPressed() {
        Intent gotoHome = new Intent(MyTicketActivity.this, HomeActivity.class);// New activity
        gotoHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoHome);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);

        getUsernameLocal();

        kosong = findViewById(R.id.kosong);
        progressBarList = findViewById(R.id.progressBarList);
        myticket_place = findViewById(R.id.myticket_place);
        myticket_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTicket>();

        btn_kembali = findViewById(R.id.btn_kembali);
        item_my_ticket = findViewById(R.id.item_my_ticket);

        progressBarList.setVisibility(View.VISIBLE);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoHome = new Intent(MyTicketActivity.this, HomeActivity.class);// New activity
                gotoHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoHome);
                finish();
            }
        });



        // ambil data user yang login
        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // data yang mau diambil apa aja?
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                    list.add(p);
                }
                ticketAdapter = new TicketAdapter(MyTicketActivity.this, list);
                myticket_place.setAdapter(ticketAdapter);

                progressBarList.setVisibility(View.GONE);

                if (list.isEmpty()) {
                    myticket_place.setVisibility(View.GONE);
                    kosong.setVisibility(View.VISIBLE);
                }
                else {
                    myticket_place.setVisibility(View.VISIBLE);
                    kosong.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
