import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * Remove film window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class RemoveFilmWindow extends Application {

    private ComboBox<String> comboBox = new ComboBox<>();
    private HashMap<String, Film> filmHashMap = BackupFile.getFilmHashMap();
    private ArrayList<String> filmArrayList = new ArrayList<>(filmHashMap.keySet());

    /**
     * Creates remove film window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getRemoveFilmWindow(Stage primaryStage) {
        Label messageLabel = new Label("Select the film that you desire to remove and then click OK.");

        HBox hBox = new HBox();
        Button backButton = new Button("⯇ BACK");
        Button okButton = new Button("OK");
        hBox.getChildren().addAll(backButton, okButton);
        hBox.setSpacing(8);
        hBox.setAlignment(Pos.CENTER);

        ObservableList<String> filmList = FXCollections.observableArrayList(filmArrayList);
        comboBox.getItems().addAll(filmList);
        if (!filmArrayList.isEmpty())
            comboBox.setValue(filmArrayList.get(0));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(messageLabel, comboBox, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        backButton.setOnAction(e -> openWelcomeWindow(primaryStage));
        okButton.setOnAction(e -> removeFilm());

        return vBox;
    }

    /**
     * This method removes the selected film in ComboBox. Film is removed from the application.
     */
    public void removeFilm() {
        String selectedFilmName = comboBox.getValue();
        filmHashMap.remove(selectedFilmName);
        comboBox.getItems().remove(selectedFilmName);
        filmArrayList.remove(selectedFilmName);
        if (!filmArrayList.isEmpty())
            comboBox.setValue(filmArrayList.get(0));
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
        Scene scene = new Scene(getRemoveFilmWindow(primaryStage), 350, 120);

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
