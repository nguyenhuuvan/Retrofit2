package com.example.retrofit2.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofit2.retrofit.APIService;
import com.example.retrofit2.R;
import com.example.retrofit2.retrofit.APIUtils;
import com.example.retrofit2.adapter.EmployeeAdapter;
import com.example.retrofit2.listener.OnClick;
import com.example.retrofit2.listener.OnDelete;
import com.example.retrofit2.listener.OnEdit;
import com.example.retrofit2.model.Employees;
import com.example.retrofit2.model.ResultDelete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEmployeeActivity extends AppCompatActivity implements OnClick, OnDelete, OnEdit {
    private RecyclerView rvListEmployee;
    private FloatingActionButton fabAdd;
    private List<Employees> employeesList;
    private EmployeeAdapter employeeAdapter;
    private ProgressBar progressBar;
    private APIService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        initView();
        initAction();
        service = APIUtils.getData();
        employeesList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(employeesList, this, this, this, this);
        rvListEmployee.setAdapter(employeeAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvListEmployee.setLayoutManager(manager);
        getData();
    }

    private void getData() {

        Call<List<Employees>> call = service.getAllEmployees();
        call.enqueue(new Callback<List<Employees>>() {
            @Override
            public void onResponse(Call<List<Employees>> call, Response<List<Employees>> response) {
                progressBar.setVisibility(View.GONE);
                for (int i= 0 ;i<response.body().size();i++){
                    employeesList.add(new Employees(response.body().get(i).getId(),
                            response.body().get(i).getEmployeeName(),
                            response.body().get(i).getEmployeeSalary(),
                            response.body().get(i).getEmployeeAge(),
                            response.body().get(i).getProfileImage()));
                    Collections.reverse(employeesList);
                    employeeAdapter.changeDataset(employeesList);
                }
            }

            @Override
            public void onFailure(Call<List<Employees>> call, Throwable t) {
                Toast.makeText(ListEmployeeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        rvListEmployee = findViewById(R.id.rvListEmployee);
        fabAdd = findViewById(R.id.fabAdd);
        progressBar = findViewById(R.id.progressbar);
    }

    private void initAction() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ListEmployeeActivity.this,CreatEmployeeActivity.class),2);
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        employeesList.clear();
//        getData();
//    }

    @Override
    public void OnClick(int pos) {
        Intent intent = new Intent(ListEmployeeActivity.this,EmployeeActivity.class);
        intent.putExtra("id",employeesList.get(pos).getId());
        startActivity(intent);
    }

    @Override
    public void OnDelete(final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Xóa");
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<ResultDelete> call = service.deleteEmployee(employeesList.get(pos).getId());
                call.enqueue(new Callback<ResultDelete>() {
                    @Override
                    public void onResponse(Call<ResultDelete> call, Response<ResultDelete> response) {
                        if(response.body().getSuccess().getText().equals("successfully! deleted Records")){
                            Toast.makeText(ListEmployeeActivity.this, response.body().getSuccess().getText(), Toast.LENGTH_SHORT).show();
                            employeeAdapter.removeItem(pos);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultDelete> call, Throwable t) {
                        Toast.makeText(ListEmployeeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void OnEdit(int pos) {
        Intent intent = new Intent(ListEmployeeActivity.this,EditEmployeeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",employeesList.get(pos).getId());
        bundle.putInt("age",employeesList.get(pos).getEmployeeAge());
        bundle.putString("name",employeesList.get(pos).getEmployeeName());
        bundle.putInt("salary",employeesList.get(pos).getEmployeeSalary());

        //pos
        bundle.putInt("pos",pos);

        intent.putExtras(bundle);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                int salary = data.getExtras().getInt("salary");
                int age = data.getExtras().getInt("age");
                String name = data.getExtras().getString("name");
                int pos = data.getExtras().getInt("pos");
                int id = data.getExtras().getInt("id");
                employeeAdapter.replaceItem(pos,new Employees(id,name,salary,age,""));
            }
        }else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                int salary = data.getExtras().getInt("salary");
                int age = data.getExtras().getInt("age");
                String name = data.getExtras().getString("name");
                int id = data.getExtras().getInt("id");
                employeesList.add(new Employees(id,name,salary,age,""));
                Collections.reverse(employeesList);
                employeeAdapter.changeDataset(employeesList);
            }

        }

    }
}
