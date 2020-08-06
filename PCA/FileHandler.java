import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public List<String[]> readCSV(String csvPath) throws IOException {
        List<String[]> data = new ArrayList<>();
        File csvFile = new File(csvPath);
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
            String row = "";
            while ((row = csvReader.readLine()) != null) {
                String[] rowItems = row.split(",");
                data.add(rowItems);
            }
        }
        return data;
    }
}
