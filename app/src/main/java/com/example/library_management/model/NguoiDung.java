package com.example.library_management.model;

public class NguoiDung {
    private int mand;
    private String hoten;
    private String sdt;
    private String diachi;
    private String taikhoan;
    private String matkhau;
    private int role;

    public NguoiDung(int mand, String hoten, String sdt, String diachi, String taikhoan, String matkhau, int role) {
        this.mand = mand;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.role = role;
    }

    public NguoiDung(String hoten, String sdt, String diachi, String taikhoan, String matkhau) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
