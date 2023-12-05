package com.example.library_management.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.library_management.R;
import com.example.library_management.adapter.PhieuMuonAdapter;
import com.example.library_management.dao.NguoiDungDAO;
import com.example.library_management.dao.PhieuMuonDAO;
import com.example.library_management.dao.SachDAO;
import com.example.library_management.dao.ThanhVienDAO;
import com.example.library_management.model.NguoiDung;
import com.example.library_management.model.PhieuMuon;
import com.example.library_management.model.Sach;
import com.example.library_management.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class BillScreen extends AppCompatActivity {
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<PhieuMuon> list;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_screen);
        recyclerView = findViewById(R.id.rcvPhieumuon);
        FloatingActionButton actionButton = findViewById(R.id.floatAddPhieuMuon);
        loadData();
        actionButton.setOnClickListener(v -> {
            showDialogAddpm();
        });
    }

    private void loadData() {
        phieuMuonDAO = new PhieuMuonDAO(this);
        list = phieuMuonDAO.getDsPhieuMuon();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(list, this);
        recyclerView.setAdapter(phieuMuonAdapter);
    }

    private void showDialogAddpm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_phieumuon, null);
        Spinner spntv = view.findViewById(R.id.spnThanhvien);
        Spinner spnsach = view.findViewById(R.id.spnSach);
        Spinner spnnd = view.findViewById(R.id.spnNguoidung);
        EditText edtTien = view.findViewById(R.id.edtTienThue);
        getDataTv(spntv);
        getDataSach(spnsach);
        getDataNguoiDung(spnnd);
        builder.setView(view);

        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // lấy mã thành viên
                HashMap<String, Object> hsTv = (HashMap<String, Object>) spntv.getSelectedItem();
                int matv = (int) hsTv.get("matv");
                // lấy mã sách
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnsach.getSelectedItem();
                int masach = (int) hsSach.get("masach");
                // lấy mã người dùng
                HashMap<String, Object> hsNd = (HashMap<String, Object>) spnnd.getSelectedItem();
                int mand = (int) hsNd.get("mand");
                int tien = Integer.parseInt(edtTien.getText().toString());
                themPhieuMuon(matv, masach, mand, tien);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getDataTv(Spinner spnTv){
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(this);
        ArrayList<ThanhVien> list= thanhVienDAO.getDsThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnTv.setAdapter(adapter);
    }
    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(this);
        ArrayList<Sach> list= sachDAO.getDsSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach", sc.getTensach());
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(adapter);
    }
    private void getDataNguoiDung(Spinner spnNd){
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
        ArrayList<NguoiDung> list= nguoiDungDAO.getDsNguoiDung();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (NguoiDung nd : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("mand", nd.getMand());
            hs.put("hoten", nd.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnNd.setAdapter(adapter);
    }
    private void themPhieuMuon(int matv, int masach, int mand, int tien){
        // Lấy ngày giờ hiện tại
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(date);

        PhieuMuon phieuMuon = new PhieuMuon(matv, masach, mand, ngay, 0, tien);
        boolean kiemtra = phieuMuonDAO.ThemPhieuMuon(phieuMuon);
        if (kiemtra){
            Toast.makeText(this, "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        }else {
            Toast.makeText(this, "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}