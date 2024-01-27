public class CMobilny extends CUrzadzenie {
    private Integer czasPracy;
    private String typMatrycy;

    public CMobilny(String identyfikator, int cena, CData dataPrzyjecia, int ilosc,
                    String kategoria, String producent, String model, CData dataProdukcji,
                    String procesor, int ram, String kartaGraficzna, int dysk,
                    int czasPracy, String typMatrycy) {
        super(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                procesor, ram, kartaGraficzna, dysk);
        this.czasPracy = czasPracy;
        this.typMatrycy = typMatrycy;
    }

    public int getCzasPracy() {
        return czasPracy;
    }

    public String getTypMatrycy() {
        return typMatrycy;
    }
}
