import java.awt.event.ActionListener;

/**
 * View interface.
 *
 * @author Kevin Wang.12470
 */
public interface OvLView extends ActionListener {

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     *
     * @param controller
     *            controller to register
     */
    void registerObserver(OvLController controller);

    /**
     * Adds the appropriate number of JPanels to the JTabbedPane on page2
     * according to user input.
     *
     * @param numOfP
     *            The number of people to be included into the schedule
     */
    void updatePanePanels(int numOfP);

    /**
     * If viewing page one, set page 1 visibility to true, otherwise set to
     * false.
     *
     * @param onPage
     */
    void onPage1(boolean onPage);

    /**
     * If viewing page one, set page 2 visibility to true, otherwise set to
     * false.
     *
     * @param onPage
     */
    void onPage2(boolean onPage);

    /**
     * If viewing page one, set page 3 visibility to true, otherwise set to
     * false.
     *
     * @param onPage
     */
    void onPage3(boolean onPage);

    /**
     * Sets the input from the combo box to a string for later.
     *
     * @return A string that represents the input from the combo box
     */
    String populationToString();

    /**
     * Returns the index of the page that is being viewed.
     *
     * @return index of currently viewed page
     */
    int returnCurrentPage();

    /**
     * Sets page visible given index.
     *
     * @param index
     *            int that describes which page to set visible
     */
    void setPageVisible(int index);

    /**
     * Sets page invisible given index.
     *
     * @param index
     *            int that describes which page to set invisible
     */
    void setPageInvisible(int index);
}
