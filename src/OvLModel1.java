import java.util.ArrayList;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Controller class.
 *
 * @author Kevin Wang.12470
 */
public final class OvLModel1 implements OvLModel {

    /**
     * The list of people involved in the schedule.
     */
    private ArrayList<Being> users;

    /**
     * Array of the name of class types. CHANGES MADE, added "Alternative" to
     * CLASSTYPENAMES, added scenario to output class, changed check session
     * existence to include alt event.
     */
    private static final String[] CLASSTYPENAMES = { "Lecture", "Recitation",
            "Lab", "Alternate Event" };

    /**
     * Array of school days.
     */
    private static final String[] SCHOOLDAYS = { "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday" };

    /**
     * Array of all days of the week.
     */
    private static final String[] ALLDAYS = { "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday" };

    /**
     * int constants that refer to their corresponding cell styles in
     * allStyles[].
     */
    private static final int BASICTEMPLATE = 0, TIMECOLUMNTEMPLATE = 1,
            LASTCOLUMNTEMPLATE = 2, SESSIONTEMPLATE = 3,
            ALTTIMECOLUMNTEMPLATE = 4;

    /**
     * Array of color indices that will be used to fill the foreground of
     * student class sessions.
     */
    private static final short[] COLORS = {
            IndexedColors.LIGHT_GREEN.getIndex(),
            IndexedColors.LIGHT_TURQUOISE.getIndex(),
            IndexedColors.CORNFLOWER_BLUE.getIndex(),
            IndexedColors.LAVENDER.getIndex(), IndexedColors.ROSE.getIndex(),
            IndexedColors.CORAL.getIndex(), IndexedColors.TAN.getIndex(),
            IndexedColors.LEMON_CHIFFON.getIndex() };

    /**
     * Constant that holds the number of class types.
     */
    private static final int NUMOFCLASSTYPES = 3;

    /**
     * Cells per hour block. 4 cells. :00 :15 :30 :45
     */
    private static final int CELLSPERHOURBLOCK = 4;

    /**
     * int constant used to convert 12hr time to 24hr time. Messy twelve.
     */
    private static final int TWELVE = 12;

    /**
     * The minute number must not reach sixty.
     */
    private static final int SIXTY = 60;

    /**
     * The half hour mark.
     */
    private static final int THIRTY = 30;

    /**
     * The Column Width equivalent to 15 characters.
     */
    private static final int COLUMNWIDTH = 18 * 256;

    /**
     * Used in promptTimeFrame method when prompting for start time and end
     * time.
     */
    private static final String[] STARTEND = { "start", "end" };

    /**
     * The timeframe that the table will display in 24hr.
     */
    private static final int[] DEFAULTTIMEFRAME = { 6, 22 };

    /**
     * If the minute is between the numbers in this array, the cell corresponds
     * to the :15, :30, :45, and +1:00 times respectively.
     */
    private static final int[] TIMEFRAMEBREAKDOWN = { 8, 22, 38, 52, 60 };

    /**
     * String of separator characters, to separate hour vs minute.
     */
    private static final String /* SEPARATORS = ";':., ", */ DIGITS = "0123456789";

    /* ------------------------------------------ */

    /**
     * Number of people to be in the schedule.
     */
    private int population;

    /**
     * Hello.
     */
    private ArrayList<ArrayList<String>> list;

    /**
     * Constructor.
     */
    public OvLModel1() {
        this.population = 1;
        this.users = new ArrayList<Being>(1);
    }

    @Override
    public void setNumOfPeople(int num) {

        this.population = num;

        //if set number is greater than arraylist size, increase arraylist size
        if (this.users.size() < this.population) {
            for (int i = 0; i < this.population - this.users.size(); i++) {
                this.users.add(new Being());
            }
        }

    }

    @Override
    public int returnNumOfPeople() {

        return this.population;

    }

}
