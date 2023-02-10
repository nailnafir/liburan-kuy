package com.nailnafir.liburan;

public class MyTicket {
    String nama_wisata, lokasi, jumlah_tiket, id_ticket, total_bayar;

    public MyTicket() {
    }

    public MyTicket(String nama_wisata, String lokasi, String jumlah_tiket, String id_ticket, String total_bayar) {
        this.nama_wisata = nama_wisata;
        this.lokasi = lokasi;
        this.jumlah_tiket = jumlah_tiket;
        this.id_ticket = id_ticket;
        this.total_bayar = total_bayar;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJumlah_tiket() {
        return jumlah_tiket;
    }

    public void setJumlah_tiket(String jumlah_tiket) {
        this.jumlah_tiket = jumlah_tiket;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }
}
