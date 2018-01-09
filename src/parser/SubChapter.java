package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubChapter {
    int startIndex;
    int endIndex;
    private List<String> title;
    static private Pattern pattern = Pattern.compile("^(Rozdział .*)|^(Art\\. .*)");

    public SubChapter(List<String> file, int startIndex) {
        title = new ArrayList<>();
        this.startIndex = startIndex;
        title.add(file.get(startIndex));
        String line = file.get(startIndex + 1);
        Matcher matcher = pattern.matcher(line);

        for (int i = startIndex + 2; i < file.size() && !matcher.matches(); i++) {
            title.add(line);
            line = file.get(i);
            matcher = pattern.matcher(line);
        }
    }

    public void setEnd(int endIndex) {
        this.endIndex = endIndex;
    }

    public void viewTitle() {
        for (int i = 0; i < this.title.size(); i++) {
            System.out.println(this.title.get(i));
        }
    }

    public void viewContent(List<String> file) {
        for (int i = this.startIndex; i <= this.endIndex; i++) {
            System.out.println(file.get(i));
        }

    }

}
