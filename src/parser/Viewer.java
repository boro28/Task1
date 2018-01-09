package parser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Viewer {
    String[] elements;
    Map<String, SubChapter> chaptersMap;
    List<String> fileContent;
    Objectification objectification;
    Map<String, Point> artsMap;
    static private String help = "Poprawna forma wejścia to: \"[ścieżka_pliku] [tryb_działania] [dokładna_nazwa_elementu]\"\n" +
            "np. \"C:\\Users\\Boro\\parser\\konstytucja.txt -t DZIAŁ II, Rozdział 3\"\n" +
            "    \"konstytucja.txt -t Art. 4., 2.\" \n" +
            "    5)\"uokik.txt -s \"\n" +
            "    6)\"konstytucja.txt -t DZIAŁ IV\"\n" +
            "    7)\"konstytucja.txt -z Art. 72., Art. 80.\" \n" +
            "Przykładowa nazwa specyficznego elementu: \"Art. 2 ust. 2. pkt 2) lit. a)\"\n" +
            "Podawane wyróżnione elementy powinny być oddzielane spacją i przecinkiem, jw.\n" +
            "Dostępne tryby działania: -t - treść, -s - spis treści. -z zakres artykułów";

    public Viewer(List<String> file, String mode, String[] parameters) {
        this.elements = parameters;
        this.fileContent = file;
        this.objectification = new Objectification(file);
        this.chaptersMap = objectification.returnChaptersMap();
        this.artsMap = objectification.returnArtsMap();
        try {
            if (mode.equals("-s")) {
                writeContents();
            } else if (mode.equals("-t")) {
                writeText();
            } else if (mode.equals("-z") && elements.length == 2) {
                wrieRange();
            } else {
                System.out.println("Błędnne argumenty");
                System.out.println(help);

            }


        } catch (NullPointerException ex) {
            System.out.println("Podany element nie istnieje.");
        }

    }

    public void writeContents() {
        if (elements[0].equals("") && elements.length == 1) {
            Set<String> keys = chaptersMap.keySet();
            for (String k : keys) {
                SubChapter subChapter = chaptersMap.get(k);
                subChapter.viewTitle();
            }
        } else if (this.elements.length < 2) {
            SubChapter chapter = chaptersMap.get(this.elements[0]);
            chapter.viewTitle();
        }

    }

    public void writeText() {
        if (chaptersMap.containsKey(elements[0])) {
            SubChapter sub;
            if (elements.length == 2) {
                Map<String, Chapter> chapMap = this.objectification.returnChaptersMap();
                Chapter chapter = chapMap.get(elements[0]);
                chaptersMap = chapter.returnSubChapters();
                sub = chaptersMap.get(elements[1]);
                sub.viewContent(this.fileContent);
            } else {
                sub = chaptersMap.get(elements[0]);
                sub.viewContent(this.fileContent);
            }
        } else if (artsMap.containsKey(elements[0])) {
            Point point = null;
            for (int i = 0; i < elements.length; i++) {
                point = artsMap.get(elements[i]);
                artsMap = point.returnPoints();
            }
            point.getContent();

        } else {
            System.out.println("Błędnie wprowadzone argumenty");
            System.out.println(help);
        }
    }

    public void wrieRange() {
        if (artsMap.containsKey(elements[0]) && artsMap.containsKey(elements[1])) {
            boolean correctOrder = true;
            Set<String> keys = artsMap.keySet();
            for (String k : keys) {
                if (k.equals(elements[0])) {
                    correctOrder = false;
                } else if (k.equals(elements[1])) {
                    correctOrder = true;
                }
            }
            if (correctOrder) {
                boolean write = false;
                for (String k : keys) {
                    if (k.equals(elements[0])) {
                        write = true;
                    }
                    if (write) {
                        Point point = artsMap.get(k);
                        point.getContent();
                    }
                    if (k.equals(elements[1])) {
                        write = false;
                    }
                }
            } else {
                System.out.println("Podaj argumenty od malejacego do rosnącego");
                System.out.println(help);
            }

        }
    }
}
