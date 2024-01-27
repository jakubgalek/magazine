import java.util.Objects;

public class CData {
    private int dzien;
    private int miesiac;
    private int rok;
    private int dni;

    private static final String[] nazwyDni = {
            "Poniedziałek", "Wtorek", "Środa",
            "Czwartek", "Piątek", "Sobota", "Niedziela"
    };

    //Metody

    // Metoda prywatna do rzucania wyjątku dla nieprawidłowej daty
    private static void rzucWyjatek(String komunikat) throws IllegalArgumentException {
        throw new IllegalArgumentException(komunikat);
    }
    public CData() {
        this.dni = 1;  //początek kalendarza gregoriańskiego
        this.ustawDate(1, 1, 1583);
    }

    public CData(int dzien, int miesiac, int rok) {
        this.ustawDate(dzien, miesiac, rok);
    }

    public CData(String s_data) {
        try {
            String[] parts = s_data.split("\\.");

            if (parts.length != 3) {
                rzucWyjatek("Nieprawidłowy format daty: " + s_data);
            }

            int dzien = Integer.parseInt(parts[0]);
            int miesiac = Integer.parseInt(parts[1]);
            int rok = Integer.parseInt(parts[2]);
            this.ustawDate(dzien, miesiac, rok);

        } catch (NumberFormatException e) {
            rzucWyjatek("Błąd podczas parsowania liczby w formacie daty: " + s_data);
        } catch (ArrayIndexOutOfBoundsException e) {
            rzucWyjatek("Nieprawidłowy format daty: " + s_data);
        } catch (IllegalArgumentException e) {
            rzucWyjatek("Nieprawidłowa data: " + s_data);
        }
    }

    public int getDzien() {
        // Pobiera dzień z obiektu daty
        return this.dzien;
    }

    public int getMiesiac() {
        // Pobiera miesiąc z obiektu daty
        return this.miesiac;
    }

    public int getRok() {
        // Pobiera rok z obiektu daty
        return this.rok;
    }

    public void setDzien(int dzien) {
        if (dzien < 1 || dzien > 31) {
            rzucWyjatek("Nieprawidłowy dzień: " + dzien);
        }
        // Ustawia dzień w obiekcie daty i aktualizuje datę
        this.dzien = dzien;
        this.ustawDate(this.dzien, this.miesiac, this.rok);
    }

    public void setMiesiac(int miesiac) {
        if (miesiac < 1 || miesiac > 12) {
            rzucWyjatek("Nieprawidłowy miesiąc: " + miesiac);
        }
        // Ustawia miesiąc w obiekcie daty i aktualizuje datę
        this.miesiac = miesiac;
        this.ustawDate(this.dzien, this.miesiac, this.rok);
    }

    public void setRok(int rok) {
        if (rok < 1583) {
            rzucWyjatek("Nieprawidłowy rok: " + rok);
        }
        // Ustawia rok w obiekcie daty i aktualizuje datę
        this.rok = rok;
        this.ustawDate(this.dzien, this.miesiac, this.rok);
    }



    public int getDni() {
        return this.dni;
    }

    private void ustawDate(int dzien, int miesiac, int rok) {
        int[] dniWMiesiacach = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (rok % 4 == 0 && (rok % 100 != 0 || rok % 400 == 0)) {
            dniWMiesiacach[2] = 29; // Rok przestępny, luty ma 29 dni.
        }

        if (miesiac < 1 || miesiac > 12 || dzien < 1 || dzien > dniWMiesiacach[miesiac]) {
            throw new IllegalArgumentException("Nieprawidłowa data: " + dzien + "." + miesiac + "." + rok);
        }

        this.dzien = dzien;
        this.miesiac = miesiac;
        this.rok = rok;

        this.dni = dzien;

        for (int i = 1; i < miesiac; i++) {
            this.dni += dniWMiesiacach[i];
        }

        if (miesiac > 2 && dniWMiesiacach[2] == 29) {
            this.dni += 1;
            // Dodaje jeden dzień dla roku przestępnego, jeśli data jest po lutym w roku przestępnym.
        }

        for (int i = 1; i < rok; i++) {
            if (i % 4 == 0 && (i % 100 != 0 || i % 400 == 0)) {
                this.dni += 366; // Rok przestępny.
            } else {
                this.dni += 365; // Rok zwykły.
            }
        }
    }

    private static final int[] dniWMiesiacach = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public void do_przyszlosci(int dni) {
        if (dni < 0) {
            rzucWyjatek("Liczba dni nie może być ujemna: " + dni);
        }
        this.dni += dni;
        int poprzedniRok = this.rok;
        int poprzedniMiesiac = this.miesiac;
        int poprzedniDzien = this.dzien;

        for (int i = 0; i < dni; i++) {
            poprzedniDzien++;
            if (poprzedniDzien > dniWMiesiacach[poprzedniMiesiac]) {
                poprzedniMiesiac++;
                if (poprzedniMiesiac > 12) {
                    poprzedniRok++;
                    poprzedniMiesiac = 1;
                }
                poprzedniDzien = 1;
            }
        }

        this.rok = poprzedniRok;
        this.miesiac = poprzedniMiesiac;
        this.dzien = poprzedniDzien;
        this.ustawDate(this.dzien, this.miesiac, this.rok);
    }


