/**
 * Seat class for existing seats in application.
 */
public class Seat {
    private int rowOfSeat;
    private int columnOfSeat;
    private String ownerName;
    private int priceThatItHasBeenBought;
    private boolean bought;

    /**
     * Seat constructor to create seat object.
     *
     * @param rowOfSeat row of the seat
     * @param columnOfSeat column of the seat
     * @param ownerName owner name of the seat
     * @param priceThatItHasBeenBought price of the seat
     */
    public Seat(int rowOfSeat, int columnOfSeat, String ownerName, int priceThatItHasBeenBought) {
        this.rowOfSeat = rowOfSeat;
        this.columnOfSeat = columnOfSeat;
        this.ownerName = ownerName;
        this.priceThatItHasBeenBought = priceThatItHasBeenBought;
        bought = !ownerName.equals("null");
    }

    /**
     * @return row of the seat
     */
    public int getRowOfSeat() {
        return rowOfSeat;
    }

    /**
     * @return column of the seat
     */
    public int getColumnOfSeat() {
        return columnOfSeat;
    }

    /**
     * @return owner name of the seat
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Sets owner name of the seat.
     *
     * @param ownerName owner name to be set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return price of the seat
     */
    public int getPriceThatItHasBeenBought() {
        return priceThatItHasBeenBought;
    }

    /**
     * Sets price of the seat.
     *
     * @param price price to be set
     */
    public void setPriceThatItHasBeenBought(int price) {
        priceThatItHasBeenBought = price;
    }

    /**
     * @return if seat is bought or not
     */
    public boolean isBought() {
        return bought;
    }

    /**
     * Sets if seat is bought or not
     *
     * @param bought boolean value to be set
     */
    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
