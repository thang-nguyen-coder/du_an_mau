package com.example.library_management.model;

public class ThanhVien {
    private int matv;
    private String hoten;
    private String sdt;

    public ThanhVien(int matv, String hoten, String sdt) {
        this.matv = matv;
        this.hoten = hoten;
        this.sdt = sdt;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }
    
    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
