import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Kevin Wang.12470
 *
 */
public final class ExcelMakerConsole {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ExcelMakerConsole() {
    }

    /**
     * Array of the name of class types.
     */
    private static final String[] CLASSTYPENAMES = { "Lecture", "Recitation",
            "Lab" };

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
    private static final short[] COLORS = { IndexedColors.LIGHT_BLUE.getIndex(),
            IndexedColors.LIGHT_GREEN.getIndex(),
            IndexedColors.LAVENDER.getIndex() };

    /**
     * Constant that holds the number of class types.
     */
    private static final int NUMOFCLASSTYPES = 3;

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
     * String of separator characters, to separate hour vs minute.
     */
    private static final String /* SEPARATORS = ";':., ", */ DIGITS = "0123456789";

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    private static void printDone() {
        /*
         * Put your code for myMethod here
         */
        System.out.println("");
        System.out.println("Done.");
    }

    /**
     * Output the name of the student as a label and the number of classes they
     * are taking as attribute.
     *
     *
     * @param name
     *            The name of the student.
     * @param classes
     *            The number of classes they are currently taking.
     * @param out
     *            The output stream.
     */
    private static void outputName(String name, int classes, SimpleWriter out) {
        out.println("\t<student name = \"" + name + "\" numberOfClasses = \""
                + classes + "\"> ");
    }

    /**
     * Outputs the name of the class that is being taken.
     *
     * @param className
     *            The name of the class being taken.
     * @param out
     *            The output stream.
     */
    private static void outputClass(String className, SimpleWriter out) {
        out.println("\t\t<class className = \"" + className + "\">");
    }

    /**
     * Prompts user for whether a class has a Lecture, Recitation, or Lab.
     *
     * @param in
     *            input stream
     * @param out
     *            output stream
     * @return a boolean array containing whether sessions exist in the class in
     *         question.
     */
    private static boolean[] checkSessionExistence(SimpleReader in,
            SimpleWriter out) {
        /*
         * declare classType boolean array. Used to determine existence of LEC
         * REC or LAB sessions per class
         */
        boolean[] sessionExists = new boolean[NUMOFCLASSTYPES];

        for (int k = 0; k < NUMOFCLASSTYPES; k++) {
            //initialize the temporary variable that holds user input
            String yesOrNo = "placeHolder";

            //keep asking if user does not input y/n
            while (!yesOrNo.equals("y") && !yesOrNo.equals("n")) {
                //prompt for input
                out.print(CLASSTYPENAMES[k] + " (y/n): ");
                //store input
                yesOrNo = in.nextLine();
                //if input is y/n, record in boolean array classType
                if (yesOrNo.equals("y") || yesOrNo.equals("n")) {
                    sessionExists[k] = (yesOrNo.equals("y"));
                } else {
                    //if input is not y/n, inform user
                    out.println("Error. Please input either (y)es or (n)o");
                }

            }
        }
        return sessionExists;
    }

    /**
     * Prompts the user for what time their class starts.
     *
     * @param in
     *            Input Stream.
     * @param out
     *            Output Stream.
     * @return user input time corresponding to the start time of the class in
     *         question
     */
    private static String getStartTime(SimpleReader in, SimpleWriter out) {
        boolean properFormat = false;
        String startTime = "placeHolder";

        /*
         * Keep asking for start time if user input is not of proper format.
         */
        while (!properFormat) {

            //prompt for start time
            out.print("Start time: ");

            //record start time
            startTime = in.nextLine();

            //check if input is in proper format
            properFormat = correctTime(startTime);

            if (!properFormat) {
                out.println();
                out.println("Sorry, cannot read the user input start time.");
                out.println("Please try again.");
            }
        }

        return startTime;
    }

    /**
     * Prompts the user for what time their class ends.
     *
     * @param in
     *            Input Stream.
     * @param out
     *            Output Stream.
     * @return user input time corresponding to the end time of the class in
     *         question
     */
    private static String getEndTime(SimpleReader in, SimpleWriter out) {
        boolean properFormat = false;
        String endTime = "placeHolder";

        /*
         * Keep asking for end time if user input is not of proper format.
         */
        while (!properFormat) {
            //prompt for end time
            out.print("End time: ");

            //record end time
            endTime = in.nextLine();

            //check if input is in proper format
            properFormat = correctTime(endTime);

            if (!properFormat) {
                out.println();
                out.println("Sorry, cannot read the user input end time.");
                out.println("Please try again.");
            }
        }

        return endTime;
    }

    /**
     * Prompts the user for where their class occurs.
     *
     * @param in
     *            Input Stream.
     * @param out
     *            Output Stream.
     * @return user input address corresponding to the location of the class in
     *         question
     */
    private static String getLocation(SimpleReader in, SimpleWriter out) {
        //prompt for end time
        out.print("Location: ");

        //record end time
        String location = in.nextLine();

        return location;
    }

