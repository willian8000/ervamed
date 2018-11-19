package ervamed.com.br.ervamed.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.w3c.dom.Text;

import ervamed.com.br.ervamed.db.MyDatabase;

@Table(database = MyDatabase.class, name = "plantas")

public class ModelPlanta extends BaseModel {

    @PrimaryKey()
    @Column
    int id;

    @Column
    String nome_cientifico;

    @Column
    String nomes_populares;

    @Column
    String fins_medicinais;

    @Column
    String formas_de_uso;

    @Column
    String riscos_de_uso;

    @Column
    String imagens;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome_cientifico() {
        return nome_cientifico;
    }

    public void setNome_cientifico(String nome_cientifico) {
        this.nome_cientifico = nome_cientifico;
    }

    public String getNomes_populares() {
        return nomes_populares;
    }

    public void setNomes_populares(String nomes_populares) {
        this.nomes_populares = nomes_populares;
    }

    public String getFins_medicinais() {
        return fins_medicinais;
    }

    public void setFins_medicinais(String fins_medicinais) {
        this.fins_medicinais = fins_medicinais;
    }

    public String getFormas_de_uso() {
        return formas_de_uso;
    }

    public void setFormas_de_uso(String formas_de_uso) {
        this.formas_de_uso = formas_de_uso;
    }

    public String getRiscos_de_uso() {
        return riscos_de_uso;
    }

    public void setRiscos_de_uso(String riscos_de_uso) {
        this.riscos_de_uso = riscos_de_uso;
    }

    public String getImagens() {
        return imagens;
    }

    public void setImagens(String imagens) {
        this.imagens = imagens;
    }

}
