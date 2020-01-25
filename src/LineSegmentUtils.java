import org.javatuples.Pair;

import javax.sound.sampled.Line;
import java.util.List;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class LineSegmentUtils {
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
     * onto the x-axis, otherwise <code>false</code>
     */
    public static boolean xProjectionIntersects(Line2D l1, Line2D l2){
        Line2D.Double xProj1 = new Line2D.Double()


    }

    public static boolean yProjectionIntersects()

    public static boolean intersects(Line2D l1, Line2D l2){
        int orientationP1Q1P2 = LineSegmentUtils.getOrientation(l1.getP1(), l1.getP2(), l2.getP1()),
            orientationP1Q1Q2 = LineSegmentUtils.getOrientation(l1.getP1(), l2.getP2(), l2.getP2()),
            orientationP2Q2P1 = LineSegmentUtils.getOrientation(l2.getP1(), l2.getP2(), l1.getP1()),
            orientationP2Q2Q1 = LineSegmentUtils.getOrientation(l2.getP1(), l2.getP2(), l1.getP2());
        if(orientationP1Q1P2 == - orientationP1Q1Q2 && orientationP2Q2P1 == - orientationP2Q2Q1)
            return true;
        else if(orientationP1Q1P2 == 0 &&)

    }

    /**
     * @param segList
     * @return
     */
    public static List<Pair<Line2D, Line2D>> intersect(List<Line2D> segList){

    }
}
