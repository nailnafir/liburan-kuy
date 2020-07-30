package firstbelajar.digitalsoftware.liburankuy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTicket> myTicket;

    public TicketAdapter(Context c, ArrayList<MyTicket> p) {
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // membuat inflater
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // membuat item yang ingin ditampilkan
        myViewHolder.xnama_wisata.setText(myTicket.get(i).getNama_wisata());
        myViewHolder.xlokasi.setText(myTicket.get(i).getLokasi());
        myViewHolder.xjumlah_tiket.setText(myTicket.get(i).getJumlah_tiket());

        // digunakan untuk intent
        final String getNamaWisata = myTicket.get(i).getNama_wisata();
        final String getJumlahTiket = myTicket.get(i).getJumlah_tiket();
        final String getTotalBayar = myTicket.get(i).getTotal_bayar();
        final String getIdTicket = myTicket.get(i).getId_ticket();
//        final String getTotalBayar = myTicket.get(i).getValue_total_bayar();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pindah activity dan mengirim data intent ke activity baru
                Intent gotoMyTicketDetails = new Intent(context, MyTicketDetailActivity.class);
//                Intent gotoDialogBarcode = new Intent(context, DialogBarcode.class);
                gotoMyTicketDetails.putExtra("nama_wisata", getNamaWisata);
                gotoMyTicketDetails.putExtra("jumlah_tiket", getJumlahTiket);
                gotoMyTicketDetails.putExtra("total_bayar", getTotalBayar);
                gotoMyTicketDetails.putExtra("id_ticket", getIdTicket);
//                gotoDialogBarcode.putExtra("nama_wisata", getNamaWisata);
//                context.startActivity(gotoDialogBarcode);
                context.startActivity(gotoMyTicketDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_wisata, xlokasi, xjumlah_tiket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
        }
    }
}
