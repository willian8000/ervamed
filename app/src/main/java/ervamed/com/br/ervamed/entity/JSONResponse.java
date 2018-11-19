package ervamed.com.br.ervamed.entity;


public class JSONResponse {
    private int total_items;
    private Plantas _embedded;

    public Erva[] getPlanta() {
        return _embedded.getErvas();
    }
    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

}
