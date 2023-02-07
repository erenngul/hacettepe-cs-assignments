import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.HashMap;

/**
 * Add film window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class AddFilmWindow extends Application {

    private TextField nameField = new TextField();
    private TextField trailerField = new TextField();
    private TextField durationField = new TextField();
    private Label errorLabel = new Label("");

    /**
     * Creates add film window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getAddFilmWindow(Stage primaryStage) {
        Label messageLabel = new Label("Please give name, relative path of the trailer and duration of the film.");

        Label nameLabel = new Label("Name:");
        Label trailerLabel = new Label("Trailer (Path):");
        Label durationLabel = new Label("Duration (m):");
        Button backButton = new Button("⯇ BACK");
        Button okButton = new Button("OK");

        GridPane gridPane = new GridPane();
        gridPane.addRow(0, nameLabel, nameField);
        gridPane.addRow(1, trailerLabel, trailerField);
        gridPane.addRow(2, durationLabel, durationField);
        gridPane.addRow(3, backButton, okButton);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(messageLabel, gridPane, errorLabel);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(15, 15, 15, 15));

        backButton.setOnAction(e -> openWelcomeWindow(primaryStage));
        okButton.setOnAction(e -> addFilm());

        return vBox;
    }

    /**
     * This method takes film name, trailer path, duration inputs and checks if inputs are suitable to be able to add a
     * new film. If the film is not unique, admin has to try different inputs to add the new film. If the inputs are
     * unique the admin can successfully add the new film.
     */
    public void addFilm() {
        String filmNameInput = nameField.getText();
        String trailerPathInput = trailerField.getText();
        String durationInput = durationField.getText();
        HashMap<String, Film> filmHashMap = BackupFile.getFilmHashMap();

        Media errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
        MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);
        try {
            if (filmNameInput.equals("")) {
                errorLabel.setText("ERROR: Film name could not be empty!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (filmHashMap.containsKey(filmNameInput)) {
                errorLabel.setText("ERROR: This film name already exists!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (trailerPathInput.equals("")) {
                errorLabel.setText("ERROR: Trailer path could not be empty!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (durationInput.equals("") || Integer.parseInt(durationInput) <= 0) {
                errorLabel.setText("ERROR: Duration has to be a positive integer!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (!new File("assets\\trailers", trailerPathInput).exists()) {
                errorLabel.setText("ERROR: There is no such a trailer!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else {
                errorLabel.setText("SUCCESS: Film added successfully!");
                filmHashMap.put(filmNameInput, new Film(filmNameInput, trailerPathInput, Integer.parseInt(durationInput)));
                nameField.setText("");
                trailerField.setText("");
                durationField.setText("");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("ERROR: Duration has to be a positive integer!");
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
        }
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(getAddFilmWindow(primaryStage));

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * Opens welcome window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openWelcomeWindow(Stage primaryStage) {
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        welcomeWindow.start(primaryStage);
    }

}
