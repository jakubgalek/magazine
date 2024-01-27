import java.util.Objects;
public class CTowar  {
    private String identyfikator;
    private int cena;
    private CData dataPrzyjecia;
    private int ilosc;
    public CTowar(String identyfikator, int cena, CData dataPrzyjecia, int ilosc) {
        this.identyfikator = identyfikator;
        this.cena = cena;
        this.dataPrzyjecia = dataPrzyjecia;
        this.ilosc = ilosc;
    }

    public String getIdentyfikator() {
        return identyfikator;
    }

    public int getCena() {
        return cena;
    }

    public CData getDataPrzyjecia() {
        return dataPrzyjecia;
    }
    public int getIlosc() {
        return ilosc;
    }
    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String toString() {
        return String.format("CTowar [Identyfikator: %s, Cena: %d, Data Przyjęcia: %s, Ilość: %d]",
                getIdentyfikator(), getCena(), getDataPrzyjecia().toString(), getIlosc());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTowar cTowar = (CTowar) o;
        return identyfikator.equals(cTowar.identyfikator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identyfikator);
    }

}