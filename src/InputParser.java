/**
 * InputParser
 *
 * Google HashCode 2017
 * Created by Marcel Eschmann, Cedric Seger and Simone Stefani on 23/02/2017.
 */

import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

public class InputParser {

    /**
     * Input file
     */
    private String fileName;
    private String fileExtension;

    /**
     * Parsed input
     */
    private int numVideos, numEndopoints, numRequests, numCaches, capacity;

    private int[] videos;
    private Cache[] caches;
    private Endpoint[] endpoints;
    private PriorityQueue<Request> requests;

    /**
     * Helpers
     */
    public int ratio;
    public int notServed = 0;

    /**
     * Constructor
     * @param file
     */
    public InputParser(String file) {
        this.fileName = file.substring(0, file.lastIndexOf('.'));
        this.fileExtension = file.substring(file.lastIndexOf('.'), file.length());
        this.readInput();
    }

    /**
     * Run simulation
     */
    public void run() {

        Request[] rs;

        rs = this.requests.toArray(new Request[0]);

        for (int i = 0; i < 6; i++) {
            int counter = requests.size() / 4;

            for (int j = 0; j < rs.length; j++) {
                if (rs[j] == null) {
                    continue;
                }
                if (counter < 0) {
                    break;
                }
                counter--;
                if (videos[rs[j].video] < this.ratio) {
                    handleRequest(rs[j]);
                    rs[j] = null;
                }
            }
            this.ratio += 30;
        }

        for (int j = 0; j < rs.length; j++) {
            if (rs[j] == null) {
                continue;
            }

            handleRequest(rs[j]);
            rs[j] = null;
        }
    }

    /**
     * Parses input file
     */
    private void readInput() {
        File inputFile = new File(this.fileName + this.fileExtension);

        try {
            Scanner in = new Scanner(inputFile);

            this.numVideos = in.nextInt();
            this.numEndopoints = in.nextInt();
            this.numRequests = in.nextInt();
            this.numCaches = in.nextInt();
            this.capacity = in.nextInt();

            this.ratio = (numCaches * capacity) / numVideos;

            // CACHES
            this.caches = new Cache[this.numCaches];

            for (int i = 0; i < this.numCaches; i++) {
                this.caches[i] = new Cache(this.capacity);
            }

            // VIDEOS
            this.videos = new int[numVideos];

            for (int i = 0; i < this.numVideos; i++) {
                this.videos[i] = in.nextInt();
            }

            // ENDPOINTS
            this.endpoints = new Endpoint[this.numEndopoints];

            for (int i = 0; i < this.numEndopoints; i++) {
                Endpoint temp = new Endpoint();
                temp.latency = in.nextInt();
                int max = in.nextInt();

                temp.cachesId = new int[max];
                temp.cachesLatency = new int[max];

                for (int j = 0; j < max; j++) {
                    temp.cachesId[j] = in.nextInt();
                    temp.cachesLatency[j] = in.nextInt();
                }
                this.endpoints[i] = temp;
            }

            // REQUESTS
            this.requests = new PriorityQueue<Request>();

            for (int i = 0; i < this.numRequests; i++) {
                Request temp = new Request();
                temp.video = in.nextInt();
                temp.endpoint = in.nextInt();
                temp.requests = in.nextInt();

                int gain = endpoints[temp.endpoint].latency;


                for (int t : endpoints[temp.endpoint].cachesLatency) {
                    if (t < gain) {
                        gain = t;
                    }
                }

                temp.gain = ((endpoints[temp.endpoint].latency - gain) * temp.requests);

                this.requests.add(temp);

            }

            in.close();

        } catch (FileNotFoundException e) {
            // file not found.
            System.out.printf("[ERROR] " + e.getMessage());
        }
    }

    public void handleRequest(Request r) {
        int [] endpointCaches = endpoints[r.endpoint].cachesId;
        int [] endpointLatencies = endpoints[r.endpoint].cachesLatency;

        // If no caches available, return
        if (endpointCaches.length == 0) {
            return;
        }

        int videoSize = videos[r.video];
        int cacheResult = -1;

        // Search first suitable cache (enough size)
        for (int i = 0; i < endpointCaches.length; i++) {
            if (caches[endpointCaches[i]].capacity - videoSize > 0) {
                cacheResult = i;
                break;
            }
        }

        if (cacheResult == -1) {
            this.notServed++;
            return;
        }

        // Search for cache with best improvement on latency
        for (int j = cacheResult; j < endpointCaches.length; j++) {
            if (caches[endpointCaches[j]].capacity - videoSize > 0) {
                if (endpointLatencies[cacheResult] - endpointLatencies[j] > 0) {
                    cacheResult = j;
                }
            }
        }

        caches[endpointCaches[cacheResult]].addVideo(r.video, videoSize);

    }

    /**
     * Write output to filename.out
     */
    public void writeOutput() {
        FileWriter out;
        File outputFile = new File(this.fileName + ".out");

        try {
            out = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(out);

            // write the total number of caches
            // replace with correct value if some caches are not used
            bw.write(String.valueOf(numCaches));
            bw.newLine();

            int counter = 0;

            // write output here
            for (int i = 0; i < this.numCaches; i++) {
                if (caches[i].capacity == this.capacity) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                for (int id : caches[i].videos) {
                    sb.append(" " + id);
                }

                bw.write(sb.toString());
                bw.newLine();

                counter++;
            }

            System.out.println("Caches used: " + counter);
            System.out.println(notServed + " requests not served");

            bw.flush();
            bw.close();

        } catch (IOException e) {
            // file not found.
            System.out.printf("[ERROR] " + e.getMessage());
        }


    }
}
