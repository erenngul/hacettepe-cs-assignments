import java.time.LocalTime;

public class Task implements Comparable<Task> {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        LocalTime startTime = LocalTime.parse(getStartTime());
        LocalTime finishTime = startTime.plusHours(getDuration());
        return finishTime.toString();
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        return getImportance() * (isUrgent() ? 2000 : 1) / (float) getDuration();
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Task o) {
        LocalTime selfFinishTime = LocalTime.parse(getFinishTime());
        LocalTime objectFinishTime = LocalTime.parse(o.getFinishTime());
        if (selfFinishTime.isAfter(objectFinishTime))
            return 1;
        else if (selfFinishTime.equals(objectFinishTime))
            return 0;
        else
            return -1;
    }
}
