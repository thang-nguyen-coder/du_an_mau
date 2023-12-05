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
import com.example.library_management.adapter.LoaiSachAdapter;
import com.example.library_management.dao.LoaiSachDAO;
import com.example.library_management.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryScreen extends AppCompatActivity {
    private LoaiSachDAO loaiSachDAO;
    private RecyclerView recyclerView;
    private ArrayList<LoaiSach> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);
        recyclerView = findViewById(R.id.rcvLoaiSach);
        FloatingActionButton floatAdd = findViewById(R.id.floatAdd);


        floatAdd.setOnClickListener(v -> {
            showDialogAdd();
        });
        // data
        loaiSachDAO = new LoaiSachDAO(this);
        // hàm load lại dữ liệu
        loadData();

    }
    private void loadData (){
        list = loaiSachDAO.getDsLoaiSach();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this, list, loaiSachDAO);
        recyclerView.setAdapter(loaiSachAdapter);
    }

    private void showDialogAdd (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_category, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText edtTenLoai = view.findViewById(R.id.edtSaveLS);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnSave.setOnClickListener(v -> {
            String name = edtTenLoai.getText().toString();
            if (name.equals("")){
                Toast.makeText(this, "Chưa nhập tên loại sách", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean check = loaiSachDAO.ThemLoaiSach(name);
            if (check){
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }
}