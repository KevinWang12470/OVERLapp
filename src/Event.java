
/**
 * The events that the being is participating in. Includes start and end times.
 * Days of occurrence. Event type. etc.
 *
 * @author Kevin Wang
 *
 */
public class Event {

    /**
     * Time members.
     */
    private int startTime, endTime, occurrences;

    /**
     * String name members.
     */
    private String name, eventType, location;

    /**
     * Constructor.
     */
    public Event() {
        this.startTime = -1;
        this.endTime = -1;
        this.occurrences = -1;

    }

    /**
     * Sets the start time of the event.
     *
     * @param time
     *            The time in 24hr and no colon
     */
    public void setStartTime(int time) {
        this.startTime = time;
    }

    /**
     * Gets the start time of the event.
     *
     * @return The start time in 24hr and no colon
     */
    public int getStartTime() {
        return this.startTime;
    }

    /**
     * Sets the start time of the event.
     *
     * @param time
     *            The time in 24hr and no colon
     */
    public void setEndTime(int time) {
        this.endTime = time;
    }

    /**
     * Gets the end time of the event.
     *
     * @return The end time in 24hr and no colon
     */
    public int getEndTime() {
        return this.endTime;
    }

    /**
     * Sets the occurrences of the event.
     *
     * @param weekInput
     *            7 digit number corresponding to each day of the week. 1 if
     *            occurs, 0 if does not occur.
     */
    public void setOccurrences(int weekInput) {
        this.occurrences = weekInput;
    }

    /**
     * Gets the occurrences of the event.
     *
     * @return 7 digit number corresponding to each day of the week. 1 if
     *         occurs, 0 if does not occur.
     */
    public int getOccurrences() {
        return this.occurrences;
    }

    /**
     * Sets the name of the event.
     *
     * @param eventName
     *            Name of the Event
     */
    public void setName(String eventName) {
        this.name = eventName;
    }

    /**
     * Gets the name of the event.
     *
     * @return the name of the event
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the location of the event.
     *
     * @param eventLocation
     *            Location of the Event
     */
    public void setLocation(String eventLocation) {
        this.location = eventLocation;
    }

    /**
     * Gets the location of the event.
     *
     * @return the location of the Event
     */
    public String getLocation() {
        return this.location;
    }
}
