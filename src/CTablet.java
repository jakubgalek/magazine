public final class CTablet extends CMobilny {
    private String zewnKlawiatura;

    public CTablet(String identyfikator, int cena, CData dataPrzyjecia, int ilosc,
                   String kategoria, String producent, String model, CData dataProdukcji,
                   String procesor, int ram, String kartaGraficzna, int dysk,
                   int czasPracy, String typMatrycy, String zewnKlawiatura) {
        super(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                procesor, ram, kartaGraficzna, dysk, czasPracy, typMatrycy);
        this.zewnKlawiatura = zewnKlawiatura;
    }

    public String getZewnKlawiatura() {
        return zewnKlawiatura;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%s,%d," +
                        "%s,%s,%s,%s," +
                        "%s,%d,%s,%d," +
                        "%d,%s,%s,%s,%s,%s,%s",
                getIdentyfikator(), getCena(), getDataPrzyjecia(), getIlosc(), getKategoria(),
                getProducent(), getModel(), getDataProdukcji(), getProcesor(),
                getRam(), getKartaGraficzna(), getDysk(), getCzasPracy(), getTypMatrycy(),
                "N\\A", "N\\A", getZewnKlawiatura(), "N\\A", "N\\A");
    }

}
