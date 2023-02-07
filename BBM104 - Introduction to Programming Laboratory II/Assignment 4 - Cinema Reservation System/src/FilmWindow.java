import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Film window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class FilmWindow extends Application {

    private User loggedInUser = LoginWindow.getLoggedInUser();
    private Film selectedFilm = WelcomeWindow.getSelectedFilm();
    private HashMap<String, Hall> hallHashMap = BackupFile.getHallHashMap();
    private ArrayList<String> hallArrayList = new ArrayList<>();
    private ComboBox<String> comboBox = new ComboBox<>();
    private Media media;
    private MediaPlayer mediaPlayer;
    private static Hall selectedHall;

    /**
     * Creates film window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized HBox to satisfy the graphical design.
     */
    public HBox getFilmWindow(Stage primaryStage) {
        Label filmLabel = new Label(String.format("%s (%s minutes)", selectedFilm.getFilmName(), selectedFilm.getFilmDuration()));
        try {
            media = new Media(new File(String.format("assets\\trailers\\%s", selectedFilm.getFilmPath())).toURI().toString());
        } catch (Exception ignored) {}
        mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(600);
        Button backButton = new Button("⯇BACK");
        Button addHallButton = new Button("Add Hall");
        Button removeHallButton = new Button("Remove Hall");
        for (String hallName : hallHashMap.keySet()) {
            if (selectedFilm.getFilmName().equals(hallHashMap.get(hallName).getFilmName()))
                hallArrayList.add(hallName);
        }
        ObservableList<String> hallList = FXCollections.observableArrayList(hallArrayList);
        comboBox.getItems().addAll(hallList);
        if (!hallArrayList.isEmpty())
            comboBox.setValue(hallArrayList.get(0));
        Button okButton = new Button("OK");

        backButton.setOnAction(e -> openWelcomeWindow(primaryStage));
        addHallButton.setOnAction(e -> openAddHallWindow(primaryStage));
        removeHallButton.setOnAction(e -> openRemoveHallWindow(primaryStage));
        okButton.setOnAction(e -> openHallWindow(primaryStage));

        VBox screenVBox = new VBox();
        if (loggedInUser.isAdmin()) {
            HBox adminHBox = new HBox();
            adminHBox.getChildren().addAll(backButton, addHallButton, removeHallButton, comboBox, okButton);
            adminHBox.setSpacing(8);
            adminHBox.setAlignment(Pos.CENTER);
            screenVBox.getChildren().addAll(filmLabel, mediaView, adminHBox);
        }
        else {
            HBox userHBox = new HBox();
            HBox.setMargin(backButton, new Insets(0, 20, 0, 0));
            userHBox.getChildren().addAll(backButton, comboBox, okButton);
            userHBox.setSpacing(8);
            userHBox.setAlignment(Pos.CENTER);
            screenVBox.getChildren().addAll(filmLabel, mediaView, userHBox);
        }
        screenVBox.setAlignment(Pos.CENTER);
        screenVBox.setSpacing(8);

        VBox mediaButtonVBox = new VBox();
        Button playButton = new Button("⯈");
        playButton.setPrefSize(45, 25);
        playButton.setOnAction(e -> {
            if (playButton.getText().equals("⯈")) {
                mediaPlayer.play();
                playButton.setText("||");
            } else {
                mediaPlayer.pause();
                playButton.setText("⯈");
            }
        });
        Button skipBackButton = new Button("<<");
        skipBackButton.setPrefSize(45, 25);
        skipBackButton.setOnAction(e -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            if (currentTime.subtract(Duration.seconds(5)).greaterThanOrEqualTo(Duration.ZERO))
                mediaPlayer.seek(currentTime.subtract(Duration.seconds(5)));
            else
                mediaPlayer.seek(Duration.ZERO);
        });
        Button skipForwardButton = new Button(">>");
        skipForwardButton.setPrefSize(45, 25);
        skipForwardButton.setOnAction(e -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            if (currentTime.add(Duration.seconds(5)).lessThanOrEqualTo(mediaPlayer.getTotalDuration()))
                mediaPlayer.seek(currentTime.add(Duration.seconds(5)));
            else
                mediaPlayer.seek(mediaPlayer.getTotalDuration());
        });
        Button rewindButton = new Button("|<<");
        rewindButton.setPrefSize(45, 25);
        rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));
        Slider volumeSlider = new Slider();
        volumeSlider.setValue(50);
        volumeSlider.setOrientation(Orientation.VERTICAL);
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

        mediaButtonVBox.getChildren().addAll(playButton, skipBackButton, skipForwardButton, rewindButton, volumeSlider);
        mediaButtonVBox.setSpacing(10);
        mediaButtonVBox.setAlignment(Pos.CENTER);

        HBox windowHBox = new HBox();
        windowHBox.getChildren().addAll(screenVBox, mediaButtonVBox);
        windowHBox.setSpacing(8);
        windowHBox.setAlignment(Pos.CENTER);
        windowHBox.setPadding(new Insets(15, 15, 15, 15));

        return windowHBox;
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
        Scene scene = new Scene(getFilmWindow(primaryStage),680, 425);

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * Opens welcome window. If mediaPlayer is playing, it is stopped.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openWelcomeWindow(Stage primaryStage) {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            mediaPlayer.stop();
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        welcomeWindow.start(primaryStage);
    }

    /**
     * Opens add hall window. If mediaPlayer is playing, it is stopped.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openAddHallWindow(Stage primaryStage) {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            mediaPlayer.stop();
        AddHallWindow addHallWindow = new AddHallWindow();
        addHallWindow.start(primaryStage);
    }

    /**
     * Opens remove hall window. If mediaPlayer is playing, it is stopped.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openRemoveHallWindow(Stage primaryStage) {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            mediaPlayer.stop();
        RemoveHallWindow removeHallWindow = new RemoveHallWindow();
        removeHallWindow.start(primaryStage);
    }

    /**
     * Opens hall window. If mediaPlayer is playing, it is stopped.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openHallWindow(Stage primaryStage) {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            mediaPlayer.stop();
        selectedHall = BackupFile.getHallHashMap().get(comboBox.getValue());
        try {
            HallWindow hallWindow = new HallWindow();
            hallWindow.start(primaryStage);
        } catch (Exception ignored) {}
    }

    /**
     * @return selected hall in ComboBox.
     */
    public static Hall getSelectedHall() {
        return selectedHall;
    }

}
