import java.util.ArrayList;

/**
 *
 * Participants in the master schedule. These beings have members that specify
 * their name, the number of events a week they have and an arraylist that has
 * the Events that they participate in.
 *
 * @author Kevin Wang
 *
 */
public class Being {

    /**
     * Name of the being in the master schedule.
     */
    private String name;

    /**
     * Number of events the being is participating in.
     */
    private int eventNum;

    /**
     * Arraylist of events for the being.
     */
    private ArrayList<Event> eventList;

    /**
     * Constructor.
     */
    public Being() {
        this.name = "myName";
        this.eventNum = 0;
        this.eventList = new ArrayList<Event>();
    }

    /**
     * Set the name of the being.
     *
     * @param input
     *            User inputted name.
     */
    public void setName(String input) {
        this.name = input;
    }

    /**
     * Output the name of the being.
     *
     * @return the name of the being.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the being.
     *
     * @param input
     *            User inputted name.
     */
    public void setEventNum(int input) {
        this.eventNum = input;
    }

    /**
     * Output the name of the being.
     *
     * @return the name of the being.
     */
    public int getEventNum() {
        return this.eventNum;
    }

}
