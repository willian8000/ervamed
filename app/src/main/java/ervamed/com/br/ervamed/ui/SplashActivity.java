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

    private ModelPlanta modelPlanta;
    private ModelImagem modelImagem;
    private ArrayList<Erva> data;
    private ArrayList<Imagem> imgs;
    private String encoded;

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
                    loadErvas();
                }else{
                    Toast.makeText(SplashActivity.this, "Sem conexão", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, 4000);
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable() && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    private static Bitmap baixarImagem(String url) throws IOException{
        URL endereco;
        InputStream inputStream;
        Bitmap imagem;

        endereco = new URL(url);
        inputStream = endereco.openStream();
        imagem = BitmapFactory.decodeStream(inputStream);
        Log.i("AsyncTask", "BASE_64_URL: " + url);
        inputStream.close();

        return imagem;
    }

    private class TarefaDownload extends AsyncTask<String, Void, Bitmap>{

        private int idErva;
        private int idImg;

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap imagemBitmap = null;

            try{
                Log.i("AsyncTask", "Baixando a imagem Thread: " + Thread.currentThread().getName());
                imagemBitmap = baixarImagem(params[0]);
                idErva = Integer.parseInt(params[1]);
                idImg = Integer.parseInt(params[2]);

            }catch (IOException e){
                Log.i("AsyncTask", e.getMessage());
            }

            return imagemBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            if(bitmap!=null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.i("AsyncTask", "Exibindo Bitmap Thread: " + Thread.currentThread().getName());

                /*
                * Update na tabela de Imanges para BASE64
                */
                SQLite.update(ModelImagem.class)
                        .set(ModelImagem_Table.encodedImage.eq(encoded))
                        .where(ModelImagem_Table.id_erva_id.is(idErva))
                        .and(ModelImagem_Table.id.is(idImg))
                        .async()
                        .execute(); // non-UI blocking

            }else{
                Log.i("AsyncTask", "Erro ao baixar a imagem " + Thread.currentThread().getName());
            }
        }
    }

    private void loadErvas(){
        ervasAPI ervasGetApi = ervasAPI.RETROFIT.create(ervasAPI.class);
        Call<JSONResponse> call = ervasGetApi.getJSON();

        call.enqueue(new Callback<JSONResponse>() {

            TarefaDownload download;

            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                int code = response.code();
                Log.d("RET_CODE_API", String.valueOf(code));

                data = new ArrayList<>(Arrays.asList(jsonResponse.getPlanta()));

                for (int i=0; i<data.size(); i++){
                    modelPlanta = new ModelPlanta();
                    modelPlanta.setId(data.get(i).getId());
                    modelPlanta.setNome_cientifico(data.get(i).getNome_cientifico());
                    modelPlanta.setNomes_populares(data.get(i).getNomes_populares());
                    modelPlanta.setFins_medicinais(data.get(i).getFins_medicinais());
                    modelPlanta.setFormas_de_uso(data.get(i).getFormas_de_uso());
                    modelPlanta.setRiscos_de_uso(data.get(i).getRiscos_de_uso());
                    modelPlanta.save();

                    imgs = new ArrayList<>(Arrays.asList(data.get(i).getImagens()));

                    for(int x=0; x<imgs.size(); x++){

                        download = new TarefaDownload();

                        modelImagem = new ModelImagem();
                        modelImagem.setId(imgs.get(x).getId());
                        modelImagem.setId_erva(data.get(i).getId());
                        modelImagem.setUrl("http://crs.unochapeco.edu.br/zend/plantas-medicinais/public/" + imgs.get(x).getUrl());
                        modelImagem.save();

                        //String url = "http://crs.unochapeco.edu.br/zend/plantas-medicinais/public/" + imgs.get(x).getUrl();
                        //String params[] = {url, String.valueOf(modelImagem.getId_erva()), String.valueOf(modelImagem.getId())};

                        //download.execute(params);



                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("RET_API_ERROR", t.toString());
            }
        });
    }
}
