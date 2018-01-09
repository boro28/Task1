package parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private List<String> fileContent;

    public FileHandler(String fileName) {

        String line;
        List<String> file = new ArrayList<>();

        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                file.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }

        this.fileContent = file;
    }

    public List returnReadFile() {
        return this.fileContent;
    }

}