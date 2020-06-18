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
    private static void nameInput() {
        /*
         * Put your code for myMethod here
         */
        System.out.println("");
        System.out.println("Done.");
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

//        for (int j = 0; j < numOfPeople; j++) {
//            out.println(namesList[j]);
//            out.print("Number of classes currently taking: ");
//
//        }

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
        nameInput();
        in.close();
        out.close();
    }

}
