package com.example.library_management.model;

public class Sach {
    private int masach;
    private String tensach;
    private String tacgia;
    private String giaban;
    private int maloai;
    private int soluong;
    private String trangthai;
    public Sach(int masach, String tensach, String tacgia, String giaban, int maloai, int soluong, String trangthai) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giaban = giaban;
        this.maloai = maloai;
        this.soluong = soluong;
        this.trangthai = trangthai;
    }

    public Sach(int masach, String tensach, int soluong) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluong = soluong;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getGiaban() {
        return giaban;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

}
