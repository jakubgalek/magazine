public class CUrzadzenie extends CProdukt {
    private String procesor;
    private int ram;
    private String kartaGraficzna;
    private int dysk;

    public CUrzadzenie(String identyfikator, int cena, CData dataPrzyjecia, int ilosc,
                       String kategoria, String producent, String model, CData dataProdukcji,
                       String procesor, int ram, String kartaGraficzna, int dysk) {
        super(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji);
        this.procesor = procesor;
        this.ram = ram;
        this.kartaGraficzna = kartaGraficzna;
        this.dysk = dysk;
    }

    public String getProcesor() {
        return procesor;
    }

    public int getRam() {
        return ram;
    }

    public String getKartaGraficzna() {
        return kartaGraficzna;
    }

    public int getDysk() {
        return dysk;
    }

}
