package com.example.library_management.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library_management.R;
import com.example.library_management.adapter.ThanhVienAdapter;
import com.example.library_management.dao.ThanhVienDAO;
import com.example.library_management.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PeopleSreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ThanhVienDAO thanhVienDAO;
    private ArrayList<ThanhVien> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_sreen);
        recyclerView = findViewById(R.id.rcvThanhvien);
        FloatingActionButton actionButton = findViewById(R.id.floatAddThanhvien);
        actionButton.setOnClickListener(v -> {
            showDialog();
        });
        loadData();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thanhvien, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText edtHotentv = view.findViewById(R.id.edtHotentv);
        EditText edtSdt = view.findViewById(R.id.edtSdt);
        Button btnLuu = view.findViewById(R.id.btnLuuTv);
        Button btnHuy = view.findViewById(R.id.btnHuyTv);

        btnLuu.setOnClickListener(v -> {
            String name = edtHotentv.getText().toString();
            String sdt = edtSdt.getText().toString();
            if (name.equals("") || sdt.equals("")){
                Toast.makeText(this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }else {
                boolean check = thanhVienDAO.ThemThanhVien(name, sdt);
                if (check){
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }
            }
        });
        btnHuy.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void loadData() {
        thanhVienDAO = new ThanhVienDAO(this);
        list = thanhVienDAO.getDsThanhVien();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(this, list, thanhVienDAO);
        recyclerView.setAdapter(adapter);
    }
}