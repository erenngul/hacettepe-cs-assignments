import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 * Add hall window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class AddHallWindow extends Application {

    private Film selectedFilm = WelcomeWindow.getSelectedFilm();
    private TextField nameField = new TextField();
    private TextField priceField = new TextField();
    private Integer[] integerArray = {3, 4, 5, 6, 7, 8, 9, 10};
    private ComboBox<Integer> rowComboBox = new ComboBox<>();
    private ComboBox<Integer> columnComboBox = new ComboBox<>();
    private Label errorLabel = new Label("");

    /**
     * Creates add hall window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getAddHallWindow(Stage primaryStage) {
        Label messageLabel = new Label(String.format("%s (%d minutes)", selectedFilm.getFilmName(), selectedFilm.getFilmDuration()));

        Label rowLabel = new Label("Row:");
        rowComboBox.getItems().addAll(integerArray);
        rowComboBox.setValue(integerArray[0]);
        Label columnLabel = new Label("Column:");
        columnComboBox.getItems().addAll(integerArray);
        columnComboBox.setValue(integerArray[0]);
        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        Button backButton = new Button("⯇ Back");
        Button okButton = new Button("OK");

        GridPane gridPane = new GridPane();
        gridPane.addRow(0, rowLabel, rowComboBox);
        gridPane.addRow(1, columnLabel, columnComboBox);
        gridPane.addRow(2, nameLabel, nameField);
        gridPane.addRow(3, priceLabel, priceField);
        gridPane.addRow(4, backButton, okButton);
        GridPane.setHalignment(rowComboBox, HPos.CENTER);
        GridPane.setHalignment(columnComboBox, HPos.CENTER);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(messageLabel, gridPane, errorLabel);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        backButton.setOnAction(e -> openFilmWindow(primaryStage));
        okButton.setOnAction(e -> addHall());

        return vBox;
    }

    /**
     * This method takes values of hall name, price from TextFields and row and column from the ComboBoxes to create a
     * new hall using these values. If hall name is not unique, it gives error and user has to try a new name. If inputs
     * are suitable, the hall is created.
     */
    public void addHall() {
        int selectedRow = rowComboBox.getValue();
        int selectedColumn = columnComboBox.getValue();
        String hallNameInput = nameField.getText();
        String priceInput = priceField.getText();
        HashMap<String, Hall> hallHashMap = BackupFile.getHallHashMap();

        Media errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
        MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);
        try {
            if (hallNameInput.equals("")) {
                errorLabel.setText("ERROR: Hall name could not be empty!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (hallHashMap.containsKey(hallNameInput)) {
                errorLabel.setText("ERROR: This hall name already exists!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (priceInput.equals("")) {
                errorLabel.setText("ERROR: Price could not be empty!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else if (Integer.parseInt(priceInput) <= 0) {
                errorLabel.setText("ERROR: Price has to be a positive integer!");
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
            }
            else {
                errorLabel.setText("SUCCESS: Hall successfully created!");
                Hall createdHall = new Hall(selectedFilm.getFilmName(), hallNameInput, Integer.parseInt(priceInput), selectedRow, selectedColumn);
                createdHall.createFromEmptyHall();
                hallHashMap.put(hallNameInput, createdHall);
                nameField.setText("");
                priceField.setText("");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("ERROR: Price has to be a positive integer!");
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
        Scene scene = new Scene(getAddHallWindow(primaryStage), 350, 225);

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * Opens film window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openFilmWindow(Stage primaryStage) {
        FilmWindow filmWindow = new FilmWindow();
        filmWindow.start(primaryStage);
    }

}
