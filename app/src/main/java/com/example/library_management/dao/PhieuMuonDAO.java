package com.example.library_management.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_management.database.Dbhelper;
import com.example.library_management.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    private Dbhelper dbhelper;
    public PhieuMuonDAO (Context context){
        dbhelper = new Dbhelper(context);
    }
    public ArrayList<PhieuMuon> getDsPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select pm.mapm, pm.matv, tv.hoten, pm.mand, nd.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue from phieumuon pm, thanhvien tv, nguoidung nd, sach sc where pm.matv = tv.matv and pm.mand = nd.mand and pm.masach = sc.masach", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0),cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7),cursor.getInt(8),cursor.getInt(9)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean ThayDoiTrangThai(int mapm){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trasach", 1);
        long check = sqLiteDatabase.update("phieumuon", values, "mapm = ?", new String[]{String.valueOf(mapm)});
        return check != -1;
    }
    public boolean ThemPhieuMuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matv", phieuMuon.getMatv());
        values.put("mand", phieuMuon.getMand());
        values.put("masach", phieuMuon.getMasach());
        values.put("ngay", phieuMuon.getNgay());
        values.put("trasach", phieuMuon.getTrangthai());
        values.put("tienthue", phieuMuon.getTienthue());
        long check = sqLiteDatabase.insert("phieumuon", null,values);
        return check != -1;
    }
}
