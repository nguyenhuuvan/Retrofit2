package com.example.retrofit2;

import com.example.retrofit2.model.Employees;
import com.example.retrofit2.model.Request_Creat_Update;
import com.example.retrofit2.model.ResultCreat;
import com.example.retrofit2.model.ResultDelete;
import com.example.retrofit2.model.ResultEdit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @GET("api/v1/employees")
    Call<List<Employees>> getAllEmployees();

    @POST("/api/v1/create")
    Call<ResultCreat> creatEmployee(@Body Request_Creat_Update request_creat_update);


    @DELETE("/api/v1/delete/{id}")
    Call<ResultDelete> deleteEmployee(@Path("id") int id);

    @PUT("/api/v1/update/{id}")
    Call<ResultEdit> updateEmployee(@Body Request_Creat_Update request_creat_update,@Path("id") int id);
}
