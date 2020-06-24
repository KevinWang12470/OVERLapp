import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
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
     * Constant that holds the number of class types.
     */
    private static final int NUMOFCLASSTYPES = 3;

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
        out.println("<student name = \"" + name + "\" numberOfClasses = \""
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
        out.println("<class className = \"" + className + "\">");
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
     * @param out
     *            The output stream.
     */
    private static void outputClassInfo(boolean[] type, String[] start,
            String[] end, String[] location, SimpleWriter out) {
        if (type[0]) {
            out.println("<LEC start = \"" + start[0] + "\" end = \"" + end[0]
                    + "\" location = \"" + location[0] + "\"/>");
        }
        if (type[1]) {
            out.println("<REC start = \"" + start[1] + "\" end = \"" + end[1]
                    + "\" location = \"" + location[1] + "\"/>");
        }
        if (type[2]) {
            out.println("<LAB start = \"" + start[2] + "\" end = \"" + end[2]
                    + "\" location = \"" + location[2] + "\"/>");
        }

        out.println("</class>");
    }

    /**
     * Outputs the footer for the student node.
     *
     * @param out
     *            The output stream.
     */
    private static void outputStudentFooter(SimpleWriter out) {
        out.println("</student>");
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

        SimpleWriter fileOut = new SimpleWriter1L("xmlSchedule.txt");

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
                 * declare classType boolean array. Used to determine existence
                 * of LEC REC or LAB sessions per class
                 */
                boolean[] classType = new boolean[NUMOFCLASSTYPES];

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
                            classType[k] = (yesOrNo.equals("y"));
                        } else {
                            //if input is not y/n, inform user
                            out.println(
                                    "Error. Please input either (y)es or (n)o");
                        }

                    }

                }

                /*
                 * Arrays used to store user info on classes. These will later
                 * be fed into the outputClassInfo function.
                 */
                String[] start = new String[NUMOFCLASSTYPES];
                String[] end = new String[NUMOFCLASSTYPES];
                String[] location = new String[NUMOFCLASSTYPES];

                for (int l = 0; l < NUMOFCLASSTYPES; l++) {
                    //if the session exists for this class, prompt and record
                    if (classType[l]) {
                        out.println();
                        out.println(CLASSTYPENAMES[l]);
                        out.print("Start time: ");
                        start[l] = in.nextLine();
                        out.print("End time: ");
                        end[l] = in.nextLine();
                        out.print("Location: ");
                        location[l] = in.nextLine();
                    } else {
                        //If session does not exist for this class, input null values
                        start[l] = "N/A";
                        end[l] = "N/A";
                        location[l] = "N/A";
                    }

                }
                //print the class information in an xml file
                outputClassInfo(classType, start, end, location, fileOut);
            }
            //print the footer of the student tag
            outputStudentFooter(fileOut);
        }

//        out.println("You typed these names:");
//        for (String name : namesList) {
//            out.println(name);
//        }

        /*
         * Put your main program code here; it may call myMethod as shown
         */

        /*
         * Close input and output streams
         */
        printDone();
        in.close();
        out.close();
        fileOut.close();
    }

}
