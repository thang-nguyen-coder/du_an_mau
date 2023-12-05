package com.example.library_management.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.library_management.R;
import com.example.library_management.adapter.ThongKeAdapter;
import com.example.library_management.dao.ThongKeDAO;
import com.example.library_management.model.Sach;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticalScreen extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_screen);
        RecyclerView viewTop10 = findViewById(R.id.rcvTop10);

        EditText edtStart = findViewById(R.id.edtNgaybatdau);
        EditText edtEnd = findViewById(R.id.edtNgayketthuc);
        Button btnSubmit = findViewById(R.id.btnThongkeDoanhthu);
        TextView tvDoanhThu = findViewById(R.id.tvThongkeTien);
        Calendar calendar = Calendar.getInstance();

        edtStart.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtStart.setText(i2 + "/" + (i1 + 1) + "/" + i);

                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });
        edtEnd.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtEnd.setText(i2 + "/" + (i1 + 1) + "/" + i);

                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });

        btnSubmit.setOnClickListener(v -> {
            ThongKeDAO thongKeDAO = new ThongKeDAO(this);
            String ngayStart = edtStart.getText().toString();
            String ngayEnd = edtEnd.getText().toString();
            int doanhthu = thongKeDAO.getDoanhThu(ngayStart, ngayEnd);
            tvDoanhThu.setText(doanhthu + " VNĐ");
        });

        //data + adapter top10
        ThongKeDAO thongKeDAO = new ThongKeDAO(this);
        ArrayList<Sach> list = thongKeDAO.getTop10();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        viewTop10.setLayoutManager(manager);
        ThongKeAdapter adapter = new ThongKeAdapter(this, list);
        viewTop10.setAdapter(adapter);
    }
}