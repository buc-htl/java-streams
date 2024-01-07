import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Streams vereinfachen das Arbeiten mit Collections und Arrays. Anstatt mit Hilfe
 * von Schleifen und Bedingungen einen Lösungs-Algorithmus umzusetzen, können Streams
 * einen einfacheren Lösungsweg darstellen.
 *
 * Streams stellen Ströme von Referenzen dar, die es erlauben, verkettete Operationen auf diesen
 * Referenzen nacheinander oder parallel auszuführen.
 *
 * Streams speichern keine Daten, verändert NICHT die Datenquelle und werden erst ausgeführt, wenn notwendig
 * (d.h. wenn das Ergebnis der verketteten Operationen benötigt werden).
 *
 * Beachte, dass hier mit "Streams" etwas anderes gemeint ist als mit I/O-Streams (z.B. FileInputStream).
 */
public class Introduction_Streams {

    public static void main(String[] args) {

        /* schauen wir uns ein erstes Beispiel an */
        firstExample();

        /* Bist du jetzt begeistert, wie kompakt man mit Streams manche Probleme lösen kann?
        * Schauen wir uns Streams etwas genauer an!
        *
        *   Wir unterscheiden 3 Schritte in der Arbeit mit Streams:
        *   Stream erzeugen -> intermediate operation(s) -> terminal operation
        *
        *   Intermediate Operations bearbeiten die Elemente des Streams und liefern selbst einen Stream
        *   zurück. Beliebig viele intermediate Operations können aneinander gereiht werden.
        *
        *   Terminal Operations lösen die Verarbeitung des Streams aus und beenden den Stream. Sie können
        *   (müssen aber nicht) das Ergebnis des Streams generieren.
        *
        *   Schauen wir uns ein weiteres konkretes Beispiel an.
         */
        secondExample();

        /*
         * ...und noch ein drittes Beispiel
         */
        thirdExample();

        /**
         * Für mehr Informationen zu den einzelnen Operationen/Methoden beachte die Lehrunterlagen und die API Dokumentation.
         *
         * Stream Interface: @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
         * IntStream Interface:  @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html
         * LongStream Interface: @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/LongStream.html
         * DoubleStream Interface @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/DoubleStream.html
         */

    }


    public static void firstExample() {
        System.out.println("first example:");
        //Ermittle wie viele Zahlen im Array `a` kleiner als `10` sind
        int[] a = {1, 2, 5, 12, 9, 11, 6, 88};

        // klassisch:
        int counter = 0;
        for (int n: a) {
            if (n < 10) {
                counter++;
            }
        }
        System.out.printf("%d kleine Elemente\n", counter);     // Ausgabe: "5 kleine Elemente"

        // kürzer mit Streams (filtere alle Elemente < 10 und zähle sie)
        System.out.printf("%d kleine Elemente\n",
            Arrays.stream(a).filter(n -> n < 10).count()); // Ausgabe: "5 kleine Elemente"
    }


    public static void secondExample() {

        //Ermittle die Summe der drei größten durch Drei und durch Sieben teilbaren Zahlen
        //der Liste numbers
        // durch 3 und durch 7 teilbaren Zahlen sind: 21 (2x), 63 (3x), 147 (1x), 189 (1x),
        // die Summe der drei größten Zahlen davon ist: 189 + 147 + 63 = 399
        List<Integer> numbers = Arrays.asList(63, 63, 21, 21, 3, 7, 189, 14, 63, 147, 6, 99);


        int sum = numbers
            .stream()                       // Erzeugen des Streams aus der Liste
            .filter(n->n%3==0 && n%7==0)    // intermediate operation: filtert die Elemente
            .sorted((a,b) -> Integer.compare(b,a))  //intermediate operation: sortiert verkehrt
            .limit(3)                               //intermediate operation: reduziere auf 3 Elemente
            .mapToInt(i->i.intValue())              //intermediate operation: Wandle Integer in int um
            .sum();                                 //terminal operation: summiere alle Elemente

         /*
           Anmerkung:
           Das Interface Stream definiert einen Stream von Objekten.
           In diesem Beispiel ein Stream von Integer Objekten.

           Dieses Interface besitzt aber keine sum() Methode. Für die primitiven Datentypen int, long und double
           existieren entsprechende Streams: IntStream, LongStream und DoubleStream.
           Diese besitzen eine sum() Methode.

           Die Methode mapToInt wandelt einen Stream in einen IntStream um. Der Lambda-Ausdruck gibt an,
           wie dies Umwandlung erfolgen soll.
         */

        System.out.println("second example: " +sum);
    }

    private static void thirdExample() {
        List<Person> people = List.of(new Person("Susanne", "Sonnenschein"), new Person("Manuel", "Mondlicht"), new Person("Willibald", "Wunderlich"));

        List<String> firstNames= people.stream().map(e -> e.getFirstName()).collect(Collectors.toList());

        System.out.println("third example: "+firstNames);
    }

}
