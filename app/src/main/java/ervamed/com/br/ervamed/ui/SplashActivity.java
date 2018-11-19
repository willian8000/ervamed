package ervamed.com.br.ervamed.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ervamed.com.br.ervamed.MainActivity;
import ervamed.com.br.ervamed.R;
import ervamed.com.br.ervamed.entity.Erva;
import ervamed.com.br.ervamed.entity.JSONResponse;
import ervamed.com.br.ervamed.model.ModelPlanta;
import ervamed.com.br.ervamed.service.ervasAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    private ModelPlanta modelPlanta;
    private ArrayList<Erva> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                if (verificaConexao()){
                    loadErvas();
                }else{
                    Toast.makeText(SplashActivity.this, "Sem conexão", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        }, 2000);


    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public void insert (){
        modelPlanta = new ModelPlanta();
        modelPlanta.save();
    }

    private void loadErvas(){
        ervasAPI ervasGetApi = ervasAPI.RETROFIT.create(ervasAPI.class);
        Call<JSONResponse> call = ervasGetApi.getJSON();

        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

//                int code = response.code();
//                Log.d("RET_CODE_API", String.valueOf(code));

                data = new ArrayList<>(Arrays.asList(jsonResponse.getPlanta()));

                for (int i=0; i<data.size(); i++){
                    modelPlanta = new ModelPlanta();
                    modelPlanta.setId(data.get(i).getId());
                    modelPlanta.setNome_cientifico(data.get(i).getNome_cientifico());
                    modelPlanta.setNomes_populares(data.get(i).getNomes_populares());
                    modelPlanta.setFins_medicinais(data.get(i).getFins_medicinais());
                    modelPlanta.setFormas_de_uso(data.get(i).getFormas_de_uso());
                    modelPlanta.setRiscos_de_uso(data.get(i).getRiscos_de_uso());
                    modelPlanta.setImagens("");
                    modelPlanta.save();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("RET_API_ERROR", t.toString());
            }
        });
    }
}