/**
 * User class for users who use the application.
 */
public class User {
    private String username;
    private String password;
    private boolean clubMember;
    private boolean admin;

    /**
     * User constructor to create user object.
     *
     * @param username username of user
     * @param password password of user
     * @param clubMember if user is club member or not
     * @param admin if user is admin or not
     */
    public User(String username, String password, boolean clubMember, boolean admin) {
        this.username = username;
        this.password = password;
        this.clubMember = clubMember;
        this.admin = admin;
    }

    /**
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return if user is club member or not
     */
    public boolean isClubMember() {
        return clubMember;
    }

    /**
     * Sets club member according to the parameter
     *
     * @param clubMember boolean value to set club membership
     */
    public void setClubMember(boolean clubMember) {
        this.clubMember = clubMember;
    }

    /**
     * @return if user is admin or not
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets admin according to the paramter
     *
     * @param admin boolean value to set adminship
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
