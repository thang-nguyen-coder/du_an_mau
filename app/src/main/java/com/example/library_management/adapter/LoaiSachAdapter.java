package com.example.library_management.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management.R;
import com.example.library_management.dao.LoaiSachDAO;
import com.example.library_management.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHoder>{

    private Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.tvMaloai.setText("ID: " + list.get(position).getMaloai());
        holder.tvLoaiSach.setText("Loại sách: " + list.get(position).getTenloai());
        holder.tvEditLS.setOnClickListener(v -> {
            ShowDialogUpdate(list.get(holder.getAdapterPosition()));
        });
        holder.tvDelLS.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa sách này ?");
            builder.setNegativeButton("Không", null);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int check = loaiSachDAO.XoaLoaiSach(list.get(holder.getAdapterPosition()).getMaloai());
                    switch (check){
                        case -1:
                            Toast.makeText(context, "Xóa không thành công ", Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(context, "Bạn cần xóa các cuốn sách trong thể loại này trước", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                            break;
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvMaloai, tvLoaiSach, tvDelLS, tvEditLS;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaloai = itemView.findViewById(R.id.tvMaLSach);
            tvLoaiSach= itemView.findViewById(R.id.tvLoaiSach);
            tvDelLS = itemView.findViewById(R.id.tvDelLS);
            tvEditLS = itemView.findViewById(R.id.tvEditLS);
        }
    }
    private void ShowDialogUpdate (LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_category, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        EditText edtTenLoai = view.findViewById(R.id.edtSaveLS);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        tvTitle.setText("Cập nhật lại tên sách");
        btnSave.setText("Cập nhật");
        edtTenLoai.setText(loaiSach.getTenloai());

        btnHuy.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        btnSave.setOnClickListener(v -> {
            String name = edtTenLoai.getText().toString();
            if (name.equals("")){
                Toast.makeText(context, "Chưa nhập tên mới", Toast.LENGTH_SHORT).show();
                return;
            }
            LoaiSach loaiSachUpdate = new LoaiSach(loaiSach.getMaloai(),name);
            boolean check = loaiSachDAO.SuaLoaiSach(loaiSachUpdate);
            if(check){
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadData(){
        list.clear();
        list = loaiSachDAO.getDsLoaiSach();
        notifyDataSetChanged();
    }
}
