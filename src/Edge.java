import javolution.util.FastSortedSet;
import java.util.stream.Stream;
import org.jgrapht.graph.DefaultEdge;

public class Edge<E, S> extends DefaultEdge{
    //CONSTRUCTORS
    public Edge(E identifier){
        this.id = identifier;
    }

    //ACCESSORS
    public Site2D<S> first(){
        return this.ownerList.first();
    }

    public Site2D<S> last(){
        return this.ownerList.last();
    }

    public boolean contains(Site2D<S> obj){
        return this.ownerList.contains(obj);
    }

    public boolean contains(S identifier){
        return this.ownerList.stream().anyMatch(site -> (site.id.equals(identifier)));
    }

    public int size(){
        return this.ownerList.size();
    }

    //MUTATORS
    public boolean remove(Site2D<S> obj){
        return this.ownerList.remove(obj);
    }

    public boolean remove(S identifier){
        Stream<Site2D<S>> temp = this.ownerList.stream().filter(site -> site.id.equals(identifier));
        return temp.count() > 0;
    }

    public void clear(){
        this.ownerList.clear();
        return;
    }

    public boolean isEmpty(){
        return this.ownerList.isEmpty();
    }

    //MEMBER VARIABLES
    E id;
    private FastSortedSet<Site2D<S>> ownerList = new FastSortedSet<>();

}


