public class CProdukt extends CTowar implements Comparable<CProdukt> {

    private String kategoria;
    private String producent;
    private String model;
    private CData dataProdukcji;

    public CProdukt(String identyfikator, int cena, CData dataPrzyjecia, int ilosc,
                    String kategoria, String producent, String model, CData dataProdukcji) {
        super(identyfikator, cena, dataPrzyjecia, ilosc);
        this.kategoria = kategoria;
        this.producent = producent;
        this.model = model;
        this.dataProdukcji = dataProdukcji;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String getProducent() {
        return producent;
    }

    public String getModel() {
        return model;
    }

    public CData getDataProdukcji() {
        return dataProdukcji;
    }


    @Override
    public int compareTo(CProdukt other) {
        // Porównaj kategorie, a następnie identyfikatory dla sortowania
        int porownanieKategorii = this.getKategoria().compareTo(other.getKategoria());
        if (porownanieKategorii == 0) {
            return this.getIdentyfikator().compareTo(other.getIdentyfikator());
        }
        return porownanieKategorii;
    }
}
