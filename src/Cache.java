import java.util.ArrayList;

/**
 * Cache
 *
 * Google HashCode 2017
 * Created by Marcel Eschmann, Cedric Seger and Simone Stefani on 23/02/2017.
 */

public class Cache {
    public int capacity;
    public ArrayList<Integer> videos;

    public Cache(int capacity) {
        this.capacity = capacity;
        this.videos = new ArrayList<Integer>();
    }

    public void addVideo(int id, int size) {
        if (videos.contains(id)) { return; }
        videos.add(id);
        this.capacity -= size;
    }
}
