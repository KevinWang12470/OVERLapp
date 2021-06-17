/**
 * Model interface.
 *
 * @author Kevin Wang.12470
 *
 */
public interface OvLModel {

    /**
     * Update the model for number of people in schedule.
     *
     * @param num
     *            The number from the controller.
     */
    void setNumOfPeople(int num);

    /**
     * Returns the model for number of people in schedule.
     *
     * @return the number of people in the schedule, of type int.
     */
    int returnNumOfPeople();

    /**
     * Returns the list of users' names as a String Array.
     *
     * @return the array of names of people involved
     */
    String[] getNames();

    /**
     * Records the user inputted names into the model.
     *
     * @param nameList
     *            The list of names to be recorded
     */
    void setNames(String[] nameList);

    /**
     * Returns the number of events each person is participating in, in the form
     * of an int array.
     *
     * @return an int array containing the number of events being participated
     *         in for each Being
     */
    int[] getEventNum();

    /**
     * Returns the number of events a single person is participating in.
     *
     * @param index
     *            the position in line that the Being is in the namesList
     * @return an int representing the number of events participated in.
     */
    int getEventNum(int index);

    /**
     * Records the user inputted number of events into the model.
     *
     * @param eventNumList
     *            The number of events the users are participating in
     */
    void setEventNum(int[] eventNumList);

}
