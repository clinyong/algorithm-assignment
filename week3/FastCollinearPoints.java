
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] segmentList;
    private int totalSegments = 0;

    private class Node {
        Point item;
        Node next;
    }

    private class PointCompare implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            return p1.compareTo(p2);
        }
    }

    private Comparator<Point> sortPoints() {
        return new PointCompare();
    }

    private class TempQueue {
        private Node head = null;

        public void push(Point item) {
            if (head == null) {
                head = new Node();
                head.item = item;
                head.next = null;
            } else {
                Node first = new Node();
                first.item = item;
                first.next = head;

                head = first;
            }

        }

        public Point[] clear() {
            int len = 0;
            Node n = head;
            while(n != null) {
                n = n.next;
                len++;
            }

            Point[] list = new Point[len];
            int i = 0;

            while(head != null) {
                list[i] = head.item;

                i++;
                head = head.next;
            }


            return list;
        }
    }

    private Point[] cloneArray(Point[] arr) {
        Point[] newArr = new Point[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }

        return newArr;
    }

    private void getLineSegment(TempQueue q) {
        Point[] list = q.clear();

        if (list.length == 4) {
            Arrays.sort(list, sortPoints());

            LineSegment newSeg = new LineSegment(list[0], list[3]);
            boolean notExist = true;

            for (int i = 0; i < totalSegments; i++) {
                LineSegment segment = segmentList[i];
                if (segment.toString().equals(newSeg.toString())){
                    notExist = false;
                    break;
                }
            }


            if (notExist) {
                segmentList[totalSegments] = newSeg;
                totalSegments += 1;
            }
        }
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        segmentList = new LineSegment[points.length];

        if (points.length > 3) {
            for (int i = 0; i < points.length; i++) {
                Point p = points[i];

                Point[] clone = cloneArray(points);
                Arrays.sort(clone, p.slopeOrder());

                double[] scopeList = new double[points.length];
                for(int k = 0;k < points.length; k++) {
                    scopeList[k] = p.slopeTo(points[k]);
                }

                TempQueue queue = new TempQueue();
                double currentSlop = Double.NaN;
                for (int j = 0; j < clone.length; j++) {
                    Point pt = clone[j];
                    double slop = p.slopeTo(pt);
                    if (slop != Double.NEGATIVE_INFINITY) {
                        if (currentSlop != Double.NaN && slop != currentSlop) {
                            currentSlop = slop;
                            getLineSegment(queue);
                            queue.push(p);
                        }
                        queue.push(pt);
                    }
                }

                getLineSegment(queue);
            }
        }

    }

    public int numberOfSegments() {
        return  totalSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] list = new LineSegment[totalSegments];

        for (int i = 0; i< totalSegments; i++) {
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}