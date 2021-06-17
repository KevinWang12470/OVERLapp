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
}
