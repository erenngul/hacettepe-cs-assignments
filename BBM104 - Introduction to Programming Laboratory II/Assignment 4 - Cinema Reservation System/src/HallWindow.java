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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hall window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class HallWindow extends Application {

    private User loggedInUser = LoginWindow.getLoggedInUser();
    private Film selectedFilm = WelcomeWindow.getSelectedFilm();
    private Hall selectedHall = FilmWindow.getSelectedHall();
    private HashMap<String, User> userHashMap = BackupFile.getUserHashMap();
    private ArrayList<ArrayList<Seat>> seatMatrix = selectedHall.getSeatMatrix();
    private String[] userArray = BackupFile.getUserHashMap().keySet().toArray(new String[0]);
    private Image emptySeatImage = new Image("assets\\icons\\empty_seat.png");
    private Image reservedSeatImage = new Image("assets\\icons\\reserved_seat.png");
    private ComboBox<String> adminComboBox = new ComboBox<>();
    private Label operationLabel = new Label("");
    private Label systemLabel = new Label("");
    private GridPane gridPane = new GridPane();
    private double discountPercentage = Integer.parseInt(PropertiesFile.getPropertiesTable().getProperty("discount-percentage"));

    /**
     * Creates hall window using various panes, labels and buttons. It iterates through the selected hall's seat list
     * and creates seats according to their attributes.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getHallWindow(Stage primaryStage) {
        VBox vBox = new VBox();
        Label hallLabel = new Label(String.format("%s (%s Minutes) Hall: %s", selectedFilm.getFilmName(), selectedFilm.getFilmDuration(), selectedHall.getHallName()));

        for (Seat seat : selectedHall.getSeatList()) {

            ImageView reservedSeatImageView = new ImageView(reservedSeatImage);
            reservedSeatImageView.setFitHeight(35);
            reservedSeatImageView.setFitWidth(35);
            ImageView emptySeatImageView = new ImageView(emptySeatImage);
            emptySeatImageView.setFitHeight(35);
            emptySeatImageView.setFitWidth(35);

            if (seat.isBought()) {
                Button reservedSeatButton = new Button("", reservedSeatImageView);
                gridPane.add(reservedSeatButton, seat.getColumnOfSeat(), seat.getRowOfSeat());
                if (loggedInUser.isAdmin()) {
                    reservedSeatButton.setOnMouseEntered(e -> systemLabel.setText(String.format("Bought by %s for %d TL!", seat.getOwnerName(), seat.getPriceThatItHasBeenBought())));
                    reservedSeatButton.setOnMouseExited(e -> systemLabel.setText(""));
                }
                if (!loggedInUser.isAdmin() && !seat.getOwnerName().equals(loggedInUser.getUsername()))
                    reservedSeatButton.setDisable(true);
                reservedSeatButton.setOnAction(e -> refundSeat(GridPane.getRowIndex(reservedSeatButton), GridPane.getColumnIndex(reservedSeatButton)));
            }
            else {
                Button emptySeatButton = new Button("", emptySeatImageView);
                gridPane.add(emptySeatButton, seat.getColumnOfSeat(), seat.getRowOfSeat());
                if (loggedInUser.isAdmin()) {
                    emptySeatButton.setOnMouseEntered(e -> systemLabel.setText("Not bought yet!"));
                    emptySeatButton.setOnMouseExited(e -> systemLabel.setText(""));
                }
                emptySeatButton.setOnAction(e -> buySeat(GridPane.getRowIndex(emptySeatButton), GridPane.getColumnIndex(emptySeatButton)));
            }
        }
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);

        ObservableList<String> userList = FXCollections.observableArrayList(userArray);
        adminComboBox.getItems().addAll(userList);
        adminComboBox.setValue(userArray[0]);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        Button backButton = new Button("⯇ BACK");
        backButton.setAlignment(Pos.CENTER_LEFT);
        backButton.setOnAction(e -> openFilmWindow(primaryStage));

        VBox hallWindowVBox = new VBox();
        if (loggedInUser.isAdmin()) {
            vBox.getChildren().addAll(hallLabel, gridPane, adminComboBox, systemLabel, operationLabel);
            hallWindowVBox.getChildren().addAll(vBox, backButton);
        }
        else {
            vBox.getChildren().addAll(hallLabel, gridPane, operationLabel);
            hallWindowVBox.getChildren().addAll(vBox, backButton);
        }
        hallWindowVBox.setSpacing(20);
        hallWindowVBox.setPadding(new Insets(15, 15, 15, 15));

        return hallWindowVBox;
    }

    /**
     * This method buys seat for logged-in user or for another user if admin is invoking this method. Afterwards the
     * hall is updated.
     *
     * @param row row of seat.
     * @param column column of seat.
     */
    public void buySeat(int row, int column) {
        Seat seat = seatMatrix.get(row).get(column);
        double discountedSeatPrice = selectedHall.getPricePerSeat() * ((100-discountPercentage)/100);

        if (loggedInUser.isAdmin()) {
            String selectedUsername = adminComboBox.getValue();
            User selectedUser = userHashMap.get(selectedUsername);
            seat.setOwnerName(selectedUsername);
            if (selectedUser.isClubMember()) {
                operationLabel.setText(String.format("Seat at %d-%d is bought for %s for %d TL successfully!", row + 1, column + 1, seat.getOwnerName(), (int) discountedSeatPrice));
                seat.setPriceThatItHasBeenBought((int) discountedSeatPrice);
            }
            else {
                operationLabel.setText(String.format("Seat at %d-%d is bought for %s for %d TL successfully!", row + 1, column + 1, seat.getOwnerName(), selectedHall.getPricePerSeat()));
                seat.setPriceThatItHasBeenBought(selectedHall.getPricePerSeat());
            }
        }
        else {
            seat.setOwnerName(loggedInUser.getUsername());
            if (loggedInUser.isClubMember()) {
                operationLabel.setText(String.format("Seat at %d-%d is bought for %d TL successfully!", row + 1, column + 1, (int) discountedSeatPrice));
                seat.setPriceThatItHasBeenBought((int) discountedSeatPrice);
            }
            else {
                operationLabel.setText(String.format("Seat at %d-%d is bought for %d TL successfully!", row + 1, column + 1, selectedHall.getPricePerSeat()));
                seat.setPriceThatItHasBeenBought(selectedHall.getPricePerSeat());
            }
        }

        seat.setBought(true);
        updateHall();
    }

    /**
     * This method refunds seat for logged-in user or for another user if admin is invoking this method. Afterwards the
     * hall is updated.
     *
     * @param row row of seat.
     * @param column column of seat.
     */
    public void refundSeat(int row, int column) {
        Seat seat = seatMatrix.get(row).get(column);

        if (loggedInUser.isAdmin())
            operationLabel.setText(String.format("Seat at %d-%d is refunded for %s successfully!", row + 1, column + 1, seat.getOwnerName()));
        else
            operationLabel.setText(String.format("Seat at %d-%d is refunded successfully!", row + 1, column + 1));

        seat.setOwnerName("null");
        seat.setBought(false);
        updateHall();
    }

    /**
     * This method clears the seat list of selected hall first. Then it iterates through seatMatrix and replaces the
     * previous hall with new seats.
     */
    public void updateHall() {
        selectedHall.getSeatList().clear();

        for (ArrayList<Seat> row : seatMatrix) {
            for (Seat seat : row) {
                ImageView reservedSeatImageView = new ImageView(reservedSeatImage);
                reservedSeatImageView.setFitHeight(35);
                reservedSeatImageView.setFitWidth(35);
                ImageView emptySeatImageView = new ImageView(emptySeatImage);
                emptySeatImageView.setFitHeight(35);
                emptySeatImageView.setFitWidth(35);

                if (seat.isBought()) {
                    Button reservedSeatButton = new Button("", reservedSeatImageView);
                    if (loggedInUser.isAdmin() || seat.getOwnerName().equals(loggedInUser.getUsername()))
                        gridPane.add(reservedSeatButton, seat.getColumnOfSeat(), seat.getRowOfSeat());
                    if (loggedInUser.isAdmin()) {
                        reservedSeatButton.setOnMouseEntered(e -> systemLabel.setText(String.format("Bought by %s for %d TL!", seat.getOwnerName(), seat.getPriceThatItHasBeenBought())));
                        reservedSeatButton.setOnMouseExited(e -> systemLabel.setText(""));
                    }
                    if (!loggedInUser.isAdmin() && !seat.getOwnerName().equals(loggedInUser.getUsername()))
                        reservedSeatButton.setDisable(true);
                    reservedSeatButton.setOnAction(e -> refundSeat(GridPane.getRowIndex(reservedSeatButton), GridPane.getColumnIndex(reservedSeatButton)));
                }
                else {
                    Button emptySeatButton = new Button("", emptySeatImageView);
                    gridPane.add(emptySeatButton, seat.getColumnOfSeat(), seat.getRowOfSeat());
                    if (loggedInUser.isAdmin()) {
                        emptySeatButton.setOnMouseEntered(e -> systemLabel.setText("Not bought yet!"));
                        emptySeatButton.setOnMouseExited(e -> systemLabel.setText(""));
                    }
                    emptySeatButton.setOnAction(e -> buySeat(GridPane.getRowIndex(emptySeatButton), GridPane.getColumnIndex(emptySeatButton)));
                }

                selectedHall.getSeatList().add(seat);
            }
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
        Scene scene = new Scene(getHallWindow(primaryStage));

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
