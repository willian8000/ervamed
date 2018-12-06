package ervamed.com.br.ervamed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import ervamed.com.br.ervamed.adapter.RecyclerViewAdapter;
import ervamed.com.br.ervamed.model.ModelImagem;
import ervamed.com.br.ervamed.model.ModelImagem_Table;

public class ErvaDetalhes extends AppCompatActivity {

    private ImageView imageErva;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private TextView nomeCientifico, nomePopulares, finsMedicinais, formasUso, riscosUso;
    private int ervaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erva_detalhes);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();

        //imageErva = findViewById(R.id.img_erva_detalhes);
        nomeCientifico = findViewById(R.id.nome_cientifico);
        nomePopulares = findViewById(R.id.nomes_popuplares_detalhes);
        finsMedicinais = findViewById(R.id.fins_medicinais);
        formasUso = findViewById(R.id.formas_de_uso);
        riscosUso = findViewById(R.id.riscos_de_uso);

        ervaID = bundle.getInt("ErvaID");
        ArrayList<ModelImagem> listImgErva = (ArrayList<ModelImagem>) new SQLite().select().from(ModelImagem.class).where(ModelImagem_Table.id_erva_id.eq(ervaID)).queryList();

        for (ModelImagem m : listImgErva ) {
            mImageUrls.add(m.getUrl());
        }

        initRecyclerView();

        //Picasso.get().load(listImgErva.get(0).getUrl()).into(imageErva);
        //byte[] decodedString = Base64.decode(listImgErva.get(0).getEncodedImage(), Base64.URL_SAFE);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        nomeCientifico.setText(bundle.getString("nomeCientifico"));
        nomePopulares.setText(bundle.getString("nomePopulares"));
        finsMedicinais.setText(bundle.getString("FinsMedicinais"));
        formasUso.setText(bundle.getString("FormasUso"));
        riscosUso.setText(bundle.getString("RiscosUso"));
        //imageErva.setImageBitmap(decodedByte);

    }

    private void initRecyclerView(){
        Log.d("INIT_RECYCLER_VIEW", "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.img_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageUrls);
        recyclerView.setAdapter(adapter);
    }


}
