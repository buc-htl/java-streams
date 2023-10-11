import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Musterbeispiele für Collectors-Objekte für die collect-Methode von Streams
 *
 * @author DI Franz Breunig, März 2016 (inspiriert von Dr. Ferdinand Kasper)
 */
public class ExamplesCollectors {
    public static void main(String[] args) throws IOException {
        List<String> words =
                Arrays.stream(("Im Anfang schuf Gott Himmel und Erde; die Erde aber war wüst und " +
                        "wirr, Finsternis lag über der Urflut und Gottes Geist schwebte über dem " +
                        "Wasser. Gott sprach: Es werde Licht. Und es wurde Licht. Gott sah, dass " +
                        "das Licht gut war. Gott schied das Licht von der Finsternis und Gott " +
                        "nannte das Licht Tag und die Finsternis nannte er Nacht. Es wurde Abend " +
                        "und es wurde Morgen: erster Tag. Dann sprach Gott: Ein Gewölbe entstehe " +
                        "mitten im Wasser und scheide Wasser von Wasser. Gott machte also das " +
                        "Gewölbe und schied das Wasser unterhalb des Gewölbes vom Wasser oberhalb " +
                        "des Gewölbes. So geschah es und Gott nannte das Gewölbe Himmel. Es wurde " +
                        "Abend und es wurde Morgen: zweiter Tag. Dann sprach Gott: Das Wasser " +
                        "unterhalb des Himmels sammle sich an einem Ort, damit das Trockene " +
                        "sichtbar werde. So geschah es. Das Trockene nannte Gott Land und das " +
                        "angesammelte Wasser nannte er Meer. Gott sah, dass es gut war. Dann " +
                        "sprach Gott: Das Land lasse junges Grün wachsen, alle Arten von Pflanzen, " +
                        "die Samen tragen, und von Bäumen, die auf der Erde Früchte bringen mit " +
                        "ihrem Samen darin. So geschah es. Das Land brachte junges Grün hervor, " +
                        "alle Arten von Pflanzen, die Samen tragen, alle Arten von Bäumen, die " +
                        "Früchte bringen mit ihrem Samen darin. Gott sah, dass es gut war. Es wurde " +
                        "Abend und es wurde Morgen: dritter Tag. Dann sprach Gott: Lichter sollen " +
                        "am Himmelsgewölbe sein, um Tag und Nacht zu scheiden. Sie sollen Zeichen " +
                        "sein und zur Bestimmung von Festzeiten, von Tagen und Jahren dienen; sie " +
                        "sollen Lichter am Himmelsgewölbe sein, die über die Erde hin leuchten. So " +
                        "geschah es. Gott machte die beiden großen Lichter, das größere, das über " +
                        "den Tag herrscht, das kleinere, das über die Nacht herrscht, auch die " +
                        "Sterne. Gott setzte die Lichter an das Himmelsgewölbe, damit sie über die " +
                        "Erde hin leuchten, über Tag und Nacht herrschen und das Licht von der " +
                        "Finsternis scheiden. Gott sah, dass es gut war. Es wurde Abend und es " +
                        "wurde Morgen: vierter Tag. Dann sprach Gott: Das Wasser wimmle von " +
                        "lebendigen Wesen und Vögel sollen über dem Land am Himmelsgewölbe " +
                        "dahinfliegen. Gott schuf alle Arten von großen Seetieren und anderen " +
                        "Lebewesen, von denen das Wasser wimmelt, und alle Arten von gefiederten " +
                        "Vögeln. Gott sah, dass es gut war. Gott segnete sie und sprach: Seid " +
                        "fruchtbar und vermehrt euch und bevölkert das Wasser im Meer und die " +
                        "Vögel sollen sich auf dem Land vermehren. Es wurde Abend und es wurde " +
                        "Morgen: fünfter Tag. Dann sprach Gott: Das Land bringe alle Arten von " +
                        "lebendigen Wesen hervor, von Vieh, von Kriechtieren und von Tieren des " +
                        "Feldes. So geschah es. Gott machte alle Arten von Tieren des Feldes, " +
                        "alle Arten von Vieh und alle Arten von Kriechtieren auf dem Erdboden. " +
                        "Gott sah, dass es gut war. Dann sprach Gott: Lasst uns Menschen machen " +
                        "als unser Abbild, uns ähnlich. Sie sollen herrschen über die Fische des " +
                        "Meeres, über die Vögel des Himmels, über das Vieh, über die ganze Erde " +
                        "und über alle Kriechtiere auf dem Land. Gott schuf also den Menschen als " +
                        "sein Abbild; als Abbild Gottes schuf er ihn. Als Mann und Frau schuf er " +
                        "sie. Gott segnete sie und Gott sprach zu ihnen: Seid fruchtbar und " +
                        "vermehrt euch, bevölkert die Erde, unterwerft sie euch und herrscht über " +
                        "die Fische des Meeres, über die Vögel des Himmels und über alle Tiere, " +
                        "die sich auf dem Land regen. Dann sprach Gott: Hiermit übergebe ich euch " +
                        "alle Pflanzen auf der ganzen Erde, die Samen tragen, und alle Bäume mit " +
                        "samenhaltigen Früchten. Euch sollen sie zur Nahrung dienen. Allen Tieren " +
                        "des Feldes, allen Vögeln des Himmels und allem, was sich auf der Erde " +
                        "regt, was Lebensatem in sich hat, gebe ich alle grünen Pflanzen zur " +
                        "Nahrung. So geschah es. Gott sah alles an, was er gemacht hatte: Es war " +
                        "sehr gut. Es wurde Abend und es wurde Morgen: der sechste Tag."
                ).split("[\\P{Alpha}]+")).collect(Collectors.toList());

        // simple-joined: mit Trennelement
        System.out.println("just joined: " + words.stream().collect(Collectors.joining(", ")));

        // complex-joined - als String-Array-Darstellung
        String complexJoind = words.stream()
                // Elemente zu einem String verketten
                .collect(Collectors.joining(
                        "\", \"",                // Trennelement:  ", "
                        "new String[] { \"",     // Prefix;        new String {"
                        "\" }"                   // Sufix:         "}
                ));
        System.out.printf("Worte als Array-Literal: %s%n%n", complexJoind);

        // toList
        List<String> wordList = words.stream().collect(Collectors.toList());
        System.out.printf("WortListe: %s%n%n", wordList);


        // Statistik: Wortlänge
        IntSummaryStatistics stat = words.stream()
                .collect(Collectors.summarizingInt(w -> w.length()));
        System.out.println(stat);
        System.out.printf(
                "wordlen statistic: min=%d, max=%d, mid=%f%n%n" ,
                stat.getMin(), stat.getMax(), stat.getAverage());

        // Partitionieren: shortWords haben maximal fünf Zeichen
        Map<Boolean, List<String>> shortWords = words.stream()
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.partitioningBy(w -> w.length() <= 5));
        System.out.printf("short words: %s%n",   shortWords.get(true));
        System.out.printf("long  words: %s%n%n", shortWords.get(false));

        // Gruppieren: nach dem Anfangsbuchstaben
        Map<String, List<String>> firstLetter = words.stream()
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.groupingBy(word -> word.substring(0,1).toLowerCase()));
        System.out.printf("grouped by first Letter: %s%n%n", firstLetter);

        // Gruppieren (& Reduzieren): nach der Worthäufigkeit mit Supplier und Downstream-Collector
        Map<String, Long> wordFrequency = words.stream()
                .map(String::toLowerCase)
                .sorted()
                .collect(
                        Collectors.groupingBy(
                                word -> word,          // key der Map (= bei uns das Wort selbst)
                                TreeMap::new,          // mapFactory (= Map-Art zum Speichern)
                                Collectors.counting()  // value der Map (= Worthäufigkeit)
                        ));
        System.out.printf("Worthäufigkeit: %s%n", wordFrequency);
    }
}
