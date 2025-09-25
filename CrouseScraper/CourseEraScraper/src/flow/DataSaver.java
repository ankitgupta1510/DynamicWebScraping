package flow;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class DataSaver {
    private PrintWriter writer;

    /**
     * File ko open karke header row likhta hai.
     * @param fileName The name of the file to create (e.g., "courses.csv").
     */
    public void openFile(String fileName) throws IOException {
        writer = new PrintWriter(new FileWriter(fileName));
        writer.println("Subject,Course Name,Partner,Difficulty Level");
    }

    /**
     * CSV file mein ek nayi row likhta hai.
     */
    public void writeData(String subject, String courseName, String partnerName, String difficulty) {
        // Commas se bachne ke liye har value ko quotes mein wrap karte hain.
        writer.printf("\"%s\",\"%s\",\"%s\",\"%s\"\n", subject, courseName, partnerName, difficulty);
    }

    /**
     * File ko save karke close karta hai.
     */
    public void closeFile() {
        if (writer != null) {
            writer.close();
        }
    }
}