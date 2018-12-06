package ervamed.com.br.ervamed.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import ervamed.com.br.ervamed.db.MyDatabase;

@Table(database = MyDatabase.class, name = "imagens_plantas")
public class ModelImagem extends BaseModel {
    @PrimaryKey()
    @Column
    int id;

    @Column
    @ForeignKey(tableClass = ModelPlanta.class)
    public int id_erva;

    @Column
    String url;

    @Column
    String encodedImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public int getId_erva() {
        return id_erva;
    }

    public void setId_erva(int id_erva) {
        this.id_erva = id_erva;
    }
}
