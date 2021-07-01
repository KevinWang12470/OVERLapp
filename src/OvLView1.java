import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * GUI for OverLAPP.
 *
 * @author Kevin Wang.12470
 */
//@SuppressWarnings("serial")
public final class OvLView1 extends JFrame implements OvLView {

    /**
     * The controller object registered with this view to keep track of events.
     */
    private OvLController controller;

    /**
     * The number of Panels that make up the GUI.
     */
    private static final int NUMOFPAGES = 3;

    /**
     * The Panels that make up the GUI.
     */
    private final JPanel[] page;

    /**
     * Tabbed pane that makes up the user info.
     */
    private final JTabbedPane userInfoPane;

    /**
     * JPanel Array list whose size is the same as the population. The panels,
     * which contain inputed user info, go into each tabbed pane.
     */
    private ArrayList<JPanel> userInfoPanels, userEventPanels;

    /**
     * Update buttons for each person involved.
     */
    private final JButton bUpdate;

    /**
     * The Text field that contains how many people are involved.
     */
    private ArrayList<JTextField> names;

    /**
     * The text field that contains how many events each person is doing.
     */
    private ArrayList<JTextField> eventNum;

    /**
     * The Panels for each event. Separated as a list of lists of panels.
     * Imagine a table where the rows are the people and columns are the events
     * they are in.
     */
    private ArrayList<ArrayList<JPanel>> eventPanels;

    /**
     * Name JTextFields for each event.
     */
    private ArrayList<ArrayList<JTextField>> eventNames;

    /**
     * Event Type ComboBoxes for each event.
     */
    private ArrayList<ArrayList<JComboBox>> eventType;

    /**
     * Event Start Hour comboBoxes for each event.
     */
    private ArrayList<ArrayList<JComboBox>> eventStartHr;

    /**
     * Event Start Minute comboBoxes for each event.
     */
    private ArrayList<ArrayList<JComboBox>> eventStartMin;

    /**
     * Event End Hour comboBoxes for each event.
     */
    private ArrayList<ArrayList<JComboBox>> eventEndHr;

    /**
     * Event End Minute comboBoxes for each event.
     */
    private ArrayList<ArrayList<JComboBox>> eventEndMin;

    /**
     * Page switch buttons.
     */
    private final JButton bp1p2, bp2p3, bp3Done;

    /**
     * Back buttons on different pages.
     */
    private final JButton[] bpBack;

    /**
     * Range of people allowed on schedule at one time.
     */
    private static final int MAXNUM = 8;

    /**
     * Combo box that will determine the number of people on the schedule
     * together.
     */
    private final JComboBox<Integer> populationRange;

