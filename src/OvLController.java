/**
 * Controller interface.
 *
 * @author Kevin Wang.12470
 *
 */
public interface OvLController {

    /**
     * Leave page 1.
     */
    void processBp1();

    /**
     * Go back 1 page.
     */
    void processBBack();

    /**
     * Update user info pane with inputted number of event text fields.
     */
    void processBUpdate();

    /**
     * Records user inputted information to the model.
     */
    void recordToModel();

    /**
     * Takes the input string and determines if it is an integer or not.
     *
     * @param s
     *            the input string to be checked whether it is an integer or not
     * @return true if integer, false if not
     */
    boolean isInteger(String s);

    /**
     * Takes the input string array and determines if they are all integers.
     *
     * @param sArray
     *            the input string array to be checked for integers
     * @return true if all are integers, false if not
     */
    boolean isInteger(String[] sArray);

    /**
     * Takes the input string array and converts all members to integers.
     *
     * @requires all members must be parsable as ints.
     * @param sArray
     *            The input string array.
     * @return an int array whose members correspond with the members of the
     *         input string array
     */
    int[] convertToInt(String[] sArray);

}
