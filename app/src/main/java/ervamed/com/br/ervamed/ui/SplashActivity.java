package ervamed.com.br.ervamed.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import ervamed.com.br.ervamed.LoadErvas;
import ervamed.com.br.ervamed.MainActivity;
import ervamed.com.br.ervamed.R;
import ervamed.com.br.ervamed.entity.Erva;
import ervamed.com.br.ervamed.entity.Imagem;
import ervamed.com.br.ervamed.entity.JSONResponse;
import ervamed.com.br.ervamed.model.ModelImagem;
import ervamed.com.br.ervamed.model.ModelImagem_Table;
import ervamed.com.br.ervamed.model.ModelPlanta;
import ervamed.com.br.ervamed.service.ervasAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //new BackgroundSplashTask().execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (verificaConexao()){
                    LoadErvas loadErvas = new LoadErvas();
                    loadErvas.loadErvas();
                }else{
                    Toast.makeText(SplashActivity.this, "Sem conexão", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, 4000);
    }

    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable() && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }





}
