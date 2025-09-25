package web;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class csvExporter {
    private PrintWriter writer;

   
    public void openFile(String fileName) throws IOException {
        writer = new PrintWriter(new FileWriter(fileName));
      
        writer.println("Course Name,Partner,Difficulty Level");
    }

    public void writeData(String courseName, String partnerName, String difficulty) {
        
        writer.printf("\"%s\",\"%s\",\"%s\"\n", courseName, partnerName, difficulty);
    }

    public void closeFile() {
        if (writer != null) {
            writer.close();
        }
    }
}