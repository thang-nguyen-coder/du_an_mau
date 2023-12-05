package com.example.library_management.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_management.database.Dbhelper;
import com.example.library_management.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private Dbhelper dbhelper;
    public LoaiSachDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }
    // lấy danh sách loại sách
    public ArrayList<LoaiSach> getDsLoaiSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from loaisach", null);
        if(cursor.getCount() > 0){
            // di chuyển con trỏ lên vị trí đầu ds
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean ThemLoaiSach (String name){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai", name);
        long check = sqLiteDatabase.insert("loaisach", null, values);
        return check != -1;
    }
    public boolean SuaLoaiSach (LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai", loaiSach.getTenloai());
        int check = sqLiteDatabase.update("loaisach", values,"maloai = ?", new String[]{String.valueOf(loaiSach.getMaloai())});
        return check != 0;
    }
    public int XoaLoaiSach (int maloai){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from sach where maloai = ?", new String[]{String.valueOf(maloai)});
        if(cursor.getCount() > 0) {
            return 0; // không được xóa vì ràng buộc khóa ngoại
        }else{
            int check = sqLiteDatabase.delete("loaisach","maloai = ?", new String[]{String.valueOf(maloai)});
            if(check == 0) {
                return -1; // không xóa được vì không tìm thấy loại sách cần xóa
            }else {
                return 1;
            }
        }
    }
}
