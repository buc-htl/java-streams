import java.util.List;
import java.util.Optional;

/**
 * In diesem Kapitel behandeln wir Java Optionals
 * @link https://docs.oracle.com/javase/9/docs/api/java/util/Optional.html
 */
public class Introduction_Optionals {


    public static void main(String[] args) {

        showTheProblem();
        whatAreOptionals();
        optionalsAndStreams();
    }

    private static void showTheProblem() {

        /* NullPointerException sind einer der häufigsten "Stolperfallen" in Java.

          Besonders bei verschachtelten Datenstrukturen kann man die Möglichkeit eines "null" Werts schnell übersehen.

          Im angeführten Beispiel möchte man den Namen des ersten Kindes ausgeben. Da für Hannes Haus aber keine Kinder
          angegeben wurden, erhält man eine NullPointerException
         */
        Person p1 = new Person("Hannes", "Haus");
        try {
            System.out.println(p1.getChildren().get(0));
        } catch (NullPointerException e) {
            System.out.println("Ups, NullPointerException.");
        }

        /* Der Erfinder der null Referenzen, Sir Tony Hoare, entschuldigte sich übrigens dafür.
           @link  https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions

           Ist nett, löst aber leider nicht unser Problem. Da es ein normaler Anwendungsfall ist, dass jemand
           keine Kinder hat, kommt es in diesen Fällen meist zu folgenden Konstrukten.
         */

        List<Person> children = p1.getChildren();
        if (children != null) {
            System.out.println("Die Kinder von "+p1+" sind: "+children.toString());
        } else {
            System.out.println(p1+" hat keine Kinder.");
        }

        /* Das ist relativ viel Code, um eine mögliche null-Referenz abzufangen. Bei mehrfach verschachtelten
        Datenstrukturen werden immer mehr if-Anweisungen notwendig.

        Um den Code zu vereinfachen wurden mit Java8 Optionals eingeführt.
         */
    }

    private static void whatAreOptionals() {

        /*
          Mit Java8 wurde die Klasse Optional eingeführt. Optionals kann man sich als Container vorstellen,
          der entweder einen Wert beinhaltet oder keinen ("leer" ist).
         */

        /*
        *  Die einfachste Möglichkeit einen Optional zu erzeugen ist mit Optional.of();
        */
        Optional<String> name = Optional.of("Semmel");

        /*
         * Wird bei Optional.of null übergeben, wird sofort eine NullPointerException geworfen.
         * Mittels Optional.ofNullable erzeugt null hingegen ein "leeres" Optional.
         */
        Optional<String> op1 = Optional.ofNullable("Kornspitz");
        Optional<String> op2 = Optional.ofNullable(null);

        /**
         * Schon am Datentyp erkennt man, dass evt. eine null-Referenz vorliegen kann.
         *
         * Die Klasse Optional stellt verschiedene Möglichkeiten zu Verfügung, um einen
         * vorhandenen Wert, bzw. dessen Abwesenheit zu behandeln.
         */
        if(op1.isPresent()){    //speichert op1 einen Wert?
            System.out.println("get(): "+op1.get());  //hole diesen Wert
        }

        /* Das liefert jetzt noch nicht viele Vorteile, aber Optionals können vieles mehr */
        System.out.print("ifPresent(): ");
        op1.ifPresent(System.out::println);     //lambda wird nur ausgeführt, wenn ein Wert vorhanden ist.

        System.out.println("orElse(): ");
        System.out.println(op1.orElse("Vollkornbrot")); //liefere einen Default-Wert, wenn der Optional leer ist
        System.out.println(op2.orElse("Vollkornbrot"));

        /*
          Neben der Klasse Optional für Objekte, stellt Java auch noch drei Klassen für die primitiven Typen int, long
          und double zu Verfügung.
          OptionalInt: @link https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/OptionalInt.html
          OptionalLong: @link https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/OptionalLong.html
          OptionalDouble: @link https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/OptionalDouble.html

          Optionals bieten noch einige weitere Möglichkeiten, auf die wir hier nicht weiter eingehen wollen.
          Siehe dazu:
          @link https://www.oracle.com/technical-resources/articles/java/java8-optional.html
          @link https://docs.oracle.com/javase/9/docs/api/java/util/Optional.html

          Beachte auch, dass Optional Methoden wie map, filter,...besitzen, die ähnlich zu den Methoden
          eines Streams sind.
         */
    }


    private static void optionalsAndStreams() {
        /**
         * Wir schauen uns jetzt Optionals an, weil wir sie in Zusammenhang mit Streams benötigen.
         *
         * Manche terminal operations liefern ein Optional. Schauen wir uns ein konkretes Beispiel an.
         * findFirst() (@link https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#findFirst--)
         *
         * findFirst() kann ein Objekt zurück liefern. Es kann aber auch kein passendes geben.
         */
        System.out.println("Optionals and Streams: ");
        List<Person> people = List.of(new Person("Susanne", "Sonnenschein"),
            new Person("Sandro", "Mondlicht"), new Person("Willibald", "Wunderlich"));

        Optional<Person> p = people.stream().filter(e -> e.getFirstName().startsWith("S")).findFirst();
        p.ifPresentOrElse(System.out::println, ()-> System.out.println("Keine Person gefunden"));

        Optional<Person> p2 = people.stream().filter(e -> e.getFirstName().startsWith("T")).findFirst();
        p2.ifPresentOrElse(System.out::println, ()-> System.out.println("Keine Person gefunden"));
    }


}
