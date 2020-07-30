package firstbelajar.digitalsoftware.liburankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DialogBarcode extends AppCompatActivity {

    ImageView dialog_barcode;
//    TextView xnama_wisata;

//    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_barcode);

        dialog_barcode = findViewById(R.id.dialog_barcode);
//        xnama_wisata = findViewById(R.id.xnama_wisata);
//
//        // mengambil data dari intent
//        Bundle bundle = getIntent().getExtras();
//        final String nama_wisata_baru = bundle.getString("nama_wisata");
//
//        // mengambil data dari firebase
//        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
//
//        // set data
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                xnama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
//                Picasso.with(DialogBarcode.this).load(dataSnapshot.child("icon_qrcode").getValue().toString()).centerCrop().fit().into(dialog_barcode);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
