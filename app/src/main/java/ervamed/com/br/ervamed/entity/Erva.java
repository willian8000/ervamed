package ervamed.com.br.ervamed.entity;

/*
 * Classe para buscar os registros (json) pela API
 */
public class Erva {

    public int id;
    private String nome_cientifico;
    private String nomes_populares;
    private String fins_medicinais;
    private String formas_de_uso;
    private String riscos_de_uso;
    private Imagem[] imagens;

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

    public Imagem[] getImagens() {
        return imagens;
    }

    public void setImagens(Imagem[] imagens) {
        this.imagens = imagens;
    }
}
