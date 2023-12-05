package com.example.library_management.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_management.database.Dbhelper;
import com.example.library_management.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungDAO {
    private Dbhelper dbhelper;
    SharedPreferences sharedPreferences;
    public NguoiDungDAO (Context context){
        dbhelper = new Dbhelper(context);
        sharedPreferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
    }
    // check thông tin đăng nhập
    public boolean CheckLogin (String user, String passwd) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from nguoidung where taikhoan = ? and matkhau = ?", new String[]{user, passwd});
        // lưu role của account đang đăng nhập
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("taikhoan", cursor.getString(4));
            editor.putInt("role", cursor.getInt(6));
            editor.apply();
        }

        return cursor.getCount() > 0;
    }
    public boolean DangKy (NguoiDung nguoiDung){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", nguoiDung.getHoten());
        values.put("sdt", nguoiDung.getSdt());
        values.put("diachi", nguoiDung.getDiachi());
        values.put("taikhoan", nguoiDung.getTaikhoan());
        values.put("matkhau", nguoiDung.getMatkhau());
        values.put("role", 2);
        long check = sqLiteDatabase.insert("nguoidung", null, values);
        return check != -1;
    }
    public boolean DoiMk (String userName , String oldPassword, String newPassword){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from nguoidung where taikhoan = ? and matkhau = ?", new String[]{userName, oldPassword});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("matkhau", newPassword);
            long check = sqLiteDatabase.update("nguoidung", values, "taikhoan = ?", new String[]{String.valueOf(userName)});
            return check != -1;
        }
        return false;
    }

    public ArrayList<NguoiDung> getDsNguoiDung() {
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from nguoidung", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new NguoiDung(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
