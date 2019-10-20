import java.awt.geom.Point2D;
import org.jgrapht.graph.

public class Site2D<S> extends Node<S>{
    //CONSTRUCTORS
    public Site2D(S identifier){
        super(identifier);
    }

    //ACCESSORS
    public double getX(){
        return this.point.getX();
    }

    public double getY(){
        return this.point.getY();
    }

    //MUTATORS
    public boolean setPointOrigin(){
            this.point = new Point2D.Double();
        if(this.point.getX() != 0 || this.point.getY() != 0)
            return false;
        return true;
    }

    public boolean setPoint(double x, double y){
        this.point = new Point2D.Double(x, y);
        if(this.point.getX() != x || this.point.getY() != y)
            return false;
        return true;
    }


    //MEMBER VARIABLES
    private Point2D point = new Point2D.Double();
    private Cycle<V, E> cell = new Cycle<>();
}