import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

public class CMagazyn {
    private List<CTowar> magazyn;

    public CMagazyn() {
        magazyn = new ArrayList<>();
    }

    public boolean contains(CTowar produkt) {
        return magazyn.contains(produkt);
    }
    public void wczytajZPliku(String nazwaPliku) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nazwaPliku))) {
            String line;
            // Pomijamy pierwszy wiersz z nagłówkami
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] dane = line.split(",");
                if (dane.length >= 19) {
                    String identyfikator = dane[0].trim();
                    int cena = Integer.parseInt(dane[1].trim());
                    CData dataPrzyjecia = new CData(dane[2].trim());
                    int ilosc = Integer.parseInt(dane[3].trim());
                    String kategoria = dane[4].trim();
                    String producent = dane[5].trim();
                    String model = dane[6].trim();
                    CData dataProdukcji = new CData(dane[7].trim());
                    String procesor = dane[8].trim();
                    int ram = Integer.parseInt(dane[9].trim());
                    String kartaGraficzna = dane[10].trim();
                    int dysk = Integer.parseInt(dane[11].trim());
                    Integer czasPracy;
                    if ("N\\A".equals(dane[12].trim())) {
                        czasPracy = 0;
                    } else {
                        czasPracy = Integer.parseInt(dane[12].trim());
                    }
                    String typMatrycy = dane[13].trim();
                    String ukladKlawiatury = dane[14].trim();
                    String klawiaturaNum = dane[15].trim();
                    String zewnKlawiatura = dane[16].trim();
                    String typObudowy = dane[17].trim();
                    String standardPlytyGlownej = dane[18].trim();

                    // Tworzymy odpowiedni obiekt w zależności od kategorii
                    switch (kategoria) {
                        case "Laptop":
                            magazyn.add(new CLaptop(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                                    procesor, ram, kartaGraficzna, dysk, czasPracy, typMatrycy, ukladKlawiatury, klawiaturaNum));
                            break;
                        case "Tablet":
                            magazyn.add(new CTablet(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                                    procesor, ram, kartaGraficzna, dysk, czasPracy, typMatrycy, zewnKlawiatura));
                            break;
                        case "Stacjonarny":
                            magazyn.add(new CStacjonarny(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                                    procesor, ram, kartaGraficzna, dysk, typObudowy, standardPlytyGlownej));
                            break;
                        default:
                            // Domyślnie tworzymy obiekt klasy CTowar
                            magazyn.add(new CTowar(identyfikator, cena, dataPrzyjecia, ilosc));
                    }
                }
            }
        }
    }
    public void wyswietlStanMagazynu() {
        try {
            if (magazyn.isEmpty()) {
                System.out.println("Magazyn jest pusty.");
            } else {
                for (CTowar towar : magazyn) {
                    if (towar != null) {
                        System.out.println(towar);
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Wystąpił wyjątek NullPointerException podczas wyświetlania stanu magazynu.");
            e.printStackTrace();
        }
    }


    public boolean czyTowarIstnieje(String identyfikator) {
        for (CTowar towar : magazyn) {
            if (towar != null && towar.getIdentyfikator().equals(identyfikator)) {
                return true;
            }
        }
        return false;
    }

    public void dodajProdukt(CTowar nowyTowar) {
        if (czyTowarIstnieje(nowyTowar.getIdentyfikator())) {
            System.out.println("Towar o podanym identyfikatorze już istnieje w magazynie.");
        } else {
            // Towar o podanym identyfikatorze nie istnieje, można go dodać
            magazyn.add(nowyTowar);
            System.out.println("Nowy towar został dodany do magazynu.");
        }
    }


    public void usunProdukt(String identyfikator) {
        // Przeszukujemy listę w poszukiwaniu produktu o podanym identyfikatorze
        for (CTowar produkt : magazyn) {
            if (produkt != null && produkt.getIdentyfikator().equals(identyfikator)) {
                System.out.println("Usunięto produkt z magazynu:" + produkt);
                magazyn.remove(produkt);
                return; // Przerywamy pętlę po usunięciu produktu
            }
        }

        // Jeśli produkt o podanym identyfikatorze nie został znaleziony
        System.out.println("Produkt o identyfikatorze " + identyfikator + " nie istnieje w magazynie.");
    }

    public void sprzedaj(String identyfikator, int iloscSprzedanych) {
        for (CTowar towar : magazyn) {
            if (towar != null && towar.getIdentyfikator().equals(identyfikator) && towar instanceof CProdukt) {
                CProdukt produkt = (CProdukt) towar;
                if (produkt.getIlosc() >= iloscSprzedanych) {
                    produkt.setIlosc(produkt.getIlosc() - iloscSprzedanych);
                    System.out.println("Sprzedano " + iloscSprzedanych + " sztuk produktu o identyfikatorze " + identyfikator);
                    System.out.println("Zaktualizowany stan magazynu:");
                    wyswietlStanMagazynu();
                    return;
                } else {
                    System.out.println("Nie można sprzedać " + iloscSprzedanych + " sztuk. Za mała dostępna ilość.");
                    return;
                }
            }
        }

        System.out.println("Produkt o identyfikatorze " + identyfikator + " nie istnieje w magazynie lub nie jest produktem.");
    }

    public void zapiszDoCSV(String nazwaPliku) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nazwaPliku))) {
            // Nagłówki
            bw.write("identyfikator,cena,dataPrzyjecia,ilosc,kategoria,producent,model,dataProdukcji,procesor,ram,kartaGraficzna,dysk,czasPracy,typMatrycy,ukladKlawiatury,klawiaturaNum,zewnKlawiatura,typObudowy,standardPlytyGlownej\n");

            // Dane
            for (CTowar towar : magazyn) {
                if (towar != null) {
                    bw.write(towar.toString() + "\n");
                }
            }
        }
    }

    public void sortowanieWedlugIdentyfikatora(boolean odwrocone) {
        Comparator<CTowar> comparator = Comparator.comparing(CTowar::getIdentyfikator);

        if (odwrocone) {
            comparator = comparator.reversed();
        }

        magazyn.sort(Comparator.nullsFirst(comparator)); // Zmiana na nullsFirst
    }


    public CTowar znajdzProduktPoIdentyfikatorze(String identyfikator) {
        for (CTowar towar : magazyn) {
            if (towar != null && towar.equals(new CTowar(identyfikator, 0, new CData(), 0))) {
                return towar;
            }
        }
        return null;
    }


    public List<CTowar> wyszukajProduktyWedlugKryterium(String kryterium, String wartosc) {
        List<CTowar> znalezioneProdukty = new ArrayList<>();

        // Zastosuj filtry w zależności od wybranego kryterium
        switch (kryterium.toLowerCase()) {
            case "identyfikator":
                for (CTowar towar : magazyn) {
                    if (towar != null && towar.getIdentyfikator().equalsIgnoreCase(wartosc)) {
                        znalezioneProdukty.add(towar);
                    }
                }
                break;
            case "cena":
                for (CTowar towar : magazyn) {
                    if (towar != null && towar instanceof CProdukt) {
                        CProdukt produkt = (CProdukt) towar;
                        if (Integer.toString(produkt.getCena()).equalsIgnoreCase(wartosc)) {
                            znalezioneProdukty.add(produkt);
                        }
                    }
                }
                break;
            // Dodaj kolejne kryteria według potrzeb
            case "kategoria":
                for (CTowar towar : magazyn) {
                    if (towar != null && towar instanceof CProdukt) {
                        CProdukt produkt = (CProdukt) towar;
                        if (produkt.getKategoria().equalsIgnoreCase(wartosc)) {
                            znalezioneProdukty.add(produkt);
                        }
                    }
                }
                break;
            default:
                System.out.println("Nieprawidłowe kryterium wyszukiwania.");
        }

        return znalezioneProdukty;
    }

    public void przyjmijTowaryZPliku(String nazwaPliku) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nazwaPliku))) {
            String line;
            // Pomijamy pierwszy wiersz z nagłówkami
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] dane = line.split(",");
                if (dane.length >= 19) {
                    String identyfikator = dane[0].trim();
                    int cena = Integer.parseInt(dane[1].trim());
                    CData dataPrzyjecia = new CData(dane[2].trim());
                    int ilosc = Integer.parseInt(dane[3].trim());
                    String kategoria = dane[4].trim();
                    String producent = dane[5].trim();
                    String model = dane[6].trim();
                    CData dataProdukcji = new CData(dane[7].trim());
                    String procesor = dane[8].trim();
                    int ram = Integer.parseInt(dane[9].trim());
                    String kartaGraficzna = dane[10].trim();
                    int dysk = Integer.parseInt(dane[11].trim());
                    Integer czasPracy;
                    if ("N\\A".equals(dane[12].trim())) {
                        czasPracy = 0;
                    } else {
                        czasPracy = Integer.parseInt(dane[12].trim());
                    }
                    String typMatrycy = dane[13].trim();
                    String ukladKlawiatury = dane[14].trim();
                    String klawiaturaNum = dane[15].trim();
                    String zewnKlawiatura = dane[16].trim();
                    String typObudowy = dane[17].trim();
                    String standardPlytyGlownej = dane[18].trim();

                    // Tworzymy odpowiedni obiekt w zależności od kategorii
                    switch (kategoria) {
                        case "Laptop":
                            dodajProdukt(new CLaptop(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                                    procesor, ram, kartaGraficzna, dysk, czasPracy, typMatrycy, ukladKlawiatury, klawiaturaNum));
                            break;
                        case "Tablet":
                            dodajProdukt(new CTablet(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                                    procesor, ram, kartaGraficzna, dysk, czasPracy, typMatrycy, zewnKlawiatura));
                            break;
                        case "Stacjonarny":
                            dodajProdukt(new CStacjonarny(identyfikator, cena, dataPrzyjecia, ilosc, kategoria, producent, model, dataProdukcji,
                                    procesor, ram, kartaGraficzna, dysk, typObudowy, standardPlytyGlownej));
                            break;
                        default:
                            // Domyślnie tworzymy obiekt klasy CTowar
                            dodajProdukt(new CTowar(identyfikator, cena, dataPrzyjecia, ilosc));
                    }
                }
            }
            System.out.println("Przyjęto towary do magazynu z pliku: " + nazwaPliku);
            System.out.println("Zaktualizowany stan magazynu:");
            wyswietlStanMagazynu();
        }
    }
}
