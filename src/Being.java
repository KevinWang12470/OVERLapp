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
        this.name = "";
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
     * Set the number of events the Being is doing a week.
     *
     * @param input
     *            User inputted name.
     */
    public void setEventNum(int input) {
        this.eventNum = input;

        while (this.eventNum > this.eventList.size()) {
            this.eventList.add(new Event());
        }

    }

    /**
     * Output the number of events the Being is doing a week.
     *
     * @return the name of the being.
     */
    public int getEventNum() {
        return this.eventNum;
    }

    /**
     * Sets the start time of the event.
     *
     * @param index
     *            index of event in eventList
     *
     * @param time
     *            The time in 24hr and no colon
     */
    public void setEventStartTime(int index, int time) {
        this.eventList.get(index).setStartTime(time);
    }

    /**
     * Gets the start time of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @return The start time in 24hr and no colon
     */
    public int getEventStartTime(int index) {
        return this.eventList.get(index).getStartTime();
    }

    /**
     * Sets the start time of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @param time
     *            The time in 24hr and no colon
     */
    public void setEventEndTime(int index, int time) {
        this.eventList.get(index).setEndTime(time);
    }

    /**
     * Gets the end time of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @return The end time in 24hr and no colon
     */
    public int getEventEndTime(int index) {
        return this.eventList.get(index).getEndTime();
    }

    /**
     * Sets the occurrences of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @param weekInput
     *            7 digit number corresponding to each day of the week. 1 if
     *            occurs, 0 if does not occur.
     */
    public void setEventOccurrences(int index, int weekInput) {
        this.eventList.get(index).setOccurrences(weekInput);
    }

    /**
     * Gets the occurrences of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @return 7 digit number corresponding to each day of the week. 1 if
     *         occurs, 0 if does not occur.
     */
    public int getEventOccurrences(int index) {
        return this.eventList.get(index).getOccurrences();
    }

    /**
     * Sets the name of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @param eventName
     *            Name of the Event
     */
    public void setEventName(int index, String eventName) {
        this.eventList.get(index).setName(eventName);
    }

    /**
     * Gets the name of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @return the name of the event
     */
    public String getEventName(int index) {
        return this.eventList.get(index).getName();
    }

    /**
     * Sets the location of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @param eventLocation
     *            Location of the Event
     */
    public void setEventLocation(int index, String eventLocation) {
        this.eventList.get(index).setLocation(eventLocation);
    }

    /**
     * Gets the location of the event.
     *
     * @param index
     *            index of the event in the eventList
     *
     * @return the location of the Event
     */
    public String getEventLocation(int index) {
        return this.eventList.get(index).getLocation();
    }

}
