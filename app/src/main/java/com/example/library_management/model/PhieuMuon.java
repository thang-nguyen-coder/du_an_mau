package com.example.library_management.model;

public class PhieuMuon {
    private int mapm;
    private int matv;
    private String hotentv;
    private int mand;
    private String hotennd;
    private int masach;
    private String tensach;
    private String ngay;
    private int trangthai;
    private int tienthue;

    public PhieuMuon(int mapm, int matv, String hotentv, int mand, String hotennd, int masach, String tensach, String ngay, int trangthai, int tienthue) {
        this.mapm = mapm;
        this.matv = matv;
        this.hotentv = hotentv;
        this.mand = mand;
        this.hotennd = hotennd;
        this.masach = masach;
        this.tensach = tensach;
        this.ngay = ngay;
        this.trangthai = trangthai;
        this.tienthue = tienthue;
    }

    public PhieuMuon(int matv, int masach, int mand, String ngay, int trangthai, int tienthue) {
        this.matv = matv;
        this.mand = mand;
        this.masach = masach;
        this.ngay = ngay;
        this.trangthai = trangthai;
        this.tienthue = tienthue;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getHotentv() {
        return hotentv;
    }

    public void setHotentv(String hotentv) {
        this.hotentv = hotentv;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public String getHotennd() {
        return hotennd;
    }

    public void setHotennd(String hotennd) {
        this.hotennd = hotennd;
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

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }
}
