import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class XMLNavigator {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private XMLNavigator() {
        // no code needed here
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        String root = "xmlSchedule.xml";
        XMLTree rootNode = new XMLTree1(root);

        out.println(
                "Number of children of Root: " + rootNode.numberOfChildren());

        out.println();
        out.println("Name of root node: " + rootNode.label());

        out.println();
        out.println("students: ");
        for (int i = 0; i < rootNode.numberOfChildren(); i++) {
            out.print(rootNode.child(i).attributeValue("name") + ", ");
        }

        out.println();
        for (int i = 0; i < rootNode.numberOfChildren(); i++) {
            out.println();
            out.println(
                    rootNode.child(i).attributeValue("name") + "'s classes: ");

            for (int j = 0; j < rootNode.child(i).numberOfChildren(); j++) {
                out.println(
                        rootNode.child(i).child(j).attributeValue("className"));
            }

        }
//TODO: find a way to print int arrays
        String timeExample = "4:10PM";

        out.println(timeExample.toLowerCase());

        if (ExcelMakerConsole.correctTime(timeExample)) {

            out.println();

            int[] timeStamp = ExcelMakerConsole.parseTime(timeExample);

            /*
             * ExcelMakerConsole.parseTime(
             * rootNode.child(0).child(0).child(0).attributeValue("start"));
             */
            out.println("Start time in 24hr time: " + timeStamp[0]
                    + " hours and " + timeStamp[1] + " minutes");
        }

//        out.println(ExcelMakerConsole.correctTime(timeExample, out));

        in.close();
        out.close();
    }

}
