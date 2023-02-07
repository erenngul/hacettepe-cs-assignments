/**
 * Film class for existing films in application.
 */
public class Film {
    private String filmName;
    private String filmPath;
    private int filmDuration;

    /**
     * Film constructor to create film object.
     *
     * @param filmName name of the film
     * @param filmPath path of the film
     * @param filmDuration duration of the film
     */
    public Film(String filmName, String filmPath, int filmDuration) {
        this.filmName = filmName;
        this.filmPath = filmPath;
        this.filmDuration = filmDuration;
    }

    /**
     * @return name of the film
     */
    public String getFilmName() {
        return filmName;
    }

    /**
     * @return path of the film
     */
    public String getFilmPath() {
        return filmPath;
    }

    /**
     * @return duration of the film
     */
    public int getFilmDuration() {
        return filmDuration;
    }
}
