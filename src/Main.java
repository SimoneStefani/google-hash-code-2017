/**
 * Main
 *
 * Google HashCode 2017
 * Created by Marcel Eschmann, Cedric Seger and Simone Stefani on 23/02/2017.
 */

public class Main {
    public static void main(String[] args) {
        String [] names = {"kittens.in", "me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in"};

        for (String file : names) {
            InputParser parser = new InputParser(file);
            parser.run();
            parser.writeOutput();
            System.out.println();
        }
    }
}
