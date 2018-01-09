package parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Objectification {
    private Map<String, Point> arts;
    private Map<String, SubChapter> chapters;

    static private Pattern[] regexe = {
            Pattern.compile("^(DZIAŁ .*)"),
            Pattern.compile("^(Rozdział .*)"),
            Pattern.compile("^(Art\\. .*)"),
    };


    public Objectification(List<String> file) {
        arts = new LinkedHashMap<>();
        chapters = new LinkedHashMap<>();
        boolean haveSubChapters = false;
        boolean addedSubChapters = false;
        String line;
        Matcher match;
        String lastMatchedChapter = null;
        String lastMatchedSubChapter = null;
        Chapter chapter = null;
        SubChapter subChapter = null;

        for (int i = 0; i < file.size(); i++) {
            line = file.get(i);

            match = regexe[0].matcher(line);
            if (match.matches()) {
                if (haveSubChapters) {
                    chapter.setEnd(i - 1);
                    if (addedSubChapters) {
                        subChapter.setEnd(i - 1);
                        chapter.addSubChapter(lastMatchedSubChapter, subChapter);
                    }
                    this.chapters.put(lastMatchedChapter, chapter);
                } else {
                    haveSubChapters = true;
                }
                chapter = new Chapter(file, i);
                addedSubChapters = false;
                lastMatchedChapter = match.group();
                continue;
            }

            match = regexe[1].matcher(line);
            if (match.matches()) {
                if (addedSubChapters) {
                    subChapter.setEnd(i - 1);
                    if (haveSubChapters) {
                        chapter.addSubChapter(lastMatchedSubChapter, subChapter);
                    } else {
                        this.chapters.put(lastMatchedSubChapter, subChapter);
                    }
                } else {
                    addedSubChapters = true;
                }
                subChapter = new SubChapter(file, i);
                lastMatchedSubChapter = match.group();
                continue;
            }

            match = regexe[2].matcher(line);
            if (match.matches()) {
                this.arts.put(match.group(1), new Point(file, i, 0));
            }


        }
        if (!chapters.isEmpty()) {
            subChapter.setEnd(file.size());
            chapter.setEnd(file.size());
        }

        if (haveSubChapters) {
            this.chapters.put(lastMatchedChapter, chapter);
        } else {
            this.chapters.put(lastMatchedSubChapter, subChapter);
        }

    }

    public Map returnArtsMap() {
        return this.arts;
    }

    public Map returnChaptersMap() {
        return this.chapters;
    }
}


