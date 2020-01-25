import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;

public abstract class Polygon<P, N> {
    //MEMBER VARIABLES
    private P id;
    private List<Vertex<N>> vertices;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    //Default constructor
    public Polygon(){ this.vertices = new ArrayList<>(); }

    //Constructor applies ID
    public Polygon(P identifier){
        this();
        this.id = identifier;
    }

    //Constructor applies ID and resets its vertices to a new vertex set
    public Polygon(P identifier, List<Vertex<N>> vertices){
        this(identifier);
        this.vertices = vertices;
    }

    //ACCESSORS
    //Checks for existence of a given vertex
    public boolean hasVertex(Vertex<N> vertex){ return this.vertices.contains(vertex); }

    //Checks for existing edge between 2 vertices
    public boolean hasEdge(Vertex<N> v1, Vertex<N> v2){
        if(!(this.hasVertex(vertex1) && this.hasVertex(vertex2)))
            return false;

    }

    //MUTATORS
    //Adds new vertex to vertex set
    public boolean addVertex(K vertex){
        if(this.hasVertex(vertex))
            return false;
        this.vertices.add(vertex);
        return true;
    }

    //Removes pre-existing vertex from vertex set
    public boolean removeVertex(K vertex){
        if(!this.hasVertex(vertex))
            return false;
        this.vertices.remove(vertex);
        return true;
    }

}

class Polygon2D<N> extends Polygon<N, Number[]> {
    //MEMBER VARIABLES


    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    //Default constructor
    public Polygon2D(){ super(); }

    //Constructor applies ID
    public Polygon2D(N identifier){ super(identifier); }

    //Constructor applies ID and resets its vertices to a new vertex set
    public Polygon2D(N identifier, List<Number[]> vertices){
        super(identifier);
        if(vertices.stream().allMatch(n -> n.length == 2))
            throw new IllegalArgumentException("Each vertex must be of type Number[2]");
        for(Number[] vertex : vertices)
            super.addVertex(vertex);
    }

    //ACCESSORS
    public boolean isXMonotone(){

    }

    public boolean isYMonotone(){

    }

    //MUTATORS


}

class Polygon3D<N> extends Polygon<N, Number[]>