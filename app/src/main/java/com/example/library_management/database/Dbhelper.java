package com.example.library_management.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context context) {
        super(context, "thuvien", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table loaisach
        db.execSQL("create table loaisach(maloai integer primary key autoincrement, tenloai text)");
        // table sach
        db.execSQL("create table sach(masach integer primary key autoincrement, tensach text, tacgia text, giaban integer, maloai integer references loaisach(maloai), soluong integer, trangthai text) ");
        //table thanhvien
        db.execSQL("create table thanhvien(matv integer primary key autoincrement, hoten text, sdt text)");
        // table nguoidung
        db.execSQL("create table nguoidung(mand integer primary key autoincrement, hoten text, sdt integer, diachi text, taikhoan text, matkhau text, role integer)");
        // table phieumuon
        db.execSQL("create table phieumuon(mapm integer primary key autoincrement, matv integer references thanhvien(matv), masach integer references sach(masach), mand integer references nguoidung(mand), ngay text, trasach integer, tienthue integer)");

        // data mẫu
        db.execSQL("insert into loaisach values" +
                "(1, 'native app')," +
                "(2, 'cross platform')," +
                "(3, 'web')");
        db.execSQL("insert into sach values" +
                "(1, 'android developer', 'Cô Huệ android', 100, 1, 10, 'đang kinh doanh')," +
                "(2, 'react native', 'Thầy Sơndt', 200, 2, 5, 'ngừng kinh doanh')," +
                "(3, 'reactJS', 'Thầy Đạt', 300, 3, 2, 'đang kinh doanh')");
        db.execSQL("insert into thanhvien values" +
                "(1,'Lê Thị Huyền Trang', '0123456789')," +
                "(2,'Hoàng Thu Hương', '0111222333')," +
                "(3,'Nguyễn Thị Phương Anh', '0999888777')");
        //role 1 là admin, role 2 là thủ thư
        db.execSQL("insert into nguoidung values" +
                "(1,'Nguyễn Quyết Thắng', 0398693828, 'Tp Thái Bình', 'admin', '1', 1)," +
                "(2, 'Nguyễn Văn Thắng', 0333444555, 'Tp Thái Bình', 'thuthu1', '1', 2)");
        db.execSQL("insert into phieumuon values" +
                "(1,1,1,1,'10/10/2023',0,100)," +
                "(2,1,1,1,'10/10/2023',0,100)," +
                "(3,2,2,2,'10/10/2023',0,200)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("drop table if exists loaisach");
            db.execSQL("drop table if exists sach");
            db.execSQL("drop table if exists thanhvien");
            db.execSQL("drop table if exists nguoidung");
            db.execSQL("drop table if exists phieumuon");
            onCreate(db);
        }
    }
}
