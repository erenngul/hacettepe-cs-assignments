import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Remove hall window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class RemoveHallWindow extends Application {

    private ComboBox<String> comboBox = new ComboBox<>();
    private Film selectedFilm = WelcomeWindow.getSelectedFilm();
    private ArrayList<String> hallArrayList = new ArrayList<>();
    private HashMap<String, Hall> hallHashMap = BackupFile.getHallHashMap();

    /**
     * Creates remove hall window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getRemoveHallWindow(Stage primaryStage) {
        Label messageLabel = new Label(String.format("Select the hall that you desire to remove from %s and then click OK.", selectedFilm.getFilmName()));

        HBox hBox = new HBox();
        Button backButton = new Button("⯇ BACK");
        Button okButton = new Button("OK");
        hBox.getChildren().addAll(backButton, okButton);
        hBox.setSpacing(8);
        hBox.setAlignment(Pos.CENTER);

        for (Hall hall : hallHashMap.values()) {
            if (hall.getFilmName().equals(selectedFilm.getFilmName()))
                hallArrayList.add(hall.getHallName());
        }

        ObservableList<String> hallList = FXCollections.observableArrayList(hallArrayList);
        comboBox.getItems().addAll(hallList);
        if (!hallArrayList.isEmpty())
            comboBox.setValue(hallArrayList.get(0));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(messageLabel, comboBox, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(15, 15, 15, 15));

        backButton.setOnAction(e -> openFilmWindow(primaryStage));
        okButton.setOnAction(e -> removeHall());

        return vBox;
    }

    /**
     * This method takes the selected hall name from ComboBox and removes the hall from application with that name.
     * Hall name is removed from the ComboBox.
     */
    public void removeHall() {
        String selectedHallName = comboBox.getValue();
        hallHashMap.remove(selectedHallName);
        comboBox.getItems().remove(selectedHallName);
        hallArrayList.remove(selectedHallName);
        if (!hallArrayList.isEmpty())
            comboBox.setValue(hallArrayList.get(0));
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
        Scene scene = new Scene(getRemoveHallWindow(primaryStage));

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
