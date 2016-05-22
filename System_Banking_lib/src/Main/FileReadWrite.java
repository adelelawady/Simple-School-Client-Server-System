package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReadWrite {

    public FileReadWrite() {
    }

    public String readFile(String filename) {
        String content = null;
        File file = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
        } finally {
        }
        return content;
    }

    public void FileWriter(String WritePath, String Content, boolean CanAppand) {
        try {
            File file = new File(WritePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (CanAppand) {
                Files.write(file.toPath(), Content.getBytes(), StandardOpenOption.APPEND);
            } else {
                Files.write(file.toPath(), Content.getBytes(), StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            System.out.println("Done");
        }
    }
}