    /**
     * Prompts the user for what days their class occurs on.
     *
     * @param in
     *            Input Stream
     * @param out
     *            Output Stream
     * @return A string of length 5 consisting of 1s and 0s. 1 means class
     *         occurs on the day, 0 means no class.
     */
    private static String getOccurrenceFrequency(SimpleReader in,
            SimpleWriter out) {

        //output string, contains 5 chars that are either 1 or 0
        String frequency = new String();

        String inputChar;

        /*
         * Iterate for however many school days there are.
         */
        for (int i = 0; i < SCHOOLDAYS.length; i++) {

            //initialize the variable
            inputChar = "placeHolder";

            //keep asking if user does not input y/n
            while (!inputChar.equals("y") && !inputChar.equals("n")) {
                //prompt for input
                out.print("Class on " + SCHOOLDAYS[i] + " (y/n): ");
                //store input
                inputChar = in.nextLine();
                //if input is y/n, change String frequency accordingly
                if (inputChar.equals("y")) {
                    frequency = frequency.concat("1");
                } else if (inputChar.equals("n")) {
                    frequency = frequency.concat("0");
                } else {
                    //if input is not y/n, inform user
                    out.println("Error. Please input either (y)es or (n)o");
                }

            }

        }

        return frequency;
    }

    /**
     * Outputs nodes depending on class type (LEC, REC, LAB) and specifies each
     * one with their information (start time, end time, location).
     *
     * @param type
     *            Boolean of class types, boolean true if LEC, REC, LAB are
     *            available respectively, otherwise false.
     * @param start
     *            Start time.
     * @param end
     *            End time.
     * @param location
     *            Building location.
     * @param frequency
     *            Class occurrence frequency.
     * @param out
     *            The output stream.
     */
    private static void outputClassInfo(boolean[] type, String[] start,
            String[] end, String[] location, String[] frequency,
            SimpleWriter out) {
        if (type[0]) {
            out.println("\t\t\t<LEC start = \"" + start[0] + "\" end = \""
                    + end[0] + "\" location = \"" + location[0]
                    + "\" frequency = \"" + frequency[0] + "\" />");
        }
        if (type[1]) {
            out.println("\t\t\t<REC start = \"" + start[1] + "\" end = \""
                    + end[1] + "\" location = \"" + location[1]
                    + "\" frequency = \"" + frequency[1] + "\" />");
        }
        if (type[2]) {
            out.println("\t\t\t<LAB start = \"" + start[2] + "\" end = \""
                    + end[2] + "\" location = \"" + location[2]
                    + "\" frequency = \"" + frequency[2] + "\" />");
        }

        out.println("\t\t</class>");
    }

    /**
     * Outputs the footer for the student node.
     *
     * @param out
     *            The output stream.
     */
    private static void outputStudentFooter(SimpleWriter out) {
        out.println("\t</student>");
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        /* clear the set */
        strSet.clear();
        /*
         * iterate through the string, adding characters to the set and checking
         * for redundancy
         */
        for (int i = 0; i < str.length(); i++) {
            if (!strSet.contains(str.charAt(i))) {
                strSet.add(str.charAt(i));
            }
        }

    }

    /**
     * Takes the time attribute and figures out the specific number for the hour
     * and minute.
     *
     * @param timeString
     *            the time of the class (start or end)
     *
     *            param out output stream.
     *
     *
     * @return an int array of length 2 that shows the hour, then the minute
     */
    public static boolean correctTime(
            String timeString/* , SimpleWriter out */) {
        assert timeString != null : "Violation of: text is not null";
        int[] timeArray = new int[2];

        int substringIndex = 0;
        int intsDetected = 0;
        boolean correctFormat = true;

        //changing to lowercase for when checking am pm
        String lowerCaseTime = timeString.toLowerCase();

        /*
         * declare and initialize digit number set to ensure just one of each
         * character
         */
        Set<Character> digitSet = new Set1L<>();
        generateElements(DIGITS, digitSet);

        assert digitSet != null : "Violation of: digits is not null";

        /*
         * Keep iterating until at the end of the string
         */
        while (substringIndex < lowerCaseTime
                .length() /* && intsDetected < 2 */) {

            //temp string that will hold either hour or minute.
            String hrMinComponent = new String();
            /*
             * If the "starting" character is not a digit
             */
            if (!digitSet.contains(lowerCaseTime.charAt(substringIndex))) {

                /*
                 * iterate through the string until a character is a digit.
                 */
                while (substringIndex < lowerCaseTime.length() && !digitSet
                        .contains(lowerCaseTime.charAt(substringIndex))) {

                    //keep track of substring index
                    substringIndex++;
                }
            } else {
                /*
                 * otherwise, iterate through the string and record until the
                 * character is not a digit
                 */
                while (substringIndex < lowerCaseTime.length() && digitSet
                        .contains(lowerCaseTime.charAt(substringIndex))) {

                    //add the current character to the temp String
                    hrMinComponent = hrMinComponent.concat(lowerCaseTime
                            .substring(substringIndex, substringIndex + 1));

                    //keep track of substring index
                    substringIndex++;
                }
                //keep track of how many instances of numbers have occurred.
                intsDetected++;

//                out.println("ints detected in loop: " + intsDetected);

                /*
                 * Convert hrMinComponent to an int, then record the result to
                 * timeArray only if there are less than 3 ints detected.
                 */

                if (intsDetected <= 2) {
                    timeArray[intsDetected - 1] = Integer
                            .parseInt(hrMinComponent);
                }

                /*
                 * Ensure proper conversion between 12hr and 24hr.
                 */
                if (lowerCaseTime.endsWith("pm") && intsDetected == 1
                        && !(timeArray[0] == TWELVE)) {
                    timeArray[0] += TWELVE;
                } else if (lowerCaseTime.endsWith("am") && intsDetected == 1
                        && (timeArray[0] == TWELVE)) {
                    timeArray[0] = 0;
                }

            }

        }

//        out.println("hr " + timeArray[0]);
//        out.println("min" + timeArray[1]);
        /*
         * Check for the correct time format.
         */
        //0 <= Hour < 24
        //0 <= Minute < 60
        //only two instances of integers in user input
        correctFormat = timeArray[0] >= 0 && timeArray[0] < TWELVE * 2
                && timeArray[1] >= 0 && timeArray[1] < SIXTY
                && intsDetected == 2;

//        out.println("intsDetected = " + intsDetected);

        return correctFormat;
    }

