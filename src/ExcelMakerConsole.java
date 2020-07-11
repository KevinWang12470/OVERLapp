import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

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
     * The timeframe that the table will display in 24hr.
     */
    private static final int[] SCHEDULESTART = { 6, 0 },
            SCHEDULEEND = { 22, 0 };

    /**
     * String of separator characters, to separate hour vs minute.
     */
    private static final String SEPARATORS = ";':., ", DIGITS = "0123456789";

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
     * Takes the time attribute and figures out where on the table (in rows) to
     * put the cell.
     *
     * @param classTime
     *            the time of the class (start or end)
     * @return the row number of that time slot
     */
    private static int convertTimetoRow(String classTime) {
        int rowIndex = 0;
        //TODO: probably finish after parseTime.
        return rowIndex;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //name of file, also name of root node
        String root = "xmlSchedule.xml";
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

        /*
         * Close input and output streams
         */
        fileOut.close();
        printDone();

        in.close();
        out.close();

    }

}
