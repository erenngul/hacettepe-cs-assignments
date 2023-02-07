import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class consists of static method to read the properties.dat file before launching the application.
 */
public class PropertiesFile {

    private static Properties propertiesTable = new Properties();
    private static String propertiesPath = "assets\\data\\properties.dat";

    /**
     * Reads properties.dat file and loads it to static variable propertiesTable to be later used in application.
     */
    public static void loadPropertiesFile() {
        try {
            FileInputStream input = new FileInputStream(propertiesPath);
            propertiesTable.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return propertiesTable which is loaded from properties.dat file.
     */
    public static Properties getPropertiesTable() {
        return propertiesTable;
    }

}