    /**
     * Takes the time attribute and figures out the specific number for the hour
     * and minute.
     *
     * @param timeString
     *            the time of the class (start or end)
     *
     * @return an int array of length 2 that shows the hour, then the minute
     */
    public static int[] parseTime(String timeString) {
        assert timeString != null : "Violation of: text is not null";
        int[] timeArray = new int[2];

        int substringIndex = 0;
        int intsDetected = 0;

        //changing to lowercase for when checking am pm
        String lowerCaseTime = timeString.toLowerCase();

        /*
         * declare and initialize separactor character set to ensure just one of
         * each character
         */
        Set<Character> digitSet = new Set1L<>();
        generateElements(DIGITS, digitSet);

        assert digitSet != null : "Violation of: digits is not null";

        /*
         * Keep iterating until at the end of the string
         */
        while (substringIndex < lowerCaseTime
                .length()/* && intsDetected < 2 */) {

            //temp string that will hold either hour or minute.
            String hrMinComponent = new String();
            /*
             * If the "starting" character is not a digit
             */
            if (!digitSet.contains(lowerCaseTime.charAt(substringIndex))) {

                /*
                 * iterate through the string until a character is a digit.
                 */
                while (substringIndex < lowerCaseTime.length() && !digitSet
                        .contains(lowerCaseTime.charAt(substringIndex))) {

                    //keep track of substring index
                    substringIndex++;
                }
            } else {
                /*
                 * otherwise, iterate through the string and record until the
                 * character is not a digit
                 */
                while (substringIndex < lowerCaseTime.length() && digitSet
                        .contains(lowerCaseTime.charAt(substringIndex))) {

                    //add the current character to the temp String
                    hrMinComponent = hrMinComponent.concat(lowerCaseTime
                            .substring(substringIndex, substringIndex + 1));

                    //keep track of substring index
                    substringIndex++;
                }
                //keep track of how many instances of numbers have occured.
                intsDetected++;

                /*
                 * Convert hrMinComponent to an int, then record the result to
                 * timeArray only if there are less than 3 ints detected.
                 */
                if (intsDetected <= 2) {
                    timeArray[intsDetected - 1] = Integer
                            .parseInt(hrMinComponent);
                }
                /*
                 * Ensure proper conversion between 12hr and 24hr.
                 */
                if (lowerCaseTime.endsWith("pm") && intsDetected == 1
                        && !(timeArray[0] == TWELVE)) {
                    timeArray[0] += TWELVE;
                } else if (lowerCaseTime.endsWith("am") && intsDetected == 1
                        && (timeArray[0] == TWELVE)) {
                    timeArray[0] = 0;
                }

            }

        }

        return timeArray;
    }

    /**
     * Prompts the user on the name of the file.
     *
     * @param in
     *            input stream
     * @param out
     *            output stream
     * @return a String to be used as the file name of the schedule
     */
    private static String promptSheetName(SimpleReader in, SimpleWriter out) {

        boolean maySubmit = false;
        String fileName = null;
        String userInput = "placeholder";

        out.println("");

        /*
         * Gets user input
         */
        while (!maySubmit) {

            //prompt for 12 hour or 24 hour time
            out.print("Input a name for the group schedule: ");

            //read and record user input
            userInput = in.nextLine();

            //check for proper file type suffix
            if (userInput.endsWith(".xlsx")) {

                while (!(userInput.equals("0") || userInput.equals("1"))) {
                    //prompt to continue
                    fileName = userInput;
                    out.print("Finish (1) or Redo Name (0): ");
                    userInput = in.nextLine();

                    //in case user does not input 1 or 0
                    if (!(userInput.equals("0") || userInput.equals("1"))) {
                        out.println();
                        out.println();
                        out.println(
                                "Please input either (1) to continue or (0) to");
                        out.println("change the file name **No parenthesis**");
                        out.println();
                        out.println();
                    } else {
                        maySubmit = userInput.equals("1");
                    }
                }
            } else {
                out.println();
                out.println("Please end the name of the file with \".xlsx\"");
                out.println();
            }
        }

        return fileName;

    }

    /**
     * Prompts the user on whether to have a 7 or 5 day week.
     *
     * @param in
     *            input stream
     * @param out
     *            output stream
     * @return boolean, 7 day week if true, 5 day week if false
     */
    private static boolean promptWeekFormat(SimpleReader in, SimpleWriter out) {

        //
        boolean sevenDays;
        String userInput = "placeholder";

        out.println("");

        /*
         * Gets user input
         */
        while (!(userInput.equals("0") || userInput.equals("1"))) {

            //prompt for 7 or 5 day week
            out.print(
                    "Use 7 day week (1) or 5 day week (0) format on the schedule: ");

            //read and record user input
            userInput = in.nextLine();

            //in case user does not input 1 or 0
            if (!(userInput.equals("0") || userInput.equals("1"))) {
                out.println();
                out.println("Please input either (1) for 7 day week format");
                out.println("or (0) for 5 day week format **No parenthesis**");
                out.println();
            }
        }
        sevenDays = userInput.equals("1");

        return sevenDays;
    }

