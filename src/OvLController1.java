import javax.swing.JFrame;
import javax.swing.JOptionPane;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Kevin Wang.12470
 */
public final class OvLController1 implements OvLController {

    /**
     * Model object.
     */
    private final OvLModel model;

    /**
     * View object.
     */
    private final OvLView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Range of people allowed on schedule at one time.
     */
    private static final int MAXNUM = 8;

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(OvLModel model, OvLView view) {
//
//        /* update display views */
//        view.updateTopDisplay(model.top());
//        view.updateBottomDisplay(model.bottom());
//
//        /*
//         * enable or disable buttons depending on values in top and bottom
//         * displays
//         */
//        view.updateDivideAllowed(!model.bottom().isZero());
//        view.updatePowerAllowed(model.bottom().compareTo(INT_LIMIT) <= 0);
//        view.updateRootAllowed(model.bottom().compareTo(TWO) >= 0
//                && model.bottom().compareTo(INT_LIMIT) <= 0);
//        view.updateSubtractAllowed(model.top().compareTo(model.bottom()) >= 0);
//
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public OvLController1(OvLModel model, OvLView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processBp1() {
        boolean inRange = true;
        boolean isInt = true;

//        try {
//            this.numOfPeople.commitEdit();
//        } catch (java.text.ParseException e) {
//            JOptionPane.showMessageDialog(new JFrame(),
//                    "There was a parsing problem...");
//
//        }

        //set result to string
        String tempString = this.view.populationToString();

        //Check if input is an integer
        try {
            Integer.parseInt(tempString);
        } catch (NumberFormatException e) {
            isInt = false;
            //notify user
            JOptionPane.showMessageDialog(new JFrame(),
                    "Input is not a valid number");
        }

        //if input is an integer, continue
        if (isInt) {

            //get the integer
            /*
             * Must do integer.parseInt after toString because if user inputs a
             * string, then inputs a valid integer, the program will parse the
             * number as a string instead of an int. Thus must run to string
             * then parseInt
             */
            this.model.setNumOfPeople(
                    Integer.parseInt(this.view.populationToString()));
            int tabNum = this.model.returnNumOfPeople();

            //check if the integer is in range
            inRange = tabNum <= MAXNUM && tabNum >= 1;

            //JOptionPane.showMessageDialog(new JFrame(), "Input is: " + tabNum);

            //if the user input is within the designated range of people
            if (inRange) {

                /*
                 * add the panels to the JPanel ArrayList. Then add those panels
                 * to the JTabbedPane
                 */
                this.view.updatePanePanels(tabNum);

                //switch to page 2
                this.view.onPage1(false);
                this.view.onPage2(true);
                this.view.onPage3(false);

                //notify user of invalid integer
            } else {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Input is an invalid number. "
                                + "Try an integer between 1 and 8 inclusive");
            }
        }
    }

    @Override
    public void processBBack() {

        //initialize the current page index variable
        int currentPageIndex = this.view.returnCurrentPage();

        //if the current page is not page 1, go back 1 page
        if (currentPageIndex > 0) {
            this.view.setPageInvisible(currentPageIndex);
            this.view.setPageVisible(currentPageIndex - 1);
        }
    }

    @Override
    public void processBUpdate() {

    }

}
