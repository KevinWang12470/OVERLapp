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

}
