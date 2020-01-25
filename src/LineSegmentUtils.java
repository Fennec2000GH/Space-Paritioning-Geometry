import org.javatuples.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import com.google.common.collect.ArrayListMultimap;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

public abstract class LineSegmentUtils {
    /** Finds the leftmost endpoint of a line segment
     * @param l Line segment
     * @return Leftmost endpoint of <code>l</code> if one endpoint is strictly left of the other endpoint, otherwise
     * returns the first endpoint used when creating <code>l</code>
     */
    public static Point2D getLeftEndpoint(Line2D l){
        try {
            if(l.getX1() == l.getX2())
                throw new Exception("Both endpoints are vertically symmetrical.");
        } catch (Exception e){
            e.printStackTrace();
            return l.getP1();
        }
        return l.getX1() < l.getX2() ? l.getP1() : l.getP2();
    }

    /** Finds the rightmost endpoint of a line segment
     * @param l Line segment
     * @return Rightmost endpoint of <code>l</code> if one endpoint is strictly right of the other endpoint, otherwise
     * returns the first endpoint used when creating <code>l</code>
     */
    public static Point2D getRightEndpoint(Line2D l){
        try {
            if(l.getX1() == l.getX2())
                throw new Exception("Both endpoints are vertically symmetrical.");
        } catch (Exception e){
            e.printStackTrace();
            return l.getP1();
        }
        return l.getX1() > l.getX2() ? l.getP1() : l.getP2();
    }

    /** Finds the topmost endpoint of a line segment
     * @param l Line segment
     * @return Topmost endpoint of <code>l</code> if one endpoint is strictly above the other endpoint, otherwise
     * returns the first endpoint used when creating <code>l</code>
     */
    public static Point2D getUpperEndpoint(Line2D l){
        try {
            if(l.getY1() == l.getY2())
                throw new Exception("Both endpoints are horizontally symmetrical.");
        } catch (Exception e){
            e.printStackTrace();
            return l.getP1();
        }
        return l.getY1() > l.getY2() ? l.getP1() : l.getP2();
    }

    /** Finds the bottommost endpoint of a line segment
     * @param l Line segment
     * @return Bottommost endpoint of <code>l</code> if one endpoint is strictly below the other endpoint, otherwise
     * returns the first endpoint used when creating <code>l</code>
     */
    public static Point2D getLowerEndpoint(Line2D l){
        try {
            if(l.getY1() == l.getY2())
                throw new Exception("Both endpoints are horizontally symmetrical.");
        } catch (Exception e){
            e.printStackTrace();
            return l.getP1();
        }
        return l.getY1() < l.getY2() ? l.getP1() : l.getP2();
    }

    /** Finds the orientation of 3 points in given order
     * @param p First point
     * @param q Second point
     * @param r Third point
     * @return -1 if counterclockwise, 0 if collinear, and 1 if clockwise orientation
     */
    public static int getOrientation(Point2D p, Point2D q, Point2D r){
        //3D vector pq
        double[] pq = {q.getX() - p.getX(), q.getY() - p.getY(), 0};

        //3D vector qr
        double[] qr = {r.getX() - q.getX(), r.getY() - q.getY(), 0};

        //cross-product
        //only the z-component is valuable as the first 2 components are zero (0)
        double z = pq[0] * qr[1] - pq[1] * qr[0];
        return (int)Math.signum(z);
    }

    /** Determines whether the projections of 2 <code>Line2D</code> objects onto the x-axis overlap or touch.
     * @param l1 First line segment
     * @param l2 Second line segment
     * @return <code>true</code> if <code>l1</code> projection onto the x-axis overlaps with <code>l2</code> projection
     * onto the x-axis, otherwise <code>false</code>.
     */
    public static boolean xProjectionIntersects(Line2D l1, Line2D l2){
        Line2D.Double xProj1 = new Line2D.Double(l1.getX1(), 0, l1.getX2(), 0),
                xProj2 = new Line2D.Double(l2.getX1(), 0, l2.getX2(), 0);
        return intersects(xProj1, xProj2);
    }

    /** Determines whether the projections of 2 <code>Line2D</code> objects onto the y-axis overlap or touch.
     * @param l1 First line segment
     * @param l2 Second line segment
     * @return <code>true</code> if <code>l1</code> projection onto the y-axis overlaps with <code>l2</code> projection
     * onto the y-axis, otherwise <code>false</code>.
     */
    public static boolean yProjectionIntersects(Line2D l1, Line2D l2){
        Line2D.Double yProj1 = new Line2D.Double(0, l1.getY1(), 0, l1.getY2()),
                yProj2 = new Line2D.Double(0, l2.getY1(), 0, l2.getY2());
        return intersects(yProj1, yProj2);
    }

    /** Determines whether 2 <code>Line2D</code> objects intersect or not based on orientations of their endpoints.
     * @param l1 First line segment
     * @param l2 Second line segment
     * @return <code>true</code> if there is an intersection between the 2 line segments.
     */
    public static boolean intersects(Line2D l1, Line2D l2){
        int orientationP1Q1P2 = LineSegmentUtils.getOrientation(l1.getP1(), l1.getP2(), l2.getP1()),
            orientationP1Q1Q2 = LineSegmentUtils.getOrientation(l1.getP1(), l2.getP2(), l2.getP2()),
            orientationP2Q2P1 = LineSegmentUtils.getOrientation(l2.getP1(), l2.getP2(), l1.getP1()),
            orientationP2Q2Q1 = LineSegmentUtils.getOrientation(l2.getP1(), l2.getP2(), l1.getP2());
        if(orientationP1Q1P2 == - orientationP1Q1Q2 && orientationP2Q2P1 == - orientationP2Q2Q1)
            return true;
        else if(orientationP1Q1P2 == 0 && LineSegmentUtils.xProjectionIntersects(l1, l2))
            return true;
        return false;
    }

    /** Finds all intersections between any 2 line segments from a <code>List</code> of <code>Line2D</code> objects.
     * @param segList List of line segments
     * @return <code>List</code> of all intersections between any 2 given line segments
     */
    public static List<Pair<Line2D, Line2D>> intersect(List<Line2D> segList){
        try {
            if(segList == null || segList.size() < 2)
                throw new IllegalArgumentException("List of line segments must have at lease 2 elements.");
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        //output list containing documented intersections
        ArrayList<Pair<Line2D, Line2D>> intersections = new ArrayList<>();

        //any line segment can be identified by either endpoint using the map below
        ArrayListMultimap<Point2D, Line2D> pointToLineMap = ArrayListMultimap.create();
        for(Line2D l : segList) {
            pointToLineMap.put(l.getP1(), l);
            pointToLineMap.put(l.getP2(), l);
        }

        //maps given input list of line segments to upper endpoints and then ranked from highest to lowest
        Comparator<Point2D> YComparator = Comparator.comparingDouble(Point2D::getY);
        List<Point2D> upperEndpoints = segList.stream().map(LineSegmentUtils::getUpperEndpoint).sorted(YComparator).map
        upperEndpoints.sort(YComparator.reversed());

        //stack holds all event points ordered from highest upper endpoint starting on top
        Stack<Point2D> epStack = new Stack<>();
        epStack.addAll(upperEndpoints);

        //hash set contains all line segments currently being scanned by plan sweep line
        HashSet<Line2D> currentLine2D = new HashSet<>(segList.size());

        //algorithm running...
        Point2D currentEP;
        while(!epStack.isEmpty()){
            currentEP = epStack.pop();



        }
        return intersections;
    }


}


