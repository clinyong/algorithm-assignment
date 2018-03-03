
import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segmentList;
    private int totalSegments = 0;

    private class PointCompare implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            return p1.compareTo(p2);
        }
    }

    private Comparator<Point> sortPoints() {
        return new PointCompare();
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        if (points.length > 3) {
            int len = points.length;

            LineSegment[] list = new LineSegment[len];
            for (int i = 0; i < len; i++) {
                Point root = points[i];
                for (int j = i + 1; j < len; j++) {
                    for (int k = j + 1; k < len; k++) {
                        for (int m = k + 1; m < len; m++) {
                            double slp1 = root.slopeTo(points[j]);
                            double slp2 = root.slopeTo(points[k]);
                            double slp3 = root.slopeTo(points[m]);

                            if (slp1 == slp2 && slp1 == slp3 && slp2 == slp3) {
                                Point[] arr = { root, points[j], points[k], points[m] };
                                Arrays.sort(arr, sortPoints());

                                LineSegment seg = new LineSegment(arr[0], arr[3]);
                                list[totalSegments] = seg;
                                totalSegments += 1;
                            }
                        }
                    }
                }
            }

            segmentList = new LineSegment[totalSegments];
            for (int i = 0; i < totalSegments; i++) {
                segmentList[i] = list[i];
            }
        }
    }

    public int numberOfSegments() {
        return totalSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] list = new LineSegment[totalSegments];

        for (int i = 0; i < totalSegments; i++) {
            list[i] = this.segmentList[i];
        }

        return list;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}