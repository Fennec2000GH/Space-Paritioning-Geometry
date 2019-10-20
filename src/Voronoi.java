import javolution.util.FastSet;
import javolution.util.FastTable;
import java.awt.geom.Point2D;
import org.jgrapht.graph.*;
import org.jgrapht.graph.builder.*;

public class Voronoi<V, S, P>{
    //ENUMERATIONS
    enum Distance {
        EUCLIDEAN,
        MANHATTAN,
        WEIGHTED;
    }

    //CONSTRUCTORS
    public Voronoi(V identifier){
        this.id = identifier;
    }

    public Voronoi(V identifier, Distance d){
        this(identifier);
        this.distType = d;
    }

    public Voronoi(V identifier, Distance d, boolean weighted, boolean directed){
        this(identifier);
        this.distType = d;
        if(weighted && directed)
            this.clusterList = new FastSet<SimpleDirectedWeightedGraph<Seed2D<S, P>, DefaultWeightedEdge>();
        else if(weighted != false && directed == false)
            this.clusterList = new FastSet<SimpleWeightedGraph<Seed2D<S, P>, DefaultWeightedEdge>>();
        else if(weighted == false && directed != false)
            this.clusterList = new FastSet<SimpleDirectedGraph<Seed2D<S, P>, DefaultEdge>>();

    }

    //ACCESSORS
    public Point2D getSeed(int i){
        if(i <= this.seedList.size())
            return Point2D.class.cast(this.seedList.get(i - 1));
        return null;
    }

    //MUTATORS
    public boolean addSeed(S identifier, P x, P y){
        if(this.seedList.stream().anyMatch(seed -> (seed.getID().equals(identifier))))
            return false;
        if(this.seedList.stream().anyMatch(seed -> (seed.getX() == (double)x && seed.getY() == (double)y)))
            return false;
        this.seedList.add(new Seed2D<S, P>(identifier, x, y, true));
        this.clusterList.add(new GraphBuilder<S, P>())
    }

    //DATA VARIABLES
    private V id;
    private Distance distType = Distance.EUCLIDEAN;
    private FastTable<Seed2D<S, P>> seedList = new FastTable<>();
    private FastSet<?> clusterList = new FastSet<SimpleGraph<Seed2D<S, P>, DefaultEdge>>();

}
