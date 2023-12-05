package com.example.library_management.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_management.database.Dbhelper;
import com.example.library_management.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    private Dbhelper dbhelper;
    public ThanhVienDAO(Context context){
        dbhelper = new Dbhelper(context);
    }
    public ArrayList<ThanhVien> getDsThanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from thanhvien", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean ThemThanhVien (String name, String sdt){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", name);
        values.put("sdt", sdt);
        long check = sqLiteDatabase.insert("thanhvien",null, values);
        return check != -1;
    }
    public boolean SuaThanhVien (ThanhVien thanhVien){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", thanhVien.getHoten());
        values.put("sdt", thanhVien.getSdt());
        int check =  sqLiteDatabase.update("thanhvien",values,"matv = ?", new String[]{String.valueOf(thanhVien.getMatv())});
        return check != 0;
    }
    public int XoaThanhVien(int matv){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from thanhvien where matv = ?", new String[]{String.valueOf(matv)});
        if (cursor.getCount() > 0){
            int check = sqLiteDatabase.delete("thanhvien", "matv = ?", new String[]{String.valueOf(matv)});
            if (check == 0){
                return -1;
            }else {
                return 1;
            }
        }else {
            return 0;
        }
    }
}
