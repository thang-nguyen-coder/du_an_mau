package com.example.library_management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management.dao.NguoiDungDAO;
import com.example.library_management.screens.BillScreen;
import com.example.library_management.screens.BookScreen;
import com.example.library_management.screens.CategoryScreen;
import com.example.library_management.screens.LoginScreen;
import com.example.library_management.screens.PeopleSreen;
import com.example.library_management.screens.RegisterScreen;
import com.example.library_management.screens.StatisticalScreen;

public class MainActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout lnLoaiSach = findViewById(R.id.lnLoaiSach);
        LinearLayout lnSach = findViewById(R.id.lnSach);
        LinearLayout lnThanhVien = findViewById(R.id.lnThanhVien);
        LinearLayout lnPhieuMuon = findViewById(R.id.lnPhieuMuon);
        LinearLayout lnThongKe = findViewById(R.id.lnThongKe);
        LinearLayout lnThoat = findViewById(R.id.lnThoat);
        LinearLayout lnAddTk = findViewById(R.id.lnAddTk);
        LinearLayout lnDoimk = findViewById(R.id.lnDoiMk);
        //Lấy role đã lưu trong sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);

        switch (role) {
            //admin
            case 1:
                break;
            //thủ thư
            case 2:
                lnAddTk.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        lnLoaiSach.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CategoryScreen.class));
        });
        lnSach.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BookScreen.class));
        });
        lnPhieuMuon.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BillScreen.class));
        });
        lnThanhVien.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PeopleSreen.class));
        });
        lnThoat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        lnThongKe.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StatisticalScreen.class));
        });
        lnAddTk.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterScreen.class));
        });
        lnDoimk.setOnClickListener(v -> {
            showDialogDoiMk();
        });
    }

    private void showDialogDoiMk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimk, null);
        TextView tvOldpass = view.findViewById(R.id.edtOldPasswd);
        TextView tvNewpass = view.findViewById(R.id.edtNewPasswd);
        TextView tvRepass = view.findViewById(R.id.edtRePasswd);
        Button btnLuu = view.findViewById(R.id.btnLuuDoiMk);
        Button btnHuy = view.findViewById(R.id.btnHuyDoiMk);
        builder.setView(view);
        btnHuy.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        btnLuu.setOnClickListener(v -> {
            String old = tvOldpass.getText().toString();
            String new_ = tvNewpass.getText().toString();
            String re = tvRepass.getText().toString();
            if(new_.equals(re)){
                SharedPreferences sharedPreferences = getSharedPreferences("dataUser", this.MODE_PRIVATE);
                String user = sharedPreferences.getString("taikhoan", "");
                // cập nhât
                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
                boolean check = nguoiDungDAO.DoiMk(user, old, new_);
                if (check){
                    Toast.makeText(this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Mật khẩu mới không được trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}