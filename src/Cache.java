import java.util.ArrayList;

/**
 * Created by S. Stefani on 2017-02-23.
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
