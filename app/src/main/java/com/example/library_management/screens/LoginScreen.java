package com.example.library_management.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library_management.MainActivity;
import com.example.library_management.R;
import com.example.library_management.dao.NguoiDungDAO;

public class LoginScreen extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPasswd = findViewById(R.id.edtPasswd);
        Button btnLogin = findViewById(R.id.btnLogin);

        nguoiDungDAO = new NguoiDungDAO(this);

        btnLogin.setOnClickListener(v -> {
            String user = edtUser.getText().toString();
            String passwd = edtPasswd.getText().toString();

            if (user.equals("") && passwd.equals("")) {
                Toast.makeText(this, "Chưa nhập tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();

            } else if (user.equals("")) {
                Toast.makeText(this, "Chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
            } else if (passwd.equals("")) {
                Toast.makeText(this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                boolean check = nguoiDungDAO.CheckLogin(user, passwd);
                if (check) {
                    startActivity(new Intent(LoginScreen.this, MainActivity.class));
                } else {
                    Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}