public final class CStacjonarny extends CUrzadzenie {
    private String typObudowy;
    private String standardPlytyGlownej;

    public CStacjonarny(String identyfikator, int cena, CData dataPrzyjecia, int ilosc,
                        String kategoria, String producent, String model, CData dataProdukcji,
                        String procesor, int ram, String kartaGraficzna, int dysk,
                        String typObudowy, String standardPlytyGlownej) {
        super(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                procesor, ram, kartaGraficzna, dysk);
        this.typObudowy = typObudowy;
        this.standardPlytyGlownej = standardPlytyGlownej;
    }

    public String gettypObudowy() {
        return typObudowy;
    }
    public String getstandardPlytyGlownej() {
        return standardPlytyGlownej;
    }
    @Override
    public String toString() {
        return String.format("%s,%d,%s,%d," +
                        "%s,%s,%s,%s," +
                        "%s,%d,%s,%d," +
                        "%s,%s,%s,%s,%s,%s,%s",
                getIdentyfikator(), getCena(), getDataPrzyjecia(), getIlosc() ,getKategoria(),
                getProducent(), getModel(), getDataProdukcji(), getProcesor(),
                getRam(), getKartaGraficzna(), getDysk(),"N\\A", "N\\A", "N\\A", "N\\A", "N\\A",
                gettypObudowy(), getstandardPlytyGlownej());
    }
}
