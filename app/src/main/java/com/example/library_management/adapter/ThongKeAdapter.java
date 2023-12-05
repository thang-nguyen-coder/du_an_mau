package com.example.library_management.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management.R;
import com.example.library_management.model.Sach;

import java.util.ArrayList;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;

    public ThongKeAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTensach.setText("Tên sách: " + list.get(holder.getAdapterPosition()).getTensach());
        holder.tvSoluong.setText("Số lượng: " + list.get(holder.getAdapterPosition()).getSoluong());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTensach, tvSoluong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTensach = itemView.findViewById(R.id.tvTenSachTop10);
            tvSoluong = itemView.findViewById(R.id.tvSoluong);
        }
    }
}
