import javafx.application.Application;

/**
 * Main class for HUCS Cinema Reservation System.
 */
public class Main {

    /**
     * Main method of the HUCS Cinema Reservation System. Reads properties file and backup file to use the data in
     * application. After data reading, LoginWindow is opened.
     *
     * @param args The command line arguments, but they aren't used for this assignment
     */
    public static void main(String[] args) {
        PropertiesFile.loadPropertiesFile();
        BackupFile.readBackupFile();
        Application.launch(LoginWindow.class, args);
    }

}