    /**
     * Constructor.
     */
    public OvLView1() {

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("OverLAPP");

        /*
         * Begin the general frame set up
         */
        this.setBounds(100, 100, 450, 300);
        this.setLayout(new CardLayout(0, 0));

        /*
         * Initialize number of pages and back buttons
         */
        this.page = new JPanel[NUMOFPAGES];
        this.bpBack = new JButton[NUMOFPAGES - 1];

        //create and add "Back Button"
        for (int i = 0; i < NUMOFPAGES - 1; i++) {
            this.bpBack[i] = new JButton("Back");
            this.bpBack[i].addActionListener(this);
        }

        /*
         * Instantiate Page 1
         */
        this.page[0] = new JPanel();

        this.page[0].setBounds(100, 100, 450, 300);
        this.page[0].setLayout(new GridLayout(3, 1));

        //Create and add instructions text for page 1
        JTextArea inputPeoplePrompt = new JTextArea(
                "Input the Number Of People in Schedule. 1 - 8");
        inputPeoplePrompt.setMargin(new Insets(30, 30, 10, 10));
        this.page[0].add(inputPeoplePrompt);

        //Initialize combobox that chooses the number of people
        Integer[] rangeForComboBox = new Integer[MAXNUM];
        for (int i = 0; i < MAXNUM; i++) {
            rangeForComboBox[i] = i + 1;
        }
        //add array into combo box
        this.populationRange = new JComboBox<>(rangeForComboBox);
        //allow for keyboard edits
        this.populationRange.setEditable(true);
        this.populationRange.addActionListener(this);
        this.page[0].add(this.populationRange);

//        //Create and add the spinner that chooses the number of people
//        SpinnerNumberModel numOfPeopleModel = new SpinnerNumberModel(1, 1, 8,
//                1);
//        this.numOfPeople = new JSpinner(numOfPeopleModel);

        //Create and add "next" button on page 1
        this.bp1p2 = new JButton("Next");
        this.bp1p2.addActionListener(this);
        this.page[0].add(this.bp1p2);

        /*
         * Instantiate Page 2
         */
        this.page[1] = new JPanel();

        //Set up grid bag layout
        this.page[1].setLayout(new GridBagLayout());
        //declare the constraints variable for use in layout
        GridBagConstraints c = new GridBagConstraints();

        //Initialize the user info pane
        this.userInfoPane = new JTabbedPane();

        //Declare the JPanels used in the tabbed pane
        this.userInfoPanels = new ArrayList<JPanel>();
        this.userEventPanels = new ArrayList<JPanel>();
        this.eventPanels = new ArrayList<ArrayList<JPanel>>();

        //Declare the JTextAreas to be used in userInfoPanels
        this.names = new ArrayList<JTextField>();
        this.eventNum = new ArrayList<JTextField>();

        JButton testButton = new JButton("userInfoPane goes here");

        //specify constraints placed on the user info pane
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500; //Large vertical length
        c.ipadx = 500; //Large horizontal length
        c.weightx = 1.0; //take all available horizontal space
        c.weighty = 1.0; //take all available vertical space
        c.gridwidth = 5; //span 3 columns
        c.gridheight = 1; //span 1 row
        c.gridx = 0; //start in far left column
        c.gridy = 0; //start in far top row
        this.page[1].add(this.userInfoPane, c);

        //create and add "Save & Continue" Button
        this.bp2p3 = new JButton("Save & Continue");
        this.bp2p3.addActionListener(this);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0; //reset the vertical size
        c.ipadx = 0;

        c.weightx = 0.2; //small increase in horizontal size
        c.weighty = 0.0; //get no extra vertical space
        c.gridwidth = 1; //width of one
        c.anchor = GridBagConstraints.LAST_LINE_END; //anchored to lower left
        c.insets = new Insets(30, 0, 30, 10); //top padding
        c.gridx = 4;
        c.gridy = 2;

        this.page[1].add(this.bp2p3, c);

        //Initialize the Back Button
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(30, 0, 30, 10);
        c.gridwidth = 1;
        c.weightx = 0.2; //for consistent column width change
        c.anchor = GridBagConstraints.LAST_LINE_START; //anchored to lower left
        c.gridx = 0;
        c.gridy = 2;
        this.page[1].add(this.bpBack[0], c);

        //Declare the update button
        this.bUpdate = new JButton("Update");
        this.bUpdate.addActionListener(this);

        c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(0, 0, 0, 0); //reset padding
        c.insets = new Insets(30, 0, 30, 10);
        c.weightx = 0.2; //for consistent column width change
        c.gridx = 2;
        c.gridy = 2;
        this.page[1].add(this.bUpdate, c);

        //Initializing buffers between buttons on bottom (back, update, save)

        JPanel buffer1 = new JPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0); //reset padding
        c.weightx = 0.3; //for consistent column width change
        c.gridx = 1;
        c.gridy = 2;
        this.page[1].add(buffer1, c);