    /**
     * Generates the day row with a full seven day week.
     *
     * @param file
     *            The workbook being worked in. The actual excel file
     * @param sheet
     *            The specific sheet or page in the file being worked on
     * @param sevenDays
     *            Boolean, if true, utilize 7 day week format, if false, utilize
     *            5 day week format
     * @param numOfStudents
     *            The number of students included in the schedule.
     * @param allStyles
     *            CellStyle array with all usable style templates
     */
    private static void generateWeekRow(Workbook file, Sheet sheet,
            boolean sevenDays, int numOfStudents, CellStyle[] allStyles) {

        //start column B
        int startColumn = 1;
        int endColumn = startColumn + numOfStudents - 1;
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(startColumn);

        /*
         * Create variable dayFormat, which will alias either ALLDAYS or
         * SCHOOLDAYS depending on desire for 7 day week or 5 day week.
         */
        String[] dayFormat;
        if (sevenDays) {
            dayFormat = ALLDAYS;
        } else {
            dayFormat = SCHOOLDAYS;
        }

        for (String day : dayFormat) {

            //begin in the start column
            cell = row.createCell(startColumn);

            //set width of schedule
            for (int i = 0; i < numOfStudents; i++) {
                sheet.setColumnWidth(startColumn + i, COLUMNWIDTH);
            }

            //put in the name of the day of the week
            cell.setCellValue(day);
            //set the style of the merged cell
            cell.setCellStyle(allStyles[BASICTEMPLATE]);

            //specify the range in which to merge the cells
            CellRangeAddress dayColumnRange = new CellRangeAddress(0, 0,
                    startColumn, endColumn);

//            /*
//             * insert the borders between days. minus two because last row num
//             * goes two too far.
//             */
//            for (int j = 1; j < sheet.getLastRowNum() - 2; j++) {
//                Row borderRow = sheet.getRow(j);
//                Cell borderCell = borderRow.createCell(endColumn);
//                borderCell.setCellStyle(allStyles[LASTCOLUMNTEMPLATE]);
//            }

            //merge however many cells as there are students
            sheet.addMergedRegion(dayColumnRange);
            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, dayColumnRange,
                    sheet);

            //refresh the start and end columns
            startColumn = startColumn + numOfStudents;
            endColumn = endColumn + numOfStudents;
        }

    }

    /**
     * Prompts the user on info such as whether to use 24 or 12 hr time.
     *
     * @param in
     *            input stream
     * @param out
     *            output stream
     * @return boolean value, 12hr time if true, 24hr time if false
     */
    private static boolean promptHrFormat(SimpleReader in, SimpleWriter out) {

        //Variables
        boolean twelveTime;
        String userInput = "placeholder";

        out.println("");

        /*
         * Gets user input
         */
        while (!(userInput.equals("0") || userInput.equals("1"))) {

            //prompt for 12 hour or 24 hour time
            out.print(
                    "Use 12-hour time (1) or 24-hour time (0) on the schedule: ");

            //read and record user input
            userInput = in.nextLine();

            //in case user does not input 1 or 0
            if (!(userInput.equals("0") || userInput.equals("1"))) {
                out.println();
                out.println("Please input either (1) for 12 hour time");
                out.println("or (0) for 24 hour time **No parenthesis**");
                out.println();
            }
        }
        twelveTime = userInput.equals("1");

        return twelveTime;
    }

    /**
     * Prompts the user on what time frame the schedule should display.
     *
     * @param in
     *            input stream
     * @param out
     *            output stream
     * @return an int array length 2 with start hour and end hour in 24hr time
     */
    private static int[] promptTimeFrame(SimpleReader in, SimpleWriter out) {

        //initialize default time frame
        int[] timeFrame = new int[2];
        timeFrame[0] = DEFAULTTIMEFRAME[0];
        timeFrame[1] = DEFAULTTIMEFRAME[1];

        //temp variables
        String userInput = "placeholder";
        boolean selfInput;

        while (!(userInput.equals("y") || userInput.equals("n"))) {
            //prompt to use default time frame
            out.println("");
            out.println(
                    "Default schedule time frame is 6:00 AM - 10:00 PM (6:00 - 22:00)");
            out.println("Use default schedule?");
            out.print("Input (y)es or (n)o: ");
            userInput = in.nextLine();

            //in case user does not input y or n
            if (!(userInput.equals("y") || userInput.equals("n"))) {
                out.println();
                out.println(
                        "Please input either (y) to use the default time frame");
                out.println(
                        "or (n) to set your own time frame **No parenthesis**");
            }
        }
        selfInput = userInput.equals("n");

        /*
         * Keep looping until user is satisfied with their input.
         */
        while (selfInput) {

            /*
             * Loop here for start and end time prompting, using STARTEND to
             * prompt for start and end.
             */
            for (int i = 0; i < 2; i++) {

                /*
                 * Keep asking until 0 <= time < 24
                 */
                while (!FormatChecker.canParseInt(userInput)
                /*
                 * && Integer.parseInt(userInput) >= 0 &&
                 * Integer.parseInt(userInput) < TWELVE * 2
                 */) {

                    //prompt
                    out.println();
                    out.print("Input your preferred schedule " + STARTEND[i]
                            + " time in hours (0 to 23): ");
                    userInput = in.nextLine();

                    if (FormatChecker.canParseInt(userInput)
                            && Integer.parseInt(userInput) >= 0
                            && Integer.parseInt(userInput) < TWELVE * 2) {
                        /*
                         * Create variable to store parseInt userinput? or keep
                         * as is?
                         */
                        timeFrame[i] = Integer.parseInt(userInput);
                    } else {
                        out.println();
                        out.println();
                        out.println(
                                "Please input an integer that is greater than");
                        out.println("or equal to 0 and less than 24.");
                        out.println();
                        out.println();

                        /*
                         * reset user input to continue looping since time frame
                         * is incorrect
                         */
                        userInput = "placeholder";
                    }
                }
                //reset user input in order to prompt for end time
                userInput = "placeholder";
            }

            //repeat to user what their input time was
            out.println("Is a start time of " + timeFrame[0] + ":00 and an");
            out.println("end time of " + timeFrame[1] + ":00 good?");

            while (!(userInput.equals("y") || userInput.equals("n"))) {

                //prompt to continue or redo time frame
                out.println();
                out.print("Input (y)es to continue or "
                        + "(n)o to redo the schedule time frame: ");
                userInput = in.nextLine();

                //in case user does not input y or n
                if (!(userInput.equals("y") || userInput.equals("n"))) {
                    out.println();
                    out.println(
                            "Please input either (y) to continue or (n) to");
                    out.println(
                            "redo the schedule time frame **No parenthesis**");
                }
            }

            //end method or loop to redo time frame.
            selfInput = userInput.equals("n");

        }
        return timeFrame;
    }

    /**
     * Generates the time column on the left in 12 hr time.
     *
     * @param file
     *            The workbook for the time column to be generated in.
     * @param sheet
     *            The sheet for the time column to be generated in.
     * @param timeFrame
     *            time frame to build the schedule around.
     * @param allStyles
     *            An array of all the cell style templates we can use
     */
    private static void generate12HrTime(Workbook file, Sheet sheet,
            int[] timeFrame, CellStyle[] allStyles) {
        //Initialize the row and cell
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        //make some time number variables
        int startTime = timeFrame[0];
        int endTime = timeFrame[1];
        int currentRow = 1;
        //Put in some cells
        //start hour to end hour
        for (int i = startTime; i <= endTime; i++) {
            //print :00 and :30
            /*
             * if desired, all cells can show time by setting j < 4
             */
            for (int j = 0; j < 4; j++) {
                row = sheet.createRow(currentRow);
                cell = row.createCell(0);

                //ensure :00 is printed instead of :0
                if (j == 0) {

                    //set the cell style
                    cell.setCellStyle(allStyles[TIMECOLUMNTEMPLATE]);

                    //if in the 12 AM area
                    if (i == 0) {
                        cell.setCellValue("12:00AM");

                        //if PM
                    } else if (i > TWELVE) {
                        cell.setCellValue(i - TWELVE + ":00PM");

                        //if 12PM
                    } else if (i == TWELVE) {
                        cell.setCellValue(i + ":00PM");

                        /* if AM */
                    } else {
                        cell.setCellValue(i + ":00AM");
                    }

                    /* ensures time column ends at end:00 */
                } else if (i != endTime && j == 2) {

                    //set the cell style
                    cell.setCellStyle(allStyles[TIMECOLUMNTEMPLATE]);

                    //in this format in case all cells show time (j * FIFTEEN)
                    //if 12 AM
                    if (i == 0) {
                        cell.setCellValue("12:" + THIRTY + "AM");

                        //if PM
                    } else if (i > TWELVE) {
                        cell.setCellValue(i - TWELVE + ":" + THIRTY + "PM");

                        //if 12PM
                    } else if (i == TWELVE) {
                        cell.setCellValue(i + ":" + THIRTY + "PM");

                        /* if AM */
                    } else {
                        cell.setCellValue(i + ":" + THIRTY + "AM");
                    }

                    //if :15 or :45. Make a blank cell
                } else if (i != endTime) {
                    cell.setCellStyle(allStyles[ALTTIMECOLUMNTEMPLATE]);
                    cell.setCellValue(" ");
                }

                /*
                 * move down the sheet according to the timeline. Each cell
                 * counts as 15 minutes. Doing currentRow++ in order to create
                 * blank cells. Pertinent when adding the actual classes to the
                 * blank schedule. We'll be able to use getRow instead of
                 * createRow (which would delete everything else in that row).
                 */
                currentRow++;

            }
        }
    }

    /**
     * Generates the time column on the left in 12 hr time.
     *
     * @param file
     *            The workbook for the time column to be generated in.
     * @param sheet
     *            The sheet for the time column to be generated in.
     * @param timeFrame
     *            time frame to build the schedule around
     * @param allStyles
     *            An array of all the cell style templates we can use
     */
    private static void generate24HrTime(Workbook file, Sheet sheet,
            int[] timeFrame, CellStyle[] allStyles) {
        //Initialize the row and cell
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        //make some time number variables
        int startTime = timeFrame[0];
        int endTime = timeFrame[1];
        int currentRow = 1;
        for (int i = startTime; i <= endTime; i++) {
            //print :00 and :30
            /*
             * if desired, all cells can show time by setting j < 4
             */
            for (int j = 0; j < 4; j++) {
                row = sheet.createRow(currentRow);
                cell = row.createCell(0);
                cell.setCellStyle(allStyles[TIMECOLUMNTEMPLATE]);

                //ensure :00 is printed instead of :0
                if (j == 0) {
                    cell.setCellValue(i + ":00");

                    /* ensures time column ends at end:00 */
                } else if (i != endTime) {

                    if (j == 2) {
                        //in this format in case all cells show time (j * FIFTEEN)
                        cell.setCellValue(i + ":" + THIRTY);
                    } else {
                        cell.setCellValue(" ");
                        cell.setCellStyle(allStyles[ALTTIMECOLUMNTEMPLATE]);
                    }
                }
                /*
                 * move down the sheet according to the timeline. Each cell
                 * counts as 15 minutes. Doing currentRow++ in order to create
                 * blank cells. Pertinent when adding the actual classes to the
                 * blank schedule. We'll be able to use getRow instead of
                 * createRow (which would delete everything else in that row).
                 */
                currentRow++;
            }
        }
    }

    /**
     * Spruces up the sheet template with the styles for the schedule.
     *
     * @param file
     *            The workbook being worked in. In other words, the excel file
     *            being worked in
     * @param sheet
     *            The sheet being worked in.
     * @param basicStyle
     *            The basic style that all cells will follow.
     * @return an array of all the cell styles to be used.
     */
    private static CellStyle[] generateStyle(Workbook file, Sheet sheet,
            CellStyle basicStyle) {

        //Create style for time column
        CellStyle altTimeColumnStyle = file.createCellStyle();
        altTimeColumnStyle.cloneStyleFrom(basicStyle);
        altTimeColumnStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        altTimeColumnStyle.setBorderRight(BorderStyle.MEDIUM);
        altTimeColumnStyle.setBorderLeft(BorderStyle.MEDIUM);
        altTimeColumnStyle
                .setFillForegroundColor(IndexedColors.WHITE.getIndex());

        CellStyle timeColumnStyle = file.createCellStyle();
        timeColumnStyle.cloneStyleFrom(altTimeColumnStyle);
        timeColumnStyle.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());

        //Create style for last column per day (to add vertical bar)
        CellStyle lastColumnStyle = file.createCellStyle();
        lastColumnStyle.cloneStyleFrom(basicStyle);
        lastColumnStyle.setBorderRight(BorderStyle.MEDIUM);

        //Create style template for class session regions
        CellStyle sessionStyleTemplate = file.createCellStyle();
        sessionStyleTemplate.cloneStyleFrom(basicStyle);
        sessionStyleTemplate.setVerticalAlignment(VerticalAlignment.CENTER);
        sessionStyleTemplate.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Initialize CellStyle array return variable
        CellStyle[] allStyles = new CellStyle[] { basicStyle, timeColumnStyle,
                lastColumnStyle, sessionStyleTemplate, altTimeColumnStyle };

        return allStyles;

    }

    /**
     * Creates the black sheet that will contain the group schedule.
     *
     * @param xmlToRead
     *            The name of the xml file that contains the information to be
     *            put on the schedule
     * @param fileName
     *            name of the file.
     * @param satSun
     *            true if includes saturday and sunday, false if not.
     * @param hr12
     *            true if 12 hour time, false if 24 hour time.
     *
     * @param scheduleTimeFrame
     *            array of length 2, contains the start hour and end hour of the
     *            schedule to be created.
     * @param numOfStudents
     *            the number of students to be included in the group schedule
     *
     *
     */
    public static void createSheetTemplate(String xmlToRead, String fileName,
            boolean satSun,

            boolean hr12, int[] scheduleTimeFrame, int numOfStudents)
            throws Exception {

        //TODO: increase frequency from 5days to 7days
        //TODO: Maybe change the colors of the sessions or something
        /*
         * TODO: do not need numOfStudents argument, can derive that from
         * xmlToRead
         */
        //Create Blank workbook
        Workbook excelFile = new XSSFWorkbook();

        //Create file system given specific name
        FileOutputStream exOut = new FileOutputStream(new File(fileName));

        //put this between exout and exout.close()
        Sheet page1 = excelFile.createSheet("Schedule Table");

        //create basic style
        CellStyle style1 = excelFile.createCellStyle();
        style1.setWrapText(true);
        style1.setAlignment(HorizontalAlignment.CENTER);

        CellStyle[] allStyles = generateStyle(excelFile, page1, style1);

        if (hr12) {
            generate12HrTime(excelFile, page1, scheduleTimeFrame, allStyles);
        } else {
            generate24HrTime(excelFile, page1, scheduleTimeFrame, allStyles);
        }

        generateWeekRow(excelFile, page1, satSun, numOfStudents, allStyles);

        markDayBorders(excelFile, page1, satSun, numOfStudents,
                scheduleTimeFrame, allStyles);

        addClassToSchedule(xmlToRead, excelFile, page1, numOfStudents, satSun,
                scheduleTimeFrame, allStyles);

        //close streams
        excelFile.write(exOut);
        excelFile.close();

    }

    /**
     * Takes the time attribute and figures out where on the table (in rows) to
     * put the cell.
     *
     * @param classTime
     *            the time of the class (start or end)
     * @param scheduleTimeFrame
     *            The time frame that the schedule follows
     * @return the row number of that time slot
     */
    private static int convertTimeToRow(String classTime,
            int[] scheduleTimeFrame) {
        int rowIndex = 1;
        int[] classTimeFrame = parseTime(classTime);

        /*
         * calculate row index
         */
        //class start hour - schedule start hour * (cells in one hour block)
        rowIndex = 1 + (classTimeFrame[0] - scheduleTimeFrame[0]) * 4;

        /*
         * calculate row index, class start minute versus :00, :15, :30, :45
         */
        // -7 <= time < +8

        //:15
        if (classTimeFrame[1] >= 8 && classTimeFrame[1] < 22) {
            rowIndex++;
        } else if (classTimeFrame[1] >= 22 && classTimeFrame[1] < 38) {
            //:30
            rowIndex = rowIndex + 2;
        } else if (classTimeFrame[1] >= 38 && classTimeFrame[1] < 52) {
            //:45
            rowIndex = rowIndex + 3;
        } else if (classTimeFrame[1] >= 52 && classTimeFrame[1] < 60) {
            // +1:00
            rowIndex = rowIndex + 4;
        }

        return rowIndex;
    }

    /**
     * Puts down the border between the days, {monday | tuesday | wednesday |
     * etc.}.
     *
     * @param file
     *            The Workbook being written in. Also known as the excel file
     * @param sheet
     *            The specific sheet being worked on.
     *
     * @param sevenDays
     *            A boolean, if true, it's a 7 day week, if false it's a 5 day
     *            week
     * @param numOfPeople
     *            The number of people in the schedule
     * @param scheduleTimeFrame
     *            The int array that shows the time frame that the schedule
     *            follows.
     * @param allStyles
     *            The array of style template available for use
     */
    private static void markDayBorders(Workbook file, Sheet sheet,
            boolean sevenDays, int numOfPeople, int[] scheduleTimeFrame,
            CellStyle[] allStyles) {

        //Initialize the start row variable
        int startRow = 1;

        //get the end frame of the schedule
        String scheduleEndTime = scheduleTimeFrame[1] + ":00";

        //Initialize the end row variable
        int endRow = convertTimeToRow(scheduleEndTime, scheduleTimeFrame);

        //determine whether 7 day week or 5 day week
        int daysOfTheWeek = SCHOOLDAYS.length;

        if (sevenDays) {
            daysOfTheWeek = ALLDAYS.length;
        }

        for (int i = 0; i < daysOfTheWeek; i++) {

            //update the current column AKA current day
            int currentColumn = numOfPeople + i * numOfPeople;

            /*
             * Update the range of the border, which spans from the row that the
             * time column beings to the row that the time column terminates.
             */
            CellRangeAddress borderLength = new CellRangeAddress(startRow,
                    endRow, currentColumn, currentColumn);

            //set the right border to medium
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, borderLength, sheet);
        }

        //For some reason cell C3 does not fill in its right border
        //TODO: STILL DOESN'T FILL BORDER
