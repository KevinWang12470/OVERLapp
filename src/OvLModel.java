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
}
