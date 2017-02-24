/**
 * Request
 *
 * Google HashCode 2017
 * Created by Marcel Eschmann, Cedric Seger and Simone Stefani on 23/02/2017.
 */

public class Request implements Comparable<Request> {
    public int video;
    public int endpoint;
    public int requests;
    public double gain = -1;


    @Override
    public int compareTo(Request o) {
        if (this.gain < o.gain) {
            return 1;
        } else if (this.gain == o.gain) {
            return 0;
        } else {
            return -1;
        }
    }
}
