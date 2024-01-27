public final class CLaptop extends CMobilny {
    private String ukladKlawiatury;
    private String klawiaturaNum;

    public CLaptop(String identyfikator, int cena, CData dataPrzyjecia, int ilosc,
                   String kategoria, String producent, String model, CData dataProdukcji,
                   String procesor, int ram, String kartaGraficzna, int dysk,
                   int czasPracy, String typMatrycy, String ukladKlawiatury, String klawiaturaNum) {
        super(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                procesor, ram, kartaGraficzna, dysk, czasPracy, typMatrycy);
        this.ukladKlawiatury = ukladKlawiatury;
        this.klawiaturaNum = klawiaturaNum;
    }

    public String getUkladKlawiatury() {
        return ukladKlawiatury;
    }

    public String getKlawiaturaNum() {
        return klawiaturaNum;
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
                getUkladKlawiatury(), getKlawiaturaNum(),"N\\A","N\\A","N\\A");
    }
}
