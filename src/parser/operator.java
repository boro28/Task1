package parser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class operator {
    public static void main(String[] args) {

        if (args.length >= 2) {
            List<String> fileContent = new Cleaner(new FileHandler(args[0]).returnReadFile()).returnStringList();
            String line = "";

            Objectification objectification = new Objectification(fileContent);
            Map<String, SubChapter> chaptersMap = objectification.returnChaptersMap();
            for (int i = 2; i < args.length; i++) {
                line = line + args[i] + " ";
            }
            if (line.length() > 0) {
                line = line.substring(0, line.length() - 1);
            }
            String[] elements = line.split(", ");
            Viewer viewer = new Viewer(fileContent, args[1], elements);


        } else {
            System.out.println("Za mała ilość argumentów");
        }


    }
}