import java.util.*;

class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
}

public class ClosestPair {
    public static void main(String[] args) {
        List<Point> points = Arrays.asList(
                new Point(2, 3), new Point(12, 30),
                new Point(40, 50), new Point(5, 1),
                new Point(12, 10), new Point(3, 4)
        );

        double dist = closestPair(points);
        System.out.println("Closest distance = " + dist);
    }

    public static double closestPair(List<Point> points) {
        points.sort(Comparator.comparingInt(p -> p.x));
        return closest(points, 0, points.size() - 1);
    }

    private static double closest(List<Point> points, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        }

        int mid = (left + right) / 2;
        Point midPoint = points.get(mid);

        double dLeft = closest(points, left, mid);
        double dRight = closest(points, mid + 1, right);
        double d = Math.min(dLeft, dRight);

        List<Point> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points.get(i).x - midPoint.x) < d) {
                strip.add(points.get(i));
            }
        }

        strip.sort(Comparator.comparingInt(p -> p.y));
        return Math.min(d, stripClosest(strip, d));
    }

    private static double bruteForce(List<Point> points, int left, int right) {
        double min = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                min = Math.min(min, dist(points.get(i), points.get(j)));
            }
        }
        return min;
    }

    private static double stripClosest(List<Point> strip, double d) {
        double min = d;
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; j++) {
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }
}
