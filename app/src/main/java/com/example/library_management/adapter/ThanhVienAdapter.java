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
import com.example.library_management.dao.ThanhVienDAO;
import com.example.library_management.model.Sach;
import com.example.library_management.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvHoten.setText("Họ tên: " + list.get(holder.getAdapterPosition()).getHoten());
        holder.tvSdt.setText("SĐT: " + list.get(holder.getAdapterPosition()).getSdt());
        holder.tvEditTv.setOnClickListener(v -> {
            ShowDialogUpdate(list.get(holder.getAdapterPosition()));
        });
        holder.tvDelTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa loại người dùng này ?");
            builder.setNegativeButton("Không", null);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int check = thanhVienDAO.XoaThanhVien(list.get(holder.getAdapterPosition()).getMatv());
                    switch (check) {
                        case -1:
                            Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
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

    private void ShowDialogUpdate (ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_thanhvien, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView tvTitle = view.findViewById(R.id.tvTitleTv);
        EditText edtHoten = view.findViewById(R.id.edtHotentv);
        EditText edtSdt = view.findViewById(R.id.edtSdt);
        Button btnSave = view.findViewById(R.id.btnLuuTv);
        Button btnHuy = view.findViewById(R.id.btnHuyTv);

        tvTitle.setText("Cập nhật lại thông tin thành viên");
        btnSave.setText("Cập nhật");
        edtHoten.setText(thanhVien.getHoten());

        btnHuy.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        btnSave.setOnClickListener(v -> {
            String name = edtHoten.getText().toString();
            String sdt = edtSdt.getText().toString();
            if (name.equals("") || sdt.equals("")){
                Toast.makeText(context, "Bạn chưa nhập đủ nội dung", Toast.LENGTH_SHORT).show();
                return;
            }
            ThanhVien thanhvienUpdate = new ThanhVien(thanhVien.getMatv(), name, sdt);
            boolean check = thanhVienDAO.SuaThanhVien(thanhvienUpdate);
            if(check){
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoten, tvSdt, tvDelTv, tvEditTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoten = itemView.findViewById(R.id.tvTentv);
            tvSdt = itemView.findViewById(R.id.tvSdt);
            tvDelTv = itemView.findViewById(R.id.tvDelTV);
            tvEditTv = itemView.findViewById(R.id.tvEditTV);
        }
    }

    public void loadData() {
        list.clear();
        list = thanhVienDAO.getDsThanhVien();
        notifyDataSetChanged();
    }
}
