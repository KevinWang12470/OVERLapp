import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 * GUI for OverLAPP.
 *
 * @author Kevin Wang.12470
 */
//@SuppressWarnings("serial")
public class OvLGUI extends JFrame implements ActionListener {

//    /**
//     * The controller object registered with this view to keep track of events.
//     */
//    private OvLController controller;

    /**
     * The Panels that make up the GUI.
     */
    private final JPanel page1, page2, page3;

    /**
     * Tabbed pane that makes up the user info.
     */
    private final JTabbedPane userInfoPane;

    /**
     * JPanels that contain inputed user info.
     */
    private final ArrayList<JPanel> userInfoPanels;

    /**
     * The Text field that contains how many people are involved.
     */
    private final JSpinner numOfPeople;

    /**
     * Page switch buttons.
     */
    private final JButton bp1p2, bp2p3, bp3Done;

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
    public OvLGUI() {

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
         * Instantiate Page 1
         */
        this.page1 = new JPanel();

        this.page1.setBounds(100, 100, 450, 300);
        this.page1.setLayout(new GridLayout(3, 1));

        //Create and add instructions text for page 1
        JTextArea inputPeoplePrompt = new JTextArea(
                "Input the Number Of People in Schedule. 1 - 8");
        inputPeoplePrompt.setMargin(new Insets(30, 30, 10, 10));
        this.page1.add(inputPeoplePrompt);

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
        this.page1.add(this.populationRange);

//        //Create and add the spinner that chooses the number of people
        SpinnerNumberModel numOfPeopleModel = new SpinnerNumberModel(1, 1, 8,
                1);
        this.numOfPeople = new JSpinner(numOfPeopleModel);

        //Create and add "next" button on page 1
        this.bp1p2 = new JButton("Next");
        this.bp1p2.addActionListener(this);
        this.page1.add(this.bp1p2);

        /*
         * Instantiate Page 2
         */
        this.page2 = new JPanel();

        //Set up grid bag layout
        this.page2.setLayout(new GridBagLayout());
        //declare the constraints variable for use in layout
        GridBagConstraints c = new GridBagConstraints();

        //Initialize the user info pane
        this.userInfoPane = new JTabbedPane();

        //Declare the JPanels used in the tabbed pane
        this.userInfoPanels = new ArrayList<JPanel>();

        JButton testButton = new JButton("userInfoPane goes here");

        //specify constraints placed on the user info pane
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500; //Large vertical length
        c.ipadx = 500; //Large horizontal length
        c.weightx = 1.0; //take all available horizontal space
        c.weighty = 1.0; //take all available vertical space
        c.gridwidth = 3; //span 3 columns
        c.gridheight = 1; //span 1 row
        c.gridx = 0; //start in far left column
        c.gridy = 0; //start in far top row
        this.page2.add(this.userInfoPane, c);

        //create and add "Save & Continue" Button
        this.bp2p3 = new JButton("Save & Continue");
        this.bp2p3.addActionListener(this);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0; //reset the vertical size
        c.ipadx = 0;

        c.weightx = 0.1; //small increase in horizontal size
        c.weighty = 0.0; //get no extra vertical space
        c.gridwidth = 1; //width of one
        c.anchor = GridBagConstraints.LAST_LINE_END; //anchored to lower left
        c.insets = new Insets(30, 0, 30, 10); //top padding
        c.gridx = 2;
        c.gridy = 2;

        this.page2.add(this.bp2p3, c);

        JPanel buffer1 = new JPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0); //reset padding
        c.gridwidth = 1;
        c.weightx = 0.5; //for consistent column width change
        c.gridx = 0;
        c.gridy = 2;
        this.page2.add(buffer1, c);

        JPanel buffer2 = new JPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5; //for consistent column width change
        c.gridx = 1;
        c.gridy = 2;
        this.page2.add(buffer2, c);

        /*
         * Instantiate Page 3
         */
        this.page3 = new JPanel();

        //Create and add "Create Excel" Button
        this.bp3Done = new JButton("Create Excel");
        this.bp3Done.addActionListener(this);
        this.page3.add(this.bp3Done);

        /*
         * Finish the set up of the entire frame
         */

        //add all the pages to the frame
        this.add(this.page1);
        this.add(this.page2);
        this.add(this.page3);

        //set the initial visibility of the pages
        this.page1.setVisible(true);
        this.page2.setVisible(false);
        this.page3.setVisible(false);

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
//TODO: CATCH USER INPUT OUTSIDE SPINNER RANGE

            boolean inRange = true;
            boolean isInt = true;

            try {
                this.numOfPeople.commitEdit();
            } catch (java.text.ParseException e) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "There was a parsing problem...");

            }

            //set result to string
            String tempString = this.populationRange.getSelectedItem()
                    .toString();

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
                 * Must do integer.parseInt after toString because if user
                 * inputs a string, then inputs a valid integer, the program
                 * will parse the number as a string instead of an int. Thus
                 * must run to string then parseInt
                 */
                int tabNum = Integer.parseInt(
                        this.populationRange.getSelectedItem().toString());

                //check if the integer is in range
                inRange = tabNum <= MAXNUM && tabNum >= 1;

                JOptionPane.showMessageDialog(new JFrame(),
                        "Input is: " + tabNum);

                //if the user input is within the designated range of people
                if (inRange) {

                    /*
                     * add the panels to the JPanel ArrayList. Then add those
                     * panels to the JTabbedPane
                     */
                    this.addPanelsToPane(tabNum);

                    //switch to page 2
                    this.page1.setVisible(false);
                    this.page2.setVisible(true);
                    this.page3.setVisible(false);

                    //notify user of invalid integer
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Input is an invalid number. "
                                    + "Try an integer between 1 and 8 inclusive");
                }
            }

            //second button
        } else if (source == this.bp2p3) {
            this.page1.setVisible(false);
            this.page2.setVisible(false);
            this.page3.setVisible(true);
        } //else if (source == this.bp3Done) {
          //ExcelMakerConsole.
//        }

        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Adds the appropriate number of JPanels to the JTabbedPane on page2
     * according to user input.
     *
     * @param numOfP
     *            The number of people to be included into the schedule
     */
    private void addPanelsToPane(int numOfP) {

        for (int i = 0; i < numOfP; i++) {
            this.userInfoPanels.add(new JPanel());
            this.userInfoPane.addTab("Person " + (i + 1),
                    this.userInfoPanels.get(i));
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("OverLAPP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
//        JComponent newContentPane = new ExcelMakerGUI();
//        newContentPane.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

//    @Override
//    public void registerObserver(OvLController controller) {
//
//        this.controller = controller;
//    }

    /**
     * Main method.
     */
    public static void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                createAndShowGUI();
                try {
                    OvLGUI window = new OvLGUI();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
