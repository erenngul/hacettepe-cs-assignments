import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with a Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int low = 0;
        int high = index - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            LocalTime midFinishTime = LocalTime.parse(taskArray[mid].getFinishTime());
            LocalTime afterMidFinishTime = LocalTime.parse(taskArray[mid + 1].getFinishTime());
            LocalTime indexStartTime = LocalTime.parse(taskArray[index].getStartTime());
            if (midFinishTime.isBefore(indexStartTime) || midFinishTime.equals(indexStartTime)) {
                if (afterMidFinishTime.isBefore(indexStartTime) || afterMidFinishTime.equals(indexStartTime))
                    low = mid + 1;
                else
                    return mid;
            }
            else
                high = mid - 1;
        }
        return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        compatibility[0] = -1;
        for (int i = 1; i < compatibility.length; i++) {
            int compatibleIndex = binarySearch(i);
            compatibility[i] = compatibleIndex;
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        calculateCompatibility();
        System.out.println("Calculating max array");
        System.out.println("---------------------");
        calculateMaxWeight(taskArray.length - 1);
        System.out.println();
        System.out.println("Calculating the dynamic solution");
        System.out.println("--------------------------------");
        solveDynamic(taskArray.length - 1);
        System.out.println();
        System.out.println("Dynamic Schedule");
        System.out.println("----------------");
        for (Task task : planDynamic)
            System.out.println("At " + task.getStartTime() + ", " + task.getName() + ".");
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if (i == -1)
            return;
        System.out.println("Called solveDynamic(" + i + ")");
        if (compatibility[i] == -1 || taskArray[i].getWeight() + maxWeight[compatibility[i]] > maxWeight[i - 1]) {
            solveDynamic(compatibility[i]);
            planDynamic.add(taskArray[i]);
        }
        else
            solveDynamic(i - 1);
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        System.out.println("Called calculateMaxWeight(" + i + ")");
        if (i == -1)
            return 0.0;
        if (maxWeight[i] == null || i == 0)
            maxWeight[i] = Math.max(taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]), calculateMaxWeight(i - 1));
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        System.out.println("Greedy Schedule");
        System.out.println("---------------");
        System.out.println("At " + taskArray[0].getStartTime() + ", " + taskArray[0].getName() + ".");
        planGreedy.add(taskArray[0]);
        LocalTime previousTaskFinishTime = LocalTime.parse(taskArray[0].getFinishTime());
        for (int i = 1; i < taskArray.length; i++) {
            LocalTime currentTaskStartTime = LocalTime.parse(taskArray[i].getStartTime());
            if (currentTaskStartTime.isAfter(previousTaskFinishTime) || currentTaskStartTime.equals(previousTaskFinishTime)) {
                System.out.println("At " + taskArray[i].getStartTime() + ", " + taskArray[i].getName() + ".");
                planGreedy.add(taskArray[i]);
                previousTaskFinishTime = LocalTime.parse(taskArray[i].getFinishTime());
            }
        }
        return planGreedy;
    }
}
