import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Node<N>{
    //MEMBER VARIABLES
    private N id;
    private TreeMap<String, Object> attributes;

    //CONSTRUCTORS
    //default constructor
    public Node(N identifier){ this.id = identifier; }

    //ACCESSORS
    //gets ID
    public N getID(){ return this.id; }

    //gets the titles of all attributes in alphanumeric order
    public String[] getAttributeAsArray(String key){
        String[] output = new String[this.attributes.keySet().size()];
        this.attributes.keySet().stream().sorted().collect(Collectors.toList()).toArray(output);
        return output;
    }

    //MUTATORS
    //sets new ID
    public void setID(N identifier){ this.id = identifier; }

    //adds new attribute that does not previously exist
    public boolean add(String key, Object obj){
        if(this.attributes.containsKey(key))
            return false;
        this.attributes.put(key, obj);
        return true;
    }

    //adds multiple attributes such that each new attribute does not previously exist
    public boolean addAll(Map<String, Object> newAttributes){
        if(this.attributes.keySet().stream().anyMatch(newAttributes::containsKey))
            return false;
        this.attributes.putAll(newAttributes);
        return true;
    }

}

class Vertex<N> extends Node<N>{
    //MEMBER VARIABLES
    private int dimension;

    //MEMBER FUNCTIONS
    //default constructor
    public Vertex(){
        super();
        this.add("Coordinates", null);
    }

    //constructor applies ID and dimension
    public Vertex(N identifier, int dim){
        super(identifier);
        this.dimension = dim;
        this.add("Coordinates", new Number[dim]);
    }

    //ACCESSORS

    //MUTATORS
    
}




