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
    }

}
