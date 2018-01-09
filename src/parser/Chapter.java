package parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Chapter extends SubChapter {
    private Map<String, SubChapter> subChapters;

    public Chapter(List<String> file, int startIndex) {
        super(file, startIndex);
        subChapters = new LinkedHashMap<>();
    }

    public void addSubChapter(String name, SubChapter subChapter) {
        this.subChapters.put(name, subChapter);

    }

    public void viewTitle() {
        super.viewTitle();
        Set<String> keys = this.subChapters.keySet();
        for (String k : keys) {
            SubChapter subChapter = this.subChapters.get(k);
            subChapter.viewTitle();
        }
    }

    public Map returnSubChapters() {
        return this.subChapters;
    }


}

