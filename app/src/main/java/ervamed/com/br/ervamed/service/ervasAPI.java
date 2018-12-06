package ervamed.com.br.ervamed.service;

import java.util.List;

import ervamed.com.br.ervamed.entity.Erva;
import ervamed.com.br.ervamed.entity.JSONResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ervasAPI {

    @Headers("Accept:application/json")
    @GET("plantas")
    Call<JSONResponse> getJSON();

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://crs.unochapeco.edu.br/zend/plantas-medicinais/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
