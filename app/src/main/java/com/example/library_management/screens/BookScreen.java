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
import com.example.library_management.adapter.SachAdapter;
import com.example.library_management.dao.SachDAO;
import com.example.library_management.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BookScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SachDAO sachDAO;
    private ArrayList<Sach> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);

        recyclerView = findViewById(R.id.rcvSach);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatAddSach);
        floatingActionButton.setOnClickListener(v -> {
            showDialog();
        });
        sachDAO = new SachDAO(this);
        loadData();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_book, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText edtTensach = view.findViewById(R.id.edtTensach);
        EditText edtTacgia = view.findViewById(R.id.edtTacgia);
        EditText edtGiaban = view.findViewById(R.id.edtGiaban);
        Button btnOK = view.findViewById(R.id.btnSaveSach);
        Button btnHuy = view.findViewById(R.id.btnHuySach);

        btnOK.setOnClickListener(v -> {
            String name = edtTensach.getText().toString();
            String tacgia = edtTacgia.getText().toString();
            String giaban = edtGiaban.getText().toString();
            if (name.equals("") && tacgia.equals("") && giaban.equals("")){
                Toast.makeText(this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }else if(name.equals("")){
                Toast.makeText(this, "Chưa nhập tên sách", Toast.LENGTH_SHORT).show();
            }
            else if(tacgia.equals("")){
                Toast.makeText(this, "Chưa nhập tên tác giả", Toast.LENGTH_SHORT).show();
            }
            else if(giaban.equals("")){
                Toast.makeText(this, "Chưa nhập giá bán", Toast.LENGTH_SHORT).show();
            }else{
                boolean check = sachDAO.ThemSach(name, tacgia, Integer.parseInt(giaban));
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
        list = sachDAO.getDsSach();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        SachAdapter adapter = new SachAdapter(this,list, sachDAO);
        recyclerView.setAdapter(adapter);
    }

}