//        RegionUtil.setBorderRight(BorderStyle.MEDIUM,
//                new CellRangeAddress(2, 2, 2, 2), sheet);

    }

    /**
     * Adds a single(?) class to the schedule.
     *
     * @param xmlToRead
     *            The xml file that is to be read.
     * @param file
     *            The workbook being written, also known as the excel file
     * @param sheet
     *            The specific sheet that is being worked on
     * @param numOfPeople
     *            number of students in the schedule
     * @param sevenDays
     *            boolean, if true, 7 day week, if false, 5 day week
     * @param scheduleTimeFrame
     *            int array of the time frame that the schedule follows
     * @param allStyles
     *            The array of all the cellstyle templates we can use
     */
    private static void addClassToSchedule(String xmlToRead, Workbook file,
            Sheet sheet, int numOfPeople, boolean sevenDays,
            int[] scheduleTimeFrame, CellStyle[] allStyles) {

        //declare excel file navigation variables
        Row startRow = sheet.getRow(1);
        Cell startCell = startRow.createCell(1);

        Row endRow = sheet.getRow(2);
        Cell endCell = endRow.createCell(2);

        //initialize the xml file to be read
        String root = xmlToRead;
        XMLTree rootNode = new XMLTree1(root);

        CellStyle[] sessionColorStyles = new CellStyle[numOfPeople];

        //iterate through the number of people
        for (int i = 0; i < numOfPeople; i++) {

            sessionColorStyles[i] = file.createCellStyle();
            sessionColorStyles[i].cloneStyleFrom(allStyles[SESSIONTEMPLATE]);
            sessionColorStyles[i].setFillForegroundColor(COLORS[i]);

            //iterate through all the classes of each person
            for (int j = 0; j < rootNode.child(i).numberOfChildren(); j++) {

                //iterate through each session available per class
                for (int k = 0; k < rootNode.child(i).child(j)
                        .numberOfChildren(); k++) {

                    //set start time row
                    startRow = sheet
                            .getRow(convertTimeToRow(
                                    rootNode.child(i).child(j).child(k)
                                            .attributeValue("start"),
                                    scheduleTimeFrame));
                    //set end time row
                    endRow = sheet
                            .getRow(convertTimeToRow(
                                    rootNode.child(i).child(j).child(k)
                                            .attributeValue("end"),
                                    scheduleTimeFrame) - 1);

                    //get class name
                    String className = rootNode.child(i).child(j)
                            .attributeValue("className");

                    //get class type
                    String classType = rootNode.child(i).child(j).child(k)
                            .label();

                    //get class start time
                    String startTime = rootNode.child(i).child(j).child(k)
                            .attributeValue("start");

                    //get class end time
                    String endTime = rootNode.child(i).child(j).child(k)
                            .attributeValue("end");

                    //get class location
                    String location = rootNode.child(i).child(j).child(k)
                            .attributeValue("location");

                    //get class frequency
                    String frequency = rootNode.child(i).child(j).child(k)
                            .attributeValue("frequency");

                    //iterate for every day of the week
                    for (int l = 0; l < frequency.length(); l++) {

                        //if class occurs that day, input info
                        if (frequency.substring(l, l + 1).equals("1")) {

                            //set starting cell
                            startCell = startRow
                                    .createCell(1 + i + (l * numOfPeople));

                            //set ending cell
                            endCell = endRow
                                    .createCell(1 + i + (l * numOfPeople));

                            /* set the cell style */
                            startCell.setCellStyle(sessionColorStyles[i]);

                            /*
                             * Write information into desired cell
                             */
                            startCell.setCellValue(className + "  " + classType
                                    + "  " + startTime + " - " + endTime + "  "
                                    + location);

                            /*
                             * Add in the merged region of the class
                             */
                            CellRangeAddress classSessionRegion = new CellRangeAddress(
                                    startRow.getRowNum(), endRow.getRowNum(),
                                    startCell.getColumnIndex(),
                                    endCell.getColumnIndex());
                            sheet.addMergedRegion(classSessionRegion);

                            //add borders
                            RegionUtil.setBorderTop(BorderStyle.THIN,
                                    classSessionRegion, sheet);
                            RegionUtil.setBorderBottom(BorderStyle.THIN,
                                    classSessionRegion, sheet);

                            //if on farthest left column per day,
                            if (i == 0) {

                                //set left border bolder
                                RegionUtil.setBorderLeft(BorderStyle.MEDIUM,
                                        classSessionRegion, sheet);

                                //if not on farthest left column per day,
                            } else {

                                //set left border thin
                                RegionUtil.setBorderLeft(BorderStyle.THIN,
                                        classSessionRegion, sheet);
                            }

                            //if on border column,
                            if (i == numOfPeople - 1) {

                                //set right border bolder
                                RegionUtil.setBorderRight(BorderStyle.MEDIUM,
                                        classSessionRegion, sheet);

                                //if not far right column per day
                            } else {

                                //set right border thin
                                RegionUtil.setBorderRight(BorderStyle.THIN,
                                        classSessionRegion, sheet);
                            }

                        }

                    }

                }

            }

        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) throws Exception {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //name of file, also name of root node
        String root = "actualSchedule.xml";
        SimpleWriter fileOut = new SimpleWriter1L(root);

        /*
         * Output root node.
         */
        fileOut.println("<" + root + ">");

        /*
         * Prompt for and record the number of names
         */
        out.print("Number of People: ");
        int numOfPeople = in.nextInteger();

        assert numOfPeople > 0 : "Violation of: Number of People must be greater than 0";

        String[] namesList = new String[numOfPeople];

        out.println("");
        out.println("");

        /*
         * Record the names
         */
        for (int i = 0; i < numOfPeople; i++) {
            out.print("Name " + (i + 1) + ": ");
            namesList[i] = in.nextLine();
        }

        out.println("");
        out.println("");

        int[] classNumList = new int[numOfPeople];
        for (int j = 0; j < numOfPeople; j++) {
            out.println(namesList[j]);
            out.print("Number of classes currently taking: ");
            classNumList[j] = in.nextInteger();

            out.println("");
            out.println("");

        }

        /*
         * Record info, iterate through all students
         */
        for (int i = 0; i < numOfPeople; i++) {
            out.println();
            out.println();
            out.println(namesList[i]);
            outputName(namesList[i], classNumList[i], fileOut);

            /*
             * prompt for class info, iterate for however many classes were
             * specified.
             */
            for (int j = 0; j < classNumList[i]; j++) {
                out.println();
                out.print("Class " + (j + 1) + ": ");

                //set temp String equal to the user input class name
                String className = in.nextLine();

                //output the class name as a tag to the xml schedule file
                outputClass(className, fileOut);

                out.println();

                /*
                 * Record which sessions exist in the class in question. Store
                 * to classType boolean array.
                 */
                boolean[] classType = checkSessionExistence(in, out);

                /*
                 * Arrays used to store user info on classes. These will later
                 * be fed into the outputClassInfo function.
                 */
                String[] start = new String[NUMOFCLASSTYPES];
                String[] end = new String[NUMOFCLASSTYPES];
                String[] location = new String[NUMOFCLASSTYPES];
                String[] occurrenceFrequency = new String[NUMOFCLASSTYPES];

                for (int l = 0; l < NUMOFCLASSTYPES; l++) {
                    //if the session exists for this class, prompt and record
                    if (classType[l]) {
                        out.println();
                        out.println(CLASSTYPENAMES[l]);
                        start[l] = getStartTime(in, out);
                        end[l] = getEndTime(in, out);
                        location[l] = getLocation(in, out);
                        occurrenceFrequency[l] = getOccurrenceFrequency(in,
                                out);
                    } else {
                        //If session does not exist for this class, input null values
                        start[l] = "N/A";
                        end[l] = "N/A";
                        location[l] = "N/A";
                        occurrenceFrequency[l] = "N/A";
                    }

                }
                //print the class information in an xml file
                outputClassInfo(classType, start, end, location,
                        occurrenceFrequency, fileOut);
            }
            //print the footer of the student tag
            outputStudentFooter(fileOut);
        }

        /*
         * output root footer
         */
        fileOut.println("</" + root + ">");

        String sheetName = promptSheetName(in, out);
        boolean use12Hr = promptHrFormat(in, out);
        boolean sevenDayWeek = promptWeekFormat(in, out);
        int[] scheduleTimeFrame = promptTimeFrame(in, out);

        createSheetTemplate("actualSchedule.xml", sheetName, use12Hr,
                sevenDayWeek, scheduleTimeFrame, numOfPeople);

        /*
         * Close input and output streams
         */
        fileOut.close();
        printDone();

        in.close();
        out.close();

    }

}
