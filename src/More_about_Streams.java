import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class More_about_Streams {

    public static void main(String[] args) {

        streamTypes();
        stringsAndStreams();
        mapsAndStreams();
        grouping();
        tryYourself();
    }


    private static void streamTypes() {
        /*
            Es gibt 4 Arten von Streams:

         * Stream von Objekten -> Stream Interface: @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
         * Stream von int Elementen -> IntStream Interface:  @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html
         * Stream von long Elementen -> LongStream Interface: @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/LongStream.html
         * Stream von double Elementen -> DoubleStream Interface @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/DoubleStream.html
         */

         int[] numbers = {1,2,3,4,5};

        IntStream intStream = Arrays.stream(numbers);     //ein Stream von ints

        Stream<Integer> stream = intStream.mapToObj(Integer::valueOf);   //mapToObj() wandelt den IntStream in einen Stream von Integer Objekten um

        intStream = Arrays.stream(numbers);
        stream = intStream.boxed();    //für eine simple Umwandlung von einem Stream primitiver Typen in einen Objekt-Stream gibt es auch die Methode boxed()

        intStream = stream.mapToInt(Integer::intValue);     //mapToInt() wandelt den Stream in einen IntStream um

        //gleiches Prinzip für die anderen Stream-Typen....
        //jedes Interface besitzt leicht unterschiedliche Methoden. Stream besitzt z.B. kein sum(), IntStream, LongStream,... schon.

        /*
            Analog gibt es auch 4 Arten von Optionals:

          Optional: @link https://docs.oracle.com/javase/9/docs/api/java/util/Optional.html
          OptionalInt: @link https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/OptionalInt.html
          OptionalLong: @link https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/OptionalLong.html
          OptionalDouble: @link https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/OptionalDouble.html
         */

        intStream = Arrays.stream(numbers);
        stream = Arrays.stream(numbers).boxed();

        OptionalInt firstInt = intStream.findFirst();   //findFirst() auf einen IntStream liefert ein OptionalInt
        Optional<Integer> first = stream.findFirst();   //findFirst() auf einen Stream liefert ein Optional<Integer>
    }

    private static void stringsAndStreams() {

        String str = "Semmel";

        //chars() wandelt einen String in einen stream von "characters" um. Da es aber keinen char Stream gibt, ist das Ergebnis von
        // chars() ein IntStream.
        int[] characters = str.chars().toArray();

        //Diesen IntStream können wir auch wieder in einen String Stream umwandeln (int -> Character -> String)
        String letters = str.chars().mapToObj(c -> String.valueOf(Character.toChars(c))).collect(Collectors.joining(", "));
        System.out.println("Buchstaben: "+letters);

    }

    private static void mapsAndStreams() {

        /* Map --> Stream */

        Map<String, Integer> votes = Map.of("Party A", 2010, "Party B", 576, "Party C", 1230);

        //stream der keys
        List<String> parties = votes.keySet().stream().filter(k -> votes.get(k)>1000).collect(Collectors.toList());
        System.out.println("Parties wit > 1000 votes: "+parties);

        //stream der values
        int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total votes: "+totalVotes);

        //stream von Map.Entry
        //ein Map.Entry fasst immer einen Key und einen Value in einem Objekt zusammen
        //@link https://docs.oracle.com/javase/8/docs/api/java/util/Map.Entry.html
        int v = votes.entrySet().stream().filter(e -> e.getKey().contains("C")).map(e->e.getValue()).findFirst().get();
        System.out.println("Votes of the first party containing a C: "+v);

        /* Stream --> Map */

       //Collectors.toMap() erzeugt aus den Elementen eines Streams eine Map.
        // Die Methode hat (grundsätzlich) zwei Argumente:
        // - eine Funktion, die den Key zurückgibt
        // - und eine Funktion für den zugehörigen Wert (value).
        //
        //Im folgenden Beispiel ist der Schlüssel die Länge des Strings, der Wert der String selbst.
        Map<Integer, String> map = Stream.of("Karli", "Susi", "Ottokar")
            .collect( Collectors.toMap(e -> e.length(), e->e));
        System.out.println("String to map v1: "+map);

        //Es führt allerdings zu einer Exception, sollten zwei Strings die gleiche Länge haben und damit den gleichen Key.
        try {
            Map<Integer, String> map2 = Stream.of("Karli", "Franz", "Ottokar")
                .collect( Collectors.toMap(e -> e.length(), e->e));
        } catch (IllegalStateException e) {
            System.out.println("Fehler!!: "+e.getLocalizedMessage());
        }

        //Daher kann man Collectors.toMap() ein drittes Argument mitgeben -
        // eine „Merger“-Funktion, die ausgeführt wird, wenn zwei Values dem gleichen Key zugeordnet werden sollen.
        map = Stream.of("Karli", "Franz","Ottokar")
            .collect(Collectors.toMap(x->x.length(),x->x,
                (oldValue, newValue)-> oldValue+", "+newValue));
        System.out.println("String to map v2: "+map);
    }

    private static void grouping() {

        //Mit groupingBy() lassen sich alle Elemente eines Streams in Gruppen zusammenfassen.

        List<Person> people = List.of(new Person("Susanne", "Sonnenschein"), new Person("Manuel", "Sonnenschein"), new Person("Willibald", "Wunderlich"));

        //fassen wir alle Personen mit dem gleichen Nachnamen in Gruppen zusammen.
        // Der Parameter von groupingBy() nennt sich "classifier function".
        Map<String, List<Person>> sameLastName = people.stream().collect(Collectors.groupingBy(Person::getLastName));
        System.out.println("Gruppen mit gleichen Nachnamen: "+sameLastName);

        //groupingBy() kann auch alle Elemente einer Gruppe auf einen einzelnen Wert reduzieren.
        // Sprich, der value der Map ist keine Liste mehr, sondern ein einzelner Wert.
        //Neben der "classifier function" benötigt groupingBy() als zweiten Parameter eine "downstream collector function",
        // die angibt wie dieser einzelne Wert für die Gruppe erzeugt werden soll.
        Map<String, Long> countSameLastName =  people.stream().collect(Collectors.groupingBy(Person::getLastName, Collectors.counting()));
        System.out.println("Wie oft kommt welcher Nachname vor: "+countSameLastName);

        //für den "downstream collector" verwenden wir, wenn immer möglich, einen der bereits in der Klasse Collectors
        // zu Verfügung gestellten @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
        //Es ist natürlich auch möglich eigene Collectors zu schreiben.
        // Dieses Thema wird hier aber nicht weiter behandelt
        // (siehe z.B. @link https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html)
    }

    private static void tryYourself() {

        //Aufgabe 1: Berechne die durchschnittliche Größe aller Personen mit gleichem Nachnamen
        List<Person> people = List.of(
            new Person("Susanne", "Sonnenschein", 185),
            new Person("Manuel", "Sonnenschein", 182),
            new Person("Peter", "Sonnenschein", 130),
            new Person("Willibald", "Wunderlich", 174),
            new Person("Hannes", "Hohensang", 174),
            new Person("Hubert", "Hohensang", 164)
    );

        System.out.println(people.stream().collect(Collectors.groupingBy(Person::getLastName,Collectors.averagingDouble(Person::getHeight))));

        //Aufgabe 2: Ersetze im folgenden String jedes 'a' durch ein 'e' mit Hilfe von Streams
        //und ja, ein replaceAll() wäre die einfachere Lösung ;)
        String s ="Wissan und Erkannan sind dia Frauda und dia Barachtigung dar Manschhait";

        System.out.println(
            s.chars()
                .map(c -> c == 'a'? 'e': c)
                .mapToObj(c -> String.valueOf(Character.toChars(c))).collect(Collectors.joining())
        );

        //alternative Lösung um char-Stream in einen String zurück zu wandeln
        System.out.println(
            s.chars()
                .map(c -> c == 'a'? 'e': c)
                .mapToObj(c -> ""+(char)c).collect(Collectors.joining())
        );




    }

}
