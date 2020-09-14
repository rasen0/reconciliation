package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class FileTool {

    public<T> void writeIntoFile(String filePath, LinkedList<T> list) throws IOException {
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (T t: list) {
            bw.write(t.toString()+"\n");
        }
        bw.close();
    }
}