    public void w_przeszlosc(int dni) {
        if (dni < 0) {
            rzucWyjatek("Liczba dni nie może być ujemna: " + dni);
        }
        int poprzedniRok = this.rok;
        int poprzedniMiesiac = this.miesiac;
        int poprzedniDzien = this.dzien;

        for (int i = 0; i < dni; i++) {
            poprzedniDzien--;
            if (poprzedniDzien < 1) {
                poprzedniMiesiac--;
                if (poprzedniMiesiac < 1) {
                    poprzedniRok--;
                    poprzedniMiesiac = 12;
                }
                poprzedniDzien = dniWMiesiacach[poprzedniMiesiac];
            }
        }

        this.rok = poprzedniRok;
        this.miesiac = poprzedniMiesiac;
        this.dzien = poprzedniDzien;
        this.ustawDate(this.dzien, this.miesiac, this.rok);
    }


    public void jutro() {
        do_przyszlosci(1);
    }

    public void wczoraj() {
        w_przeszlosc(1);
    }

    public void zaTydzen() {
        do_przyszlosci(7);
    }

    public void tydzenTemu() {
        w_przeszlosc(7);
    }

    public boolean porownajDaty(CData x) {
        if (x == null) {
            rzucWyjatek("Porównywany obiekt nie może być null.");
        }
        return this.dni == x.getDni();
    }

    public int roznicaDat(CData x) {
        if (x == null) {
            rzucWyjatek("Porównywany obiekt nie może być null.");
        }
        return Math.abs(x.getDni() - this.dni);
    }

    private String formatujDate(int dzien, int miesiac, int rok) {
        int[] dniWMiesiacach = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (dzien > dniWMiesiacach[miesiac]) {
            dzien -= dniWMiesiacach[miesiac];
            miesiac++;
            if (miesiac > 12) {
                miesiac = 1;
                rok++;
            }
        }
        return String.format("%02d.%02d.%04d", dzien, miesiac, rok);
    }

    @Override
    public String toString() {
        return formatujDate(this.dzien, this.miesiac, this.rok);
    }

    public String dzienTygodnia() {
        int dayOfWeek = obliczDzienTygodnia();
        return nazwyDni[dayOfWeek];
    }

    private int obliczDzienTygodnia() {
        int dzien = this.getDzien();
        int miesiac = this.getMiesiac();
        int rok = this.getRok();

        if (miesiac < 3) {
            miesiac += 12;
            rok -= 1;
        }

        int k = rok % 100;
        int j = rok / 100;

        int dayOfWeek = (dzien + (13 * (miesiac + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;

        int dzienTygodnia = (dayOfWeek + 5) % 7;

        return (dzienTygodnia + 6) % 7 + 1;
    }

    //Metody statyczne
    public static boolean porownajDaty(CData x1, CData x2) {
        if (x1 == null || x2 == null) {
            rzucWyjatek("Porównywane obiekty nie mogą być null.");
        }

        try {
            return x1.getDni() == x2.getDni();
        } catch (ArithmeticException e) {
            rzucWyjatek("Błąd w obliczeniach daty przy porównywaniu.");
        }

        return false;
    }

    public static int roznicaDat(CData x1, CData x2) {
        if (x1 == null || x2 == null) {
            rzucWyjatek("Porównywane obiekty nie mogą być null.");
        }

        try {
            return Math.abs(x2.getDni() - x1.getDni());
        } catch (ArithmeticException e) {
            rzucWyjatek("Błąd w obliczeniach daty przy różnicy dat.");
        }

        return -1;
    }


    public static int[] dataWielkanocy(int rok) {
        int a = rok % 19;
        int b = rok / 100;
        int c = rok % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;

        int miesiac = (h + l - 7 * m + 114) / 31;
        int dzien = ((h + l - 7 * m + 114) % 31) + 1;

        return new int[] {dzien, miesiac, rok};
    }

    public static String dzienTygodnia(CData x) {
        if (x == null) {
            rzucWyjatek("Podany obiekt nie może być null.");
        }

        try {
            int dayOfWeek = x.obliczDzienTygodnia();
            return nazwyDni[dayOfWeek];
        } catch (ArrayIndexOutOfBoundsException e) {
            rzucWyjatek("Błąd obliczania dnia tygodnia.");
        }
        return null; // Jeśli wystąpiłby wyjątek, zwracamy null
    }


@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CData cData = (CData) o;
    return dzien == cData.dzien &&
            miesiac == cData.miesiac &&
            rok == cData.rok;
}

@Override
public int hashCode() {
    return Objects.hash(dzien, miesiac, rok);
}

}