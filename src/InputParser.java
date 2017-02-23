import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.Scanner;

public class InputParser {
    /**
     * Input file
     */
    private String fileName;
    private String fileExtension;

    private int numVideos, numEndopoints, numRequests, numCaches, capacity;

    private int [] videos;
    private Cache [] caches;
    private Request [] requests;
    private Endpoint [] endpoints;

    public InputParser(String file) {
        this.fileName = file.substring(0, file.lastIndexOf('.'));
        this.fileExtension = file.substring(file.lastIndexOf('.'), file.length());
        this.readInput();
    }

    public void run() {
        for (Request r : requests) {
            handleRequest(r);
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

            this.caches = new Cache[this.numCaches];

            for (int i = 0; i < this.numCaches; i++) {
                this.caches[i] = new Cache(this.capacity);
            }

            this.requests = new Request[this.numRequests];
            this.endpoints = new Endpoint[this.numEndopoints];

            // VIDEOS
            this.videos = new int[numVideos];

            for (int i = 0; i < this.numVideos; i++) {
                this.videos[i] = in.nextInt();
            }

            // ENDPOINTS
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
            for (int i = 0; i < this.numRequests; i++) {
                Request temp = new Request();
                temp.video = in.nextInt();
                temp.endpoint = in.nextInt();
                temp.requests = in.nextInt();

                this.requests[i] = temp;
            }

            in.close();

        } catch (FileNotFoundException e) {
            // file not found.
            System.out.printf("[ERROR] " + e.getMessage());
        }
    }

    public void handleRequest(Request r) {
        if (endpoints[r.endpoint].cachesId.length == 0) {
            return;
        }

        int videoSize = videos[r.video];

        int resultCache = -1;

        for (int i = 0; i <  endpoints[r.endpoint].cachesId.length; i++) {
            if (caches[endpoints[r.endpoint].cachesId[i]].capacity - videoSize > 0) {
                caches[endpoints[r.endpoint].cachesId[i]].addVideo(r.video, videoSize);
                return;
            }
        }
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

            // print amount of commands
            bw.write(String.valueOf(-1));
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
//                String output = "" + i + " " + StringUtils.join(caches[i].videos, " ");
                bw.write(sb.toString());
                bw.newLine();

                counter++;
            }

            System.out.println("Caches used: " + counter);

            bw.flush();
            bw.close();

        } catch (IOException e) {
            // file not found.
            System.out.printf("[ERROR] " + e.getMessage());
        }


    }
}