        JPanel buffer2 = new JPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0); //reset padding
        c.weightx = 0.4; //for consistent column width change
        c.gridx = 3;
        c.gridy = 2;
        this.page[1].add(buffer2, c);

        /*
         * Instantiate Page 3
         */
        this.page[2] = new JPanel();
        this.page[2].setLayout(new GridLayout(1, 3));

        //Create and add "Create Excel" Button
        this.bp3Done = new JButton("Create Excel");
        this.bp3Done.addActionListener(this);
        this.page[2].add(this.bpBack[1]);
        this.page[2].add(this.bp3Done);

        /*
         * Finish the set up of the entire frame
         */

        //add all the pages to the frame
        this.add(this.page[0]);
        this.add(this.page[1]);
        this.add(this.page[2]);

        //set the initial visibility of the pages
        this.page[0].setVisible(true);
        this.page[1].setVisible(false);
        this.page[2].setVisible(false);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = event.getSource();

        //first button
        if (source == this.bp1p2) {

//            boolean inRange = true;
//            boolean isInt = true;
//
////            try {
////                this.numOfPeople.commitEdit();
////            } catch (java.text.ParseException e) {
////                JOptionPane.showMessageDialog(new JFrame(),
////                        "There was a parsing problem...");
////
////            }
//
//            //set result to string
//            String tempString = this.populationRange.getSelectedItem()
//                    .toString();
//
//            //Check if input is an integer
//            try {
//                Integer.parseInt(tempString);
//            } catch (NumberFormatException e) {
//                isInt = false;
//                //notify user
//                JOptionPane.showMessageDialog(new JFrame(),
//                        "Input is not a valid number");
//            }
//
//            //if input is an integer, continue
//            if (isInt) {
//
//                //get the integer
//                /*
//                 * Must do integer.parseInt after toString because if user
//                 * inputs a string, then inputs a valid integer, the program
//                 * will parse the number as a string instead of an int. Thus
//                 * must run to string then parseInt
//                 */
//                int tabNum = Integer.parseInt(
//                        this.populationRange.getSelectedItem().toString());
//
//                //check if the integer is in range
//                inRange = tabNum <= MAXNUM && tabNum >= 1;
//
//                JOptionPane.showMessageDialog(new JFrame(),
//                        "Input is: " + tabNum);
//
//                //if the user input is within the designated range of people
//                if (inRange) {
//
//                    /*
//                     * add the panels to the JPanel ArrayList. Then add those
//                     * panels to the JTabbedPane
//                     */
//                    this.addPanelsToPane(tabNum);
//
//                    //switch to page 2
//                    this.page[0].setVisible(false);
//                    this.page[1].setVisible(true);
//                    this.page[2].setVisible(false);
//
//                    //notify user of invalid integer
//                } else {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            "Input is an invalid number. "
//                                    + "Try an integer between 1 and 8 inclusive");
//                }
//            }

            this.controller.processBp1();

            //second button
        } else if (source == this.bp2p3) {
            this.page[0].setVisible(false);
            this.page[1].setVisible(false);
            this.page[2].setVisible(true);
        } else if (source == this.bpBack[0] || source == this.bpBack[1]) {
            this.controller.processBBack();
        } else if (source == this.bUpdate) {
            this.controller.processBUpdate();
        }

        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());

    }

    @Override
    public void updatePanePanels(int numOfP) {

//        if(userInfoPanels)

        /*
         * TODO: Add memory of already filled in tabs instead of clearing all
         * panels
         */
        //Clear arrays if necessary
        this.userInfoPanels.clear();
        this.userEventPanels.clear();
        this.names.clear();
        this.eventNum.clear();

        this.userInfoPane.removeAll();

        for (int i = 0; i < numOfP; i++) {

            /*
             * Create blank elements in JPanel arraylist that will go into each
             * JTabbedPane
             */
            this.userInfoPanels.add(new JPanel());
            this.userEventPanels.add(new JPanel());

            /*
             * Create blank fields in JTextField arraylist that will be the name
             * input field
             */
            this.names.add(new JTextField(15));
            this.eventNum.add(new JTextField(15));
            //this.bUpdate.add(new JButton("Update"));

            //add name text fields into JPanels
            this.userInfoPanels.get(i).add(new JLabel("Name"));
            this.userInfoPanels.get(i).add(this.names.get(i));
            this.userInfoPanels.get(i).add(new JLabel("Number of Events"));
            this.userInfoPanels.get(i).add(this.eventNum.get(i));
            //this.userInfoPanels.get(i).add(this.bUpdate.get(i));

            //put JPanel into designated tabbed pane slot
            this.userInfoPane.addTab("Person " + (i + 1),
                    this.userInfoPanels.get(i));
        }

    }

    @Override
    public void updateTPaneValues() {

    }

    @Override
    public String[] recordUserNames() {
        String[] nameList = new String[this.userInfoPanels.size()];

        for (int i = 0; i < this.userInfoPanels.size(); i++) {
            nameList[i] = this.names.get(i).getText();
        }
        return nameList;
    }

    @Override
    public void updateUserNames(String[] nameList) {
        for (int i = 0; i < nameList.length; i++) {
            this.names.get(i).setText(nameList[i]);
        }
    }

    @Override
    public void updateUserEventNum(int[] eventNumList) {
        for (int i = 0; i < eventNumList.length; i++) {
            this.eventNum.get(i).setText(Integer.toString(eventNumList[i]));
        }
    }

    @Override
    public String[] recordUserEventNum() {

        String[] eventNumList = new String[this.userInfoPanels.size()];

        for (int i = 0; i < this.userInfoPanels.size(); i++) {
            eventNumList[i] = this.eventNum.get(i).getText();
        }

        return eventNumList;
    }

    @Override
    public void registerObserver(OvLController controller) {

        this.controller = controller;
    }

    @Override
    public void onPage1(boolean onPage) {

        this.page[0].setVisible(onPage);

    }

    @Override
    public void onPage2(boolean onPage) {

        this.page[1].setVisible(onPage);

    }

    @Override
    public void onPage3(boolean onPage) {

        this.page[2].setVisible(onPage);

    }

    @Override
    public String populationToString() {
        String output = this.populationRange.getSelectedItem().toString();

        return output;
    }

    @Override
    public int returnCurrentPage() {

        //Initialize return variable
        int pageIndex = -1;

        //iterate through all pages, check which one is visible
        for (int i = 0; i < NUMOFPAGES; i++) {
            if (this.page[i].isVisible()) {
                pageIndex = i;
                break;
            }
        }
        assert pageIndex > 0 : "Violation of pageIndex >= 0";
        return pageIndex;
    }

    @Override
    public void setPageVisible(int pageIndex) {

        this.page[pageIndex].setVisible(true);
    }

    @Override
    public void setPageInvisible(int pageIndex) {

        this.page[pageIndex].setVisible(false);

    }

    /**
     * Main method.
     */
    public static void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    OvLView1 window = new OvLView1();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
