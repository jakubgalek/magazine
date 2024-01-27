import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;


public class AplikacjaGraficzna {
    private static CMagazyn magazyn;
    private static JTextArea textArea;

    public static void main(String[] args) {
        magazyn = new CMagazyn();

        // Utwórz główny wątek interfejsu graficznego
        javax.swing.SwingUtilities.invokeLater(AplikacjaGraficzna::createAndShowGUI);
            wczytajDaneZPliku();
    }

    private static void createAndShowGUI() {
        // Utwórz ramkę
        JFrame frame = new JFrame("Obsługa magazynu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Utwórz panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        // Dodaj JTextArea
        textArea = new JTextArea(20, 70);
        textArea.setEditable(false); // Nie pozwól na edycję tekstu

        // Dodaj JScrollPane, aby umożliwić przewijanie tekstu w przypadku dużo informacji
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        // Dodaj komponenty do panelu
        JButton viewMagazineButton = new JButton("Wyświetl stan magazynowy");
        JButton addButton = new JButton("Dodaj towar");
        JButton sellButton = new JButton("Sprzedaj towar");
        JButton removeButton = new JButton("Usuń produkt z magazynu");
        JButton searchButton = new JButton("Szukaj");
        JButton sortButton = new JButton("Sortuj");
        JButton takeFromFileButton = new JButton("Przyjmij towary (z pliku)");
        JButton saveAndExitButton = new JButton("Zapisz do CSV i wyjdź");


        // Dodaj obsługę zdarzenia dla przycisku "Wyświetl stan magazynowy"
        viewMagazineButton.addActionListener(e -> {
            textArea.setText("");
            wyswietlStanMagazynu();
        });

        // Dodaj obsługę zdarzenia dla przycisku "Dodaj towar"
        addButton.addActionListener(e -> dodajTowar());
        // Dodaj obsługę zdarzenia dla przycisku "Usun towar"
        removeButton.addActionListener(e -> usunTowar());
        // Dodaj obsługę zdarzenia dla przycisku "Sprzedaj towar"
        sellButton.addActionListener(e -> sprzedajTowar());
        // Dodaj obsługę zdarzenia dla przycisku "Przyjmij z pliku"
        takeFromFileButton.addActionListener(e -> {
            try {
                // Utwórz okno dialogowe do wyboru pliku
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                // Jeśli użytkownik wybrał plik
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Pobierz wybrany plik
                    File selectedFile = fileChooser.getSelectedFile();

                    // Wywołaj metodę przyjmowania towarów z pliku
                    magazyn.przyjmijTowaryZPliku(selectedFile.getAbsolutePath());
                }
            } catch (IOException j) {
                throw new RuntimeException(j);
            }

        });

        // Dodaj obsługę zdarzenia dla przycisku "Sortuj"
        sortButton.addActionListener(e -> {
            // Zapytaj użytkownika o kierunek sortowania
            String[] options = {"Rosnąco", "Malejąco"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "Wybierz kierunek sortowania:",
                    "Sortowanie",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            boolean descending = (result == 1); // result == 1 malejąco, w przeciwnym razie rosnąco

            // Wywołaj metodę sortowania
            magazyn.sortowanieWedlugIdentyfikatora(descending);
            textArea.setText("");
            magazyn.wyswietlStanMagazynu();
        });


        // Dodaj obsługę zdarzenia dla przycisku "Szukaj"
        searchButton.addActionListener(e -> {
            // Zapytaj użytkownika o kryterium
            String kryterium = JOptionPane.showInputDialog(null, "Podaj kryterium wyszukiwania (np. identyfikator, cena, kategoria):");

            if (kryterium == null){
                // Obsłuż przypadek, gdy użytkownik anuluje wprowadzanie kryterium lub zostawi je puste
                JOptionPane.showMessageDialog(null, "Kryterium nie może być puste. Anulowano wyszukiwanie.");
                return;
            }
            // Zapytaj użytkownika o wartość
            String wartosc = JOptionPane.showInputDialog(null, "");

            // Przykład wyszukiwania produktów według kryterium
            List<CTowar> znalezioneProdukty = magazyn.wyszukajProduktyWedlugKryterium(kryterium, wartosc);

            // Wyświetlanie znalezionych produktów
            textArea.setText("");
            textArea.append("Znalezione produkty według kryterium '" + kryterium + "':\n");
            textArea.append("identyfikator,cena,dataPrzyjecia,ilosc,kategoria,producent,model,dataProdukcji,procesor,ram,kartaGraficzna,dysk,czasPracy,typMatrycy,ukladKlawiatury,klawiaturaNum,zewnKlawiatura,typObudowy,standardPlytyGlownej\n");
            for (CTowar produkt : znalezioneProdukty) {
                textArea.append(produkt + "\n");
            }
        });

        // Dodaj obsługę zdarzenia dla przycisku "Zapisz do CSV i wyjdź"
        saveAndExitButton.addActionListener(e -> {
            try {
                magazyn.zapiszDoCSV("magazyn.csv");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            zamknijAplikacje();
        });


        // Dodaj komponenty do panelu
        panel.add(viewMagazineButton);
        panel.add(addButton);
        panel.add(sellButton);
        panel.add(removeButton);
        panel.add(searchButton);
        panel.add(sortButton);
        panel.add(takeFromFileButton);
        panel.add(saveAndExitButton);

        // Dodaj panel do ramki
        frame.getContentPane().add(panel);

        // Ustaw rozmiar ramki
        frame.setSize(800, 600);

        // Wyśrodkuj ramkę na ekranie
        frame.setLocationRelativeTo(null);

        // Ustaw widoczność ramki
        frame.setVisible(true);

        // Przekieruj standardowe wyjście do JTextArea
        redirectSystemStreams();
    }

    private static void wczytajDaneZPliku() {
        try {
            magazyn.wczytajZPliku("magazyn.csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas wczytywania danych z pliku.");
        }
    }


    private static void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    private static void updateTextArea(final String text) {
        SwingUtilities.invokeLater(() -> textArea.append(text));
    }


    // Metody wywoływane w addActionListener
    private static void wyswietlStanMagazynu() {
        magazyn.wyswietlStanMagazynu();
    }

    private static void dodajTowar() {
        // Wybór kategorii
        Object[] options = {"Laptop", "Tablet", "Stacjonarny"};
        int categoryChoice = JOptionPane.showOptionDialog(null, "Wybierz kategorię towaru:", "Wybór kategorii",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (categoryChoice == -1) {
            // Użytkownik anulował wybór kategorii
            return;
        }

        // Pobierz dane wspólne dla wszystkich kategorii
        JTextField identyfikatorField = new JTextField();
        JTextField cenaField = new JTextField();

        Object[] commonFields = {
                "Identyfikator:", identyfikatorField,
                "Cena:", cenaField,
        };

        int commonOption = JOptionPane.showConfirmDialog(null, commonFields, "Dodaj nowy towar", JOptionPane.OK_CANCEL_OPTION);
        if (commonOption != JOptionPane.OK_OPTION) {
            // Użytkownik anulował wprowadzanie danych
            return;
        }

        // Pobierz dane z pól tekstowych
        String identyfikator = identyfikatorField.getText();
        int cena = Integer.parseInt(cenaField.getText());

        switch (categoryChoice) {
            case 0: // Laptop
                JTextField czasPracyField = new JTextField();
                JTextField typMatrycyField = new JTextField();
                JTextField ukladKlawiaturyField = new JTextField();
                JTextField klawiaturaNumField = new JTextField();

                Object[] laptopFields = {
                        "Czas pracy baterii (w godzinach):", czasPracyField,
                        "Typ matrycy laptopa:", typMatrycyField,
                        "Układ klawiatury laptopa:", ukladKlawiaturyField,
                        "Czy posiada klawiaturę numeryczną (Tak/Nie):", klawiaturaNumField,
                };

                int laptopOption = JOptionPane.showConfirmDialog(null, laptopFields, "Dodaj nowy laptop", JOptionPane.OK_CANCEL_OPTION);
                if (laptopOption != JOptionPane.OK_OPTION) {
                    return;
                }

                int czasPracy = Integer.parseInt(czasPracyField.getText());
                String typMatrycy = typMatrycyField.getText();
                String ukladKlawiatury = ukladKlawiaturyField.getText();
                String klawiaturaNum = klawiaturaNumField.getText();

                magazyn.dodajProdukt(new CLaptop(identyfikator, cena, new CData(), 0, "", "", "", new CData(), "", 0, "", 0, czasPracy, typMatrycy, ukladKlawiatury, klawiaturaNum));
                break;

            case 1: // Tablet
                JTextField czasPracyTabletField = new JTextField();
                JTextField typMatrycyTabletField = new JTextField();
                JTextField zewnKlawiaturaTabletField = new JTextField();

                Object[] tabletFields = {
                        "Czas pracy tabletu (w godzinach):", czasPracyTabletField,
                        "Typ matrycy tabletu:", typMatrycyTabletField,
                        "Czy tablet posiada zewnętrzną klawiaturę? (Tak/Nie):", zewnKlawiaturaTabletField,
                };

                int tabletOption = JOptionPane.showConfirmDialog(null, tabletFields, "Dodaj nowy tablet", JOptionPane.OK_CANCEL_OPTION);
                if (tabletOption != JOptionPane.OK_OPTION) {
                    return;
                }

                int czasPracyTablet = Integer.parseInt(czasPracyTabletField.getText());
                String typMatrycyTablet = typMatrycyTabletField.getText();
                String zewnKlawiaturaTablet = zewnKlawiaturaTabletField.getText();

                magazyn.dodajProdukt(new CTablet(identyfikator, cena, new CData(), 0, "", "", "", new CData(), "", 0, "", 0, czasPracyTablet, typMatrycyTablet, zewnKlawiaturaTablet));
                break;

            case 2: // Stacjonarny
                JTextField typObudowyField = new JTextField();
                JTextField standardPlytyGlownejField = new JTextField();

                Object[] stacjonarnyFields = {
                        "Typ obudowy stacjonarnego:", typObudowyField,
                        "Standard płyty głównej stacjonarnego:", standardPlytyGlownejField,
                };

                int stacjonarnyOption = JOptionPane.showConfirmDialog(null, stacjonarnyFields, "Dodaj nowy stacjonarny", JOptionPane.OK_CANCEL_OPTION);
                if (stacjonarnyOption != JOptionPane.OK_OPTION) {
                    return;
                }

                String typObudowy = typObudowyField.getText();
                String standardPlytyGlownej = standardPlytyGlownejField.getText();

                magazyn.dodajProdukt(new CStacjonarny(identyfikator, cena, new CData(), 0, "", "", "", new CData(), "", 0, "", 0, typObudowy, standardPlytyGlownej));
                break;

            default:
                break;
        }
    }
    private static void sprzedajTowar() {
            // Implementacja sprzedaży towaru
            JTextField identyfikatorField = new JTextField();
            JTextField iloscField = new JTextField();

            Object[] message = {
                    "Podaj identyfikator produktu do sprzedaży:", identyfikatorField,
                    "Podaj ilość sztuk do sprzedaży:", iloscField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Sprzedaż towaru", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Pobierz dane z pól tekstowych
                String identyfikatorSprzedazy = identyfikatorField.getText();
                int iloscSprzedanych = Integer.parseInt(iloscField.getText());

                // Wywołaj metodę sprzedaj z odpowiednimi danymi
                magazyn.sprzedaj(identyfikatorSprzedazy, iloscSprzedanych);
            }
    }

    private static void usunTowar() {
        JTextField identyfikatorField = new JTextField();

        Object[] message = {
                "Podaj identyfikator produktu do usunięcia:", identyfikatorField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Usuń produkt z magazynu", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Pobierz dane z pola tekstowego
            String idToDelete = identyfikatorField.getText();

            // Wywołaj metodę usunProdukt z odpowiednimi danymi
            magazyn.usunProdukt(idToDelete);
        }
    }

    private static void zamknijAplikacje() {
        int confirm = JOptionPane.showOptionDialog(
                null, "Czy na pewno chcesz zamknąć aplikację?", "Potwierdź zamknięcie",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}