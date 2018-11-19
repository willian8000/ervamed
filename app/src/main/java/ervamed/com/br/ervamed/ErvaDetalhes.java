package ervamed.com.br.ervamed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ervamed.com.br.ervamed.model.ModelPlanta;

public class ErvaDetalhes extends AppCompatActivity {

    private ImageView imageErva;

    public TextView nomeCientifico, nomePopulares, finsMedicinais, formasUso, riscosUso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erva_detalhes);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();

        nomeCientifico = findViewById(R.id.nome_cientifico);
        nomePopulares = findViewById(R.id.nomes_popuplares_detalhes);
        finsMedicinais = findViewById(R.id.fins_medicinais);
        formasUso = findViewById(R.id.formas_de_uso);
        riscosUso = findViewById(R.id.riscos_de_uso);

        nomeCientifico.setText(bundle.getString("nomeCientifico"));
        nomePopulares.setText(bundle.getString("nomePopulares"));
        finsMedicinais.setText(bundle.getString("FinsMedicinais"));
        formasUso.setText(bundle.getString("FormasUso"));
        riscosUso.setText(bundle.getString("RiscosUso"));




    }


}
