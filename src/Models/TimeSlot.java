package Models;

/**
 * Class created to hold a twelve hour time as a string and its corresponding military time to use for calculations
 */
public class TimeSlot {

    String twelveHourTime;
    int militaryTime;

    public TimeSlot(String time, int militaryTime) {

        this.militaryTime = militaryTime;
        twelveHourTime = time;

    }

    /**
     * Getter for twelve hour time
     * @return
     */
    public String getTwelveHourTime() {

        return twelveHourTime;

    }

    /**
     * Getter for military time
     * @return
     */
    public int getMilitaryTime() {

        return militaryTime;

    }

}
