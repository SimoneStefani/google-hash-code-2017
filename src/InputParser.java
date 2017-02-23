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

    public InputParser(String file) {
        this.fileName = file.substring(0, file.lastIndexOf('.'));
        this.fileExtension = file.substring(file.lastIndexOf('.'), file.length());
        this.readInput();
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
            }

            // REQUESTS
            for (int i = 0; i < this.numRequests; i++) {
                Request temp = new Request();
                temp.video = in.nextInt();
                temp.endpoint = in.nextInt();
                temp.requests = in.nextInt();
            }

            in.close();

        } catch (FileNotFoundException e) {
            // file not found.
            System.out.printf("[ERROR] " + e.getMessage());
        }
    }
}
