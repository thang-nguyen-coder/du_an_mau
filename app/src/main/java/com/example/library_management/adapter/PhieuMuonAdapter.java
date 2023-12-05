package com.example.library_management.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management.R;
import com.example.library_management.dao.PhieuMuonDAO;
import com.example.library_management.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_bill, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvId.setText("ID: " + list.get(holder.getAdapterPosition()).getMapm());
        holder.tvTentv.setText("Người mượn: " + list.get(holder.getAdapterPosition()).getHotentv());
        holder.tvTensach.setText("Tên sách: " + list.get(holder.getAdapterPosition()).getTensach());
        holder.tvTennd.setText("Người quản lý: " + list.get(holder.getAdapterPosition()).getHotennd());
        holder.tvNgay.setText("Ngày mượn: " + list.get(holder.getAdapterPosition()).getNgay());
        holder.tvTienthue.setText("Tiền thuê: " + list.get(holder.getAdapterPosition()).getTienthue());
        String trangthai = "";
        if(list.get(holder.getAdapterPosition()).getTrangthai() == 1){
            trangthai = "Đã trả sách";
            holder.btnTrasach.setVisibility(View.GONE);
        }else {
            trangthai = "Chưa trả sách";
            holder.btnTrasach.setVisibility(View.VISIBLE);
        }
        holder.tvTrangThai.setText("Trạng thái: " + trangthai);
        holder.btnTrasach.setOnClickListener(v -> {
            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
            boolean kiemtra = phieuMuonDAO.ThayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
            if (kiemtra){
                list.clear();
                list = phieuMuonDAO.getDsPhieuMuon();
                notifyDataSetChanged();
            }else {
                Toast.makeText(context, "Trả sách không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvTentv, tvTensach, tvTennd, tvNgay, tvTienthue, tvTrangThai;
        Button btnTrasach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvMapm);
            tvTentv = itemView.findViewById(R.id.tvTentv);
            tvTensach = itemView.findViewById(R.id.tvTensachpm);
            tvTennd = itemView.findViewById(R.id.tvTennd);
            tvNgay = itemView.findViewById(R.id.tvNgaymuon);
            tvTienthue = itemView.findViewById(R.id.tvTienthue);
            tvTrangThai = itemView.findViewById(R.id.tvTrangthai);
            btnTrasach = itemView.findViewById(R.id.btnTrasach);
        }
    }
}
