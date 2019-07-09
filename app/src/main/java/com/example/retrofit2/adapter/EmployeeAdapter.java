package com.example.retrofit2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofit2.R;
import com.example.retrofit2.listener.OnClick;
import com.example.retrofit2.listener.OnDelete;
import com.example.retrofit2.listener.OnEdit;
import com.example.retrofit2.model.Employees;

import java.util.List;

public class EmployeeAdapter  extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{
    private List<Employees> employeesList;
    private final Context context;
    private final OnClick onClick;
    private final OnEdit onEdit;
    private final OnDelete onDelete;

    public EmployeeAdapter(List<Employees> employeesList, Context context, OnClick onClick, OnEdit onEdit, OnDelete onDelete) {
        this.employeesList = employeesList;
        this.context = context;
        this.onClick = onClick;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_employees, parent, false);
        return new ViewHolder(itemView);
    }
    public void removeItem(int position) {
        employeesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
    public void replaceItem(int postion, Employees item) {
        employeesList.remove(postion);
        employeesList.add(postion, item);
        notifyItemChanged(postion);
    }


//    public void addItem(int position, Employees item) {
//        employeesList.add(position, item);
//        notifyItemInserted(position);
//    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Employees employees = employeesList.get(position);
        holder.tv_Employee_Age.setText("Age: "+employees.getEmployeeAge());
        holder.tvID.setText("ID: "+employees.getId()+"");
        holder.tv_Employee_Name.setText("Name: "+employees.getEmployeeName());
        holder.tv_Employee_Salary.setText("Salary: "+employees.getEmployeeSalary());
        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PopupMenu popup = new PopupMenu(context, holder.imgMenu);
                    popup.inflate(R.menu.option_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.delete:
                                    onDelete.OnDelete(position);
                                    return true;
                                case R.id.update:
                                    onEdit.OnEdit(position);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                } catch (Exception e) {
                    Log.e("lỗi onclick","lỗi onclick");
                }


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onClick.OnClick(position);
                } catch (Exception e) {
                    Log.e("lỗi onclick","lỗi onclick");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (employeesList == null) return 0;
        return employeesList.size();
    }
    public void changeDataset(List<Employees> items) {
        this.employeesList = items;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgMenu;
        final TextView tvID;
        final TextView tv_Employee_Salary;
        final TextView tv_Employee_Age;
        final TextView tv_Employee_Name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tv_Employee_Age = itemView.findViewById(R.id.tv_Employee_Age);
            tv_Employee_Name = itemView.findViewById(R.id.tv_Employee_Name);
            tv_Employee_Salary = itemView.findViewById(R.id.tv_Employee_Salary);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
