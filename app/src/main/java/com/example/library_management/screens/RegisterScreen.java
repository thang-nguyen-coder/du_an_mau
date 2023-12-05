package com.example.library_management.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library_management.R;
import com.example.library_management.dao.NguoiDungDAO;
import com.example.library_management.model.NguoiDung;

public class RegisterScreen extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPasswd = findViewById(R.id.edtPasswd);
        EditText edtRepasswd = findViewById(R.id.edtRePasswd);
        EditText edtSdt = findViewById(R.id.edtSdt);
        EditText edtAddress = findViewById(R.id.edtAddress);
        EditText edtFullname = findViewById(R.id.edtFullname);
        Button btnOK = findViewById(R.id.btnOK);

        nguoiDungDAO = new NguoiDungDAO(this);

        btnOK.setOnClickListener(v -> {
            String pass = edtPasswd.getText().toString();
            String repass = edtRepasswd.getText().toString();
            if(!pass.equals(repass)){
                Toast.makeText(this, "Mật khẩu không được trùng nhau", Toast.LENGTH_SHORT).show();
            }else {
                String user = edtUser.getText().toString();
                String name = edtFullname.getText().toString();
                String phone = edtSdt.getText().toString();
                String address = edtAddress.getText().toString();
                NguoiDung nguoidung = new NguoiDung(name, phone, address, user, pass);

                boolean check = nguoiDungDAO.DangKy(nguoidung);
                if (check){
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}