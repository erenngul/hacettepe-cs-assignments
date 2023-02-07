import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class consists of static methods to read the backup.dat file before launching the application and update it after
 * application.
 */
public class BackupFile {

    private static ArrayList<String[]> backupList = new ArrayList<>();
    private static HashMap<String, User> userHashMap = new HashMap<>();
    private static HashMap<String, Film> filmHashMap = new HashMap<>();
    private static HashMap<String, Hall> hallHashMap = new HashMap<>();
    private static ArrayList<String[]> hallList = new ArrayList<>();
    private static ArrayList<String[]> seatList = new ArrayList<>();

    /**
     * Reads backup.dat file, classifies the data according to first word of line and adds the data to various lists.
     * If backup.dat doesn't exist, a new one will be created and a new admin user is added to the file.
     */
    public static void readBackupFile() {
        File backup = new File("assets\\data\\backup.dat");

        if (backup.length() == 0) {
            User admin = new User("admin", hashPassword("password"), true, true);
            try {
                FileWriter writer = new FileWriter(backup);
                writer.write(String.format("user\t%s\t%s\t%s\t%s\n", admin.getUsername(), admin.getPassword(), admin.isClubMember(), admin.isAdmin()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Scanner scanner = new Scanner(backup);

            while (scanner.hasNextLine())
                backupList.add(scanner.nextLine().trim().split("\t"));

            for (String[] line : backupList) {
                switch (line[0]) {
                    case "user":
                        userHashMap.put(line[1], new User(line[1], line[2], Boolean.parseBoolean(line[3]), Boolean.parseBoolean(line[4])));
                        break;
                    case "film":
                        filmHashMap.put(line[1], new Film(line[1], line[2], Integer.parseInt(line[3])));
                        break;
                    case "hall":
                        hallList.add(line);
                        break;
                    case "seat":
                        seatList.add(line);
                }
            }

            for (String[] line : hallList)
                hallHashMap.put(line[2], new Hall(line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5])));

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates backup.dat file when the application closes. The method gets new data from updated data HashMaps during
     * the application runtime. Clears the backup.dat and writes all of the new data in order.
     */
    public static void updateBackupFile() {
        File backup = new File("assets\\data\\backup.dat");
        try {
            FileWriter writer = new FileWriter(backup);

            for (User user : userHashMap.values())
                writer.write(String.format("user\t%s\t%s\t%s\t%s\n", user.getUsername(), user.getPassword(), user.isClubMember(), user.isAdmin()));

            for (Film film : filmHashMap.values()) {
                writer.write(String.format("film\t%s\t%s\t%s\n", film.getFilmName(), film.getFilmPath(), film.getFilmDuration()));
                for (Hall hall : hallHashMap.values()) {
                    if (hall.getFilmName().equals(film.getFilmName())) {
                        writer.write(String.format("hall\t%s\t%s\t%s\t%s\t%s\n", hall.getFilmName(), hall.getHallName(), hall.getPricePerSeat(), hall.getRow(), hall.getColumn()));
                        for (Seat seat : hall.getSeatList())
                            writer.write(String.format("seat\t%s\t%s\t%s\t%s\t%s\t%s\n", hall.getFilmName(), hall.getHallName(), seat.getRowOfSeat(), seat.getColumnOfSeat(), seat.getOwnerName(), seat.getPriceThatItHasBeenBought()));
                    }
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns Base64 encoded version of MD5 hashed version of the given password.
     *
     * @param password Password to be hashed.
     * @return Base64 encoded version of MD5 hashed version of password.
     */
    private static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

    /**
     * @return ArrayList of seats taken from backup.dat file.
     */
    public static ArrayList<String[]> getSeatList() {
        return seatList;
    }

    /**
     * @return HashMap of users taken from backup.dat file.
     */
    public static HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    /**
     * @return HashMap of films taken from backup.dat file.
     */
    public static HashMap<String, Film> getFilmHashMap() {
        return filmHashMap;
    }

    /**
     * @return HashMap of halls taken from backup.dat file.
     */
    public static HashMap<String, Hall> getHallHashMap() {
        return hallHashMap;
    }

}
