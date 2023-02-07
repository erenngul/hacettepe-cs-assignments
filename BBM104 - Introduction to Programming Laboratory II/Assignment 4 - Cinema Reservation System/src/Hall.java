import java.util.ArrayList;

/**
 * Hall class for existing halls in application.
 */
public class Hall {
    private String filmName;
    private String hallName;
    private int pricePerSeat;
    private int row;
    private int column;
    private ArrayList<Seat> seatList = new ArrayList<>();
    private ArrayList<ArrayList<Seat>> seatMatrix = new ArrayList<>();

    /**
     * Hall constructor to create hall object. Makes seatList and seatMatrix to be used for halls in application.
     *
     * @param filmName name of the film in the hall
     * @param hallName name of the hall
     * @param pricePerSeat price per seat
     * @param row row number of the hall
     * @param column column number of the hall
     */
    public Hall(String filmName, String hallName, int pricePerSeat, int row, int column) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.pricePerSeat = pricePerSeat;
        this.row = row;
        this.column = column;

        for (String[] line : BackupFile.getSeatList()) {
            if (hallName.equals(line[2]))
                seatList.add(new Seat(Integer.parseInt(line[3]), Integer.parseInt(line[4]), line[5], Integer.parseInt(line[6])));
        }

        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            seatMatrix.add(new ArrayList<>());
        }
        for (String[] line : BackupFile.getSeatList()) {
            if (hallName.equals(line[2]))
                seatMatrix.get(Integer.parseInt(line[3])).add(Integer.parseInt(line[4]), new Seat(Integer.parseInt(line[3]), Integer.parseInt(line[4]), line[5], Integer.parseInt(line[6])));
        }
    }

    /**
     * This method is to put seats inside an empty hall after one was added to hall list of a film.
     */
    public void createFromEmptyHall() {
        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            seatMatrix.add(new ArrayList<>());
            for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                seatList.add(new Seat(rowIndex, columnIndex, "null", pricePerSeat));
                seatMatrix.get(rowIndex).add(columnIndex, new Seat(rowIndex, columnIndex, "null", pricePerSeat));
            }
        }
    }

    /**
     * @return name of the film in the hall
     */
    public String getFilmName() {
        return filmName;
    }

    /**
     * @return name of the hall
     */
    public String getHallName() {
        return hallName;
    }

    /**
     * @return price per seat
     */
    public int getPricePerSeat() {
        return pricePerSeat;
    }

    /**
     * @return row number of the hall
     */
    public int getRow() {
        return row;
    }

    /**
     * @return column number of the hall
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return seatList of the hall
     */
    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    /**
     * @return seatMatrix of the hall
     */
    public ArrayList<ArrayList<Seat>> getSeatMatrix() {
        return seatMatrix;
    }
}
