/**
 * Created by S. Stefani on 2017-02-23.
 */
public class Main {

        public static void main(String[] args) {
            InputParser parser = new InputParser("videos_worth_spreading.in");
            parser.run();
            parser.writeOutput();
//            parser.writeStats();
        }
}
