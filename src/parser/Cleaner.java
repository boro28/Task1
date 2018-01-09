package parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cleaner {
    private List<String> cleanedFile;
    static private Pattern[] regexe = {
            Pattern.compile("^©.*"),
            Pattern.compile(".*(-$)"),
            Pattern.compile("^(Art\\. \\d+\\w*?\\.).+"),
    };


    public Cleaner(List<String> rawContent) {

        List<String> file = rawContent;
        String line;
        Matcher matcher;

        for (int i = 0; i < file.size(); i++) {
            line = file.get(i);

            matcher = regexe[0].matcher(line);
            if (matcher.matches()) {
                file.remove(i);
                file.remove(i);
                i--;
                continue;
            }

            matcher = regexe[1].matcher(line);
            if (matcher.matches()) {

                String[] wordSpliter = file.get(i + 1).split(" ", 2);
                String changer = line.replaceFirst("(-$)", wordSpliter[0]);
                file.set(i, changer);

                if (wordSpliter.length > 1) {
                    file.set(i + 1, wordSpliter[1]);
                } else {
                    file.remove(i + 1);
                }
            }

            matcher = regexe[2].matcher(line);
            if (matcher.matches()) {
                String[] artSpliter = line.split(" ", 3);
                file.set(i, matcher.group(1));
                file.add(i + 1, artSpliter[2]);
            }

        }

        this.cleanedFile = file;
    }

    public List returnStringList() {
        return this.cleanedFile;
    }
}