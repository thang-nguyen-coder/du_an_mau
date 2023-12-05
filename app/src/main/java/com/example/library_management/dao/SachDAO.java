package com.example.library_management.dao;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.library_management.database.Dbhelper;
import com.example.library_management.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    private Dbhelper dbhelper;
    public SachDAO(Context context){
        dbhelper = new Dbhelper(context);
    }

    //hàm lấy ds sách
    public ArrayList<Sach> getDsSach (){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from sach", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4), cursor.getInt(5),cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean ThemSach (String name, String tacgia, int gia){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", name);
        values.put("tacgia", tacgia);
        values.put("giaban", gia);
        long check = sqLiteDatabase.insert("sach", null, values);
        return check != -1;
    }
    public boolean SuaSach(Sach sach){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach", sach.getMasach());
        values.put("tensach", sach.getTensach());
        values.put("tacgia", sach.getTacgia());
        values.put("giaban", sach.getGiaban());
        values.put("trangthai", sach.getTrangthai());
        int check = sqLiteDatabase.update("sach", values, "masach = ?", new String[]{String.valueOf(sach.getMasach())});
        return check != 0;
    }
    public int XoaSach(int masach) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from sach where masach = ?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() > 0) {
           int check = sqLiteDatabase.delete("sach", "masach = ?", new String[]{String.valueOf(masach)});
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
