package parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Point {
    private List<String> content;
    private Map<String, Point> points;
    static private Pattern[] pattern = {
            Pattern.compile("^(DZIAŁ .*)|^(Rozdział .*)|^(Art\\. .*)"),
            Pattern.compile("^(\\d+\\w*\\.) .*"),
            Pattern.compile("^(\\d+\\w*\\)) .*"),
            Pattern.compile("^([a-z]\\) .*)"),
    };

    public Point(List<String> file, int fileIndex, int ratingIndex) {
        this.content = new ArrayList<>();
        this.points = new LinkedHashMap<>();
        String line = file.get(fileIndex);
        this.content.add(line);
        int matched = 3;
        boolean endFlag = false;
        Matcher matcher;

        for (int i = fileIndex + 1; i < file.size() && !endFlag; i++) {
            line = file.get(i);

            for (int j = 0; j <= matched && !endFlag; j++) {
                matcher = pattern[j].matcher(line);
                if (matcher.matches()) {
                    if (j > ratingIndex) {
                        matched = j;
                        if (matched == 3) {
                            this.points.put(line.split(" ")[0], new Point(file, i, j));
                        } else {
                            this.points.put(matcher.group(1), new Point(file, i, j));
                        }
                    } else {
                        endFlag = true;
                    }
                }
            }
            if (this.points.isEmpty() && !endFlag) {
                this.content.add(line);
            }
        }
    }

    public void getContent() {
        for (int i = 0; i < this.content.size(); i++) {
            System.out.println(this.content.get(i));
        }
        Set<String> keys = this.points.keySet();
        for (String k : keys) {
            Point point = this.points.get(k);
            point.getContent();
        }
    }

    public Map returnPoints() {
        return this.points;
    }
}
