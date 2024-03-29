package com.example.retrofit2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.retrofit2.retrofit.APIService;
import com.example.retrofit2.R;
import com.example.retrofit2.retrofit.APIUtils;
import com.example.retrofit2.model.Request_Creat_Update;
import com.example.retrofit2.model.ResultCreat;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatEmployeeActivity extends AppCompatActivity {
    private TextInputEditText edName;
    private TextInputEditText edSalary;
    private TextInputEditText edAge;
    private APIService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_employee);

        initView();
        service = APIUtils.getData();
    }

    private void initView() {
        edName = findViewById(R.id.edName);
        edSalary =  findViewById(R.id.edSalary);
        edAge =  findViewById(R.id.edAge);
    }

    public void creat(View view) {
        final String name = edName.getText().toString().trim();
        final String salary = edSalary.getText().toString().trim();
        final String age = edAge.getText().toString().trim();

        if(name.isEmpty()||salary.isEmpty()||age.isEmpty()){
            if(name.isEmpty())
                edName.setError("Mời nhập name");
            if(salary.isEmpty())
                edSalary.setError("Mời nhập salary");
            if(age.isEmpty())
                edAge.setError("Mời nhập age");
        }else{
            Call<ResultCreat> call = service.creatEmployee(new Request_Creat_Update(name,Integer.parseInt(salary),Integer.parseInt(age)));
            call.enqueue(new Callback<ResultCreat>() {
                @Override
                public void onResponse(Call<ResultCreat> call, Response<ResultCreat> response) {
                    if(response!=null){
                        if(response.body().getAge()==Integer.parseInt(age)&&response.body().getName().equals(name)&&response.body().getSalary()==Integer.parseInt(salary)){
                            Log.e("reponse",response.body().toString());
                            Toast.makeText(CreatEmployeeActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                            Intent returnIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt("age", Integer.parseInt(age));
                            bundle.putString("name",name);
                            bundle.putInt("id",response.body().getId());
                            bundle.putInt("salary", Integer.parseInt(salary));
                            returnIntent.putExtras(bundle);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }else {
                            Toast.makeText(CreatEmployeeActivity.this, "Không thể thêm", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(CreatEmployeeActivity.this, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResultCreat> call, Throwable t) {
                    Log.e("onFailure", "onFailure: " + t.getMessage());
                    if(t.getMessage().equals("Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 18 path $.error.")){
                        Toast.makeText(CreatEmployeeActivity.this,"Trùng tên", Toast.LENGTH_SHORT).show();
                    }else
                    Toast.makeText(CreatEmployeeActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
