package com.example.retrofit2.retrofit;

public class APIUtils {
    public static final String Base_Url="http://dummy.restapiexample.com/";
    public static APIService    getData(){
        return RetrofitClient.getRetrofitClient(Base_Url).create(APIService.class);
    }
}
