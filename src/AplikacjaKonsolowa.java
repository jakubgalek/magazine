import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;


public class AplikacjaKonsolowa {

    // Obsługa wielu magazynów
    private final List<CMagazyn> magazyny;

    public AplikacjaKonsolowa() {
        this.magazyny = new ArrayList<>();
        // Domyślny magazyn
        this.magazyny.add(new CMagazyn());
    }

    public static void main(String[] args) {

        AplikacjaKonsolowa aplikacja = new AplikacjaKonsolowa();
        CMagazyn magazyn = aplikacja.magazyny.get(0);
        try {
            magazyn.wczytajZPliku("magazyn.csv");
            System.out.println("Dane magazynu zostały wczytane z pliku.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas wczytywania danych z pliku.");
        }

        Scanner scanner = new Scanner(System.in);
        int wybor = 0;

        do {
            System.out.println("Menu:");
            System.out.println("1. Wyświetl stan magazynowy");
            System.out.println("2. Dodaj towar");
            System.out.println("3. Sprzedaj towar");
            System.out.println("4. Usuń produkt z magazynu");
            System.out.println("5. Szukaj");
            System.out.println("6. Sortuj");
            System.out.println("7. Przyjmij towary (z pliku)");
            System.out.println("8. Wyjście");

            System.out.print("Wybierz opcję: ");

            try {
                wybor = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Musisz podać cyfrę od 1 do 10.");
                scanner.nextLine();
            }

            switch (wybor) {
                case 1:
                    magazyn.wyswietlStanMagazynu();
                    break;
                case 2:
                    System.out.println("Podaj identyfikator produktu: ");
                    String identyfikatorNowegoProduktu = scanner.next();
                    CTowar nowyProdukt = new CTowar(identyfikatorNowegoProduktu, 0,
                            new CData(1, 1, 2000), 0);

                    // Sprawdzenie, czy obiekt o podanym identyfikatorze już istnieje w zbiorze
                    if (magazyn.contains(nowyProdukt)) {
                        System.out.println("Produkt o podanym identyfikatorze już istnieje.");
                    }
                     else {
                        System.out.println("Podaj cenę produktu: ");
                        int cenaNowegoProduktu = scanner.nextInt();

                        System.out.println("Podaj datę przyjęcia produktu (w formacie dd.mm.yyyy): ");
                        String dataPrzyjeciaNowegoProduktu = scanner.next();
                        CData dataPrzyjeciaNowegoProduktuObj = new CData(dataPrzyjeciaNowegoProduktu);

                        System.out.println("Podaj ilość sztuk produktu: ");
                        int iloscNowegoProduktu = scanner.nextInt();

                        System.out.println("Podaj kategorię produktu (Laptop/Tablet/Stacjonarny): ");
                        String kategoriaNowegoProduktu = scanner.next();

                        System.out.println("Podaj producenta produktu: ");
                        String producentNowegoProduktu = scanner.next();

                        System.out.println("Podaj model produktu: ");
                        String modelNowegoProduktu = scanner.next();

                        System.out.println("Podaj datę produkcji produktu (w formacie dd.mm.yyyy): ");
                        String dataProdukcjiNowegoProduktu = scanner.next();
                        CData dataProdukcjiNowegoProduktuObj = new CData(dataProdukcjiNowegoProduktu);

                        System.out.println("Podaj procesor produktu: ");
                        String procesorNowegoProduktu = scanner.next();

                        System.out.println("Podaj ilość RAM produktu: ");
                        int ramNowegoProduktu = scanner.nextInt();

                        System.out.println("Podaj kartę graficzną produktu: ");
                        String kartaGraficznaNowegoProduktu = scanner.next();

                        System.out.println("Podaj rozmiar dysku produktu: ");
                        int dyskNowegoProduktu = scanner.nextInt();

                        switch (kategoriaNowegoProduktu) {
                            case "Laptop":
                                System.out.println("Podaj czas pracy baterii laptopa: ");
                                int czasPracyNowegoLaptopa = scanner.nextInt();

                                System.out.println("Podaj typ matrycy laptopa: ");
                                String typMatrycyNowegoLaptopa = scanner.next();

                                System.out.println("Podaj układ klawiatury laptopa: ");
                                String ukladKlawiaturyNowegoLaptopa = scanner.next();

                                System.out.println("Czy posiada klawiaturę numeryczną (Tak/Nie): ");
                                String klawiaturaNumNowegoLaptopa = scanner.next();

                                // Dodajemy nowy laptop do magazynu
                                magazyn.dodajProdukt(new CLaptop(identyfikatorNowegoProduktu, cenaNowegoProduktu,
                                        dataPrzyjeciaNowegoProduktuObj, iloscNowegoProduktu, kategoriaNowegoProduktu, producentNowegoProduktu,
                                        modelNowegoProduktu, dataProdukcjiNowegoProduktuObj, procesorNowegoProduktu, ramNowegoProduktu,
                                        kartaGraficznaNowegoProduktu, dyskNowegoProduktu, czasPracyNowegoLaptopa, typMatrycyNowegoLaptopa,
                                        ukladKlawiaturyNowegoLaptopa, klawiaturaNumNowegoLaptopa));
                                break;

                            case "Tablet":
                                System.out.println("Podaj czas pracy tabletu: ");
                                int czasPracyNowegoTabletu = scanner.nextInt();

                                System.out.println("Podaj typ matrycy tabletu: ");
                                String typMatrycyNowegoTabletu = scanner.next();

                                System.out.println("Czy tablet posiada zewnętrzną klawiaturę? (Tak/Nie): ");
                                String zewnKlawiaturaNowegoTabletu = scanner.next();

                                // Dodajemy nowy tablet do magazynu
                                magazyn.dodajProdukt(new CTablet(identyfikatorNowegoProduktu, cenaNowegoProduktu,
                                        dataPrzyjeciaNowegoProduktuObj, iloscNowegoProduktu, kategoriaNowegoProduktu, producentNowegoProduktu,
                                        modelNowegoProduktu, dataProdukcjiNowegoProduktuObj, procesorNowegoProduktu, ramNowegoProduktu,
                                        kartaGraficznaNowegoProduktu, dyskNowegoProduktu, czasPracyNowegoTabletu, typMatrycyNowegoTabletu,
                                        zewnKlawiaturaNowegoTabletu));
                                break;

                            case "Stacjonarny":
                                System.out.println("Podaj typ obudowy stacjonarnego: ");
                                String typObudowyNowegoStacjonarnego = scanner.next();

                                System.out.println("Podaj standard płyty głównej stacjonarnego: ");
                                String standardPlytyGlownejNowegoStacjonarnego = scanner.next();

                                // Dodajemy nowy stacjonarny do magazynu
                                magazyn.dodajProdukt(new CStacjonarny(identyfikatorNowegoProduktu, cenaNowegoProduktu,
                                        dataPrzyjeciaNowegoProduktuObj, iloscNowegoProduktu, kategoriaNowegoProduktu, producentNowegoProduktu,
                                        modelNowegoProduktu, dataProdukcjiNowegoProduktuObj, procesorNowegoProduktu, ramNowegoProduktu,
                                        kartaGraficznaNowegoProduktu, dyskNowegoProduktu, typObudowyNowegoStacjonarnego,
                                        standardPlytyGlownejNowegoStacjonarnego));
                                break;

                            default:
                                break;
                        }
                    }
                    break;

                case 3:
                    // Implementacja sprzedaży towaru
                    System.out.println("Podaj identyfikator produktu do sprzedaży: ");
                    String identyfikatorSprzedazy = scanner.next();

                    System.out.println("Podaj ilość sztuk do sprzedaży: ");
                    int iloscSprzedanych = scanner.nextInt();

                    magazyn.sprzedaj(identyfikatorSprzedazy, iloscSprzedanych);
                    break;
                case 4:
                    System.out.print("Podaj identyfikator produktu do usunięcia: ");
                    String identyfikatorDoUsuniecia = scanner.next();

                    magazyn.usunProdukt(identyfikatorDoUsuniecia);
                    break;
                case 5:
                    // Zapytaj użytkownika o kryterium
                    System.out.print("Podaj kryterium wyszukiwania (np. identyfikator, cena, kategoria): ");
                    String kryterium = scanner.next();

                    // Zapytaj użytkownika o wartość
                    System.out.print("Podaj wartość do wyszukania: ");
                    String wartosc = scanner.next();

                    // Przykład wyszukiwania produktów według kryterium
                    List<CTowar> znalezioneProdukty = magazyn.wyszukajProduktyWedlugKryterium(kryterium, wartosc);

                    // Wyświetlanie znalezionych produktów
                    System.out.println("Znalezione produkty według kryterium '" + kryterium + "':");
                    System.out.println("identyfikator,cena,dataPrzyjecia,ilosc,kategoria,producent,model,dataProdukcji,procesor,ram,kartaGraficzna,dysk,czasPracy,typMatrycy,ukladKlawiatury,klawiaturaNum,zewnKlawiatura,typObudowy,standardPlytyGlownej");
                    for (CTowar produkt : znalezioneProdukty) {
                        System.out.println(produkt);
                    }
                    break;
                case 6:
                    System.out.println("Sortowanie według ceny:");
                    System.out.println("1. Rosnąco");
                    System.out.println("2. Malejąco");
                    System.out.print("Wybierz opcję: ");
                    int wyborSortowania = scanner.nextInt();

                    switch (wyborSortowania) {
                        case 1:
                            magazyn.sortowanieWedlugIdentyfikatora(false);
                            System.out.println("Posortowano rosnąco według ceny.");
                            magazyn.wyswietlStanMagazynu();
                            break;
                        case 2:
                            magazyn.sortowanieWedlugIdentyfikatora(true);
                            System.out.println("Posortowano malejąco według ceny.");
                            magazyn.wyswietlStanMagazynu();
                            break;
                        default:
                            System.out.println("Nieprawidłowy wybór.");
                    }
                    break;
                case 7:
                    System.out.print("Podaj nazwę pliku z listą nowych towarów (np. lista_nowych.csv): ");
                    scanner.nextLine();
                    String nazwaPliku = scanner.nextLine();

                    try {
                        magazyn.przyjmijTowaryZPliku(nazwaPliku);
                        System.out.println("Pomyślnie załadowano towary z pliku.");
                    } catch (IOException g) {
                        g.printStackTrace();
                        System.out.println("Błąd podczas wczytywania danych z pliku.");
                    }
                    break;
                case 8:
                    try {
                        magazyn.zapiszDoCSV("magazyn.csv");
                        System.out.println("Zaktualizowany stan magazynu zapisano do pliku CSV.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Błąd podczas zapisywania danych do pliku CSV.");
                    }
                    System.out.println("Zakończono program.");
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór.");
            }
        } while (wybor != 8);
    }
}
