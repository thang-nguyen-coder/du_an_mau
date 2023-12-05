package com.example.library_management.adapter;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management.R;
import com.example.library_management.dao.SachDAO;
import com.example.library_management.model.LoaiSach;
import com.example.library_management.model.Sach;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Sach> list;

    private final SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTenSach.setText("Tên sách: " + list.get(position).getTensach());
        holder.tvTacgia.setText("Tác giả: " + list.get(position).getTacgia());
        holder.tvGia.setText("Giá: " + list.get(position).getGiaban() + "$");
        holder.tvSoluong.setText("Số lượng: " + list.get(position).getSoluong());
        String trangthai = list.get(holder.getAdapterPosition()).getTrangthai();
        if (trangthai.equals("đang kinh doanh")){
            holder.tvTrangthai.setTextColor(ContextCompat.getColor(context, R.color.dangkinhdoanh));
            holder.tvTrangthai.setText("Trạng thái: " + trangthai);
        }else{
            holder.tvTrangthai.setTextColor(ContextCompat.getColor(context, R.color.ngungkinhdoanh));
            holder.tvTrangthai.setText("Trạng thái: " + trangthai);
        }
        holder.tvDelSach.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa sách này ?");
            builder.setNegativeButton("Không", null);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int check = sachDAO.XoaSach(list.get(holder.getAdapterPosition()).getMasach());
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
        holder.tvEditSach.setOnClickListener(v -> {
            ShowDialogUpdate(list.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvTacgia, tvGia, tvDelSach, tvEditSach, tvSoluong, tvTrangthai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvTacgia = itemView.findViewById(R.id.tvTacGia);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvDelSach = itemView.findViewById(R.id.tvDelS);
            tvEditSach = itemView.findViewById(R.id.tvEditS);
            tvSoluong = itemView.findViewById(R.id.tvSoluong);
            tvTrangthai = itemView.findViewById(R.id.tvTrangthai);
        }
    }

    private void ShowDialogUpdate(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_book, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView tvTitle = view.findViewById(R.id.tvTitleSach);
        EditText edtTenSach = view.findViewById(R.id.edtTensach);
        EditText edtTacgia = view.findViewById(R.id.edtTacgia);
        EditText edtGiaban = view.findViewById(R.id.edtGiaban);
        EditText edtSoluong = view.findViewById(R.id.edtSoluong);
        EditText edtTrangthai = view.findViewById(R.id.edtTrangthai);
        Button btnSwitch = view.findViewById(R.id.btnTrangthai);


        btnSwitch.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Thông báo");
            builder1.setMessage("Hãy chọn trạng thái");
            builder1.setNegativeButton("đang kinh doanh", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    edtTrangthai.setText("đang kinh doanh");
                }
            });
            builder1.setPositiveButton("ngừng kinh doanh", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    edtTrangthai.setText("ngừng kinh doanh");
                }
            });
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();

        });
        Button btnSave = view.findViewById(R.id.btnSaveSach);
        Button btnHuy = view.findViewById(R.id.btnHuySach);

        tvTitle.setText("Cập nhật lại thông tin sách");
        btnSave.setText("Cập nhật");
        edtTenSach.setText(sach.getTensach());

        btnHuy.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        btnSave.setOnClickListener(v -> {
            String name = edtTenSach.getText().toString();
            String tacgia = edtTacgia.getText().toString();
            String giaban = edtGiaban.getText().toString();
            int soluong = Integer.parseInt(edtSoluong.getText().toString());
            String trangthai = edtTrangthai.getText().toString();

            if (name.equals("") && tacgia.equals("") && giaban.equals("")) {
                Toast.makeText(context, "Bạn chưa nhập đủ nội dung", Toast.LENGTH_SHORT).show();
            } else if (name.length() < 5) {
                Toast.makeText(context, "tên sách tối thiếu là 5 ký tự", Toast.LENGTH_SHORT).show();
            }else if (name.length() > 30) {
                Toast.makeText(context, "tên sách tối đa là 30 ký tự", Toast.LENGTH_SHORT).show();
            } else if (tacgia.equals("")) {
                Toast.makeText(context, "Chưa nhập tên tác giả", Toast.LENGTH_SHORT).show();
            } else if (giaban.equals("")) {
                Toast.makeText(context, "Chưa nhập giá bán", Toast.LENGTH_SHORT).show();
            } else {
                Sach sachUpdate = new Sach(sach.getMasach(), name, tacgia, giaban, sach.getMaloai(), soluong, trangthai);
                boolean check = sachDAO.SuaSach(sachUpdate);
                if (check) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void loadData() {
        list.clear();
        list = sachDAO.getDsSach();
        notifyDataSetChanged();
    }
}
