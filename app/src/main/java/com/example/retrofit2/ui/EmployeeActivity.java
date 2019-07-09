package com.example.retrofit2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit2.R;
import com.example.retrofit2.model.Employees;
import com.example.retrofit2.retrofit.APIService;
import com.example.retrofit2.retrofit.APIUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity {
    private ProgressBar progressbar;
    private TextView tvID;
    private TextView tvEmployeeName;
    private TextView tvEmployeeSalary;
    private TextView tvEmployeeAge;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        initView();
        service = APIUtils.getData();
        getData();
    }

    private void initView() {
        progressbar = findViewById(R.id.progressbar);
        tvID = findViewById(R.id.tvID);
        tvEmployeeName = findViewById(R.id.tv_Employee_Name);
        tvEmployeeSalary = findViewById(R.id.tv_Employee_Salary);
        tvEmployeeAge = findViewById(R.id.tv_Employee_Age);
    }

    private void getData() {
        Call<Employees> call = service.getEmployee(getIntent().getIntExtra("id",0));
        call.enqueue(new Callback<Employees>() {
            @Override
            public void onResponse(Call<Employees> call, Response<Employees> response) {
                progressbar.setVisibility(View.GONE);
                tvEmployeeAge.setText("Age: "+response.body().getEmployeeAge());
                tvID.setText("ID: "+response.body().getId());
                tvEmployeeName.setText("Name: "+response.body().getEmployeeName());
                tvEmployeeSalary.setText("Salary: "+response.body().getEmployeeSalary());
            }

            @Override
            public void onFailure(Call<Employees> call, Throwable t) {
                Toast.makeText(EmployeeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
