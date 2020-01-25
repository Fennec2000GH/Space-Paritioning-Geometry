import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Node<N>{
    //MEMBER VARIABLES
    private N id;
    private HashMap<String, Object> attributes;
    private HashSet<Node<N>> neighbors;

    //CONSTRUCTORS
    //default constructor
    public Node(){ this.attributes = new HashMap<>(); }

    //constructor applies ID
    public Node(N identifier){
        this();
        this.id = identifier;
    }

    //ACCESSORS
    //gets ID
    public N getID(){ return this.id; }

    //gets specific attribute from key
    public Object getAttribute(String key){ return this.attributes.getOrDefault(key, null); }

    //gets the titles of all attributes in alphanumeric order
    public String[] getAttributeAsArray(String key){
        String[] output = new String[this.attributes.keySet().size()];
        this.attributes.keySet().stream().sorted().collect(Collectors.toList()).toArray(output);
        return output;
    }

    //checks whether given Node is a neighbor (connected by edge) of this Node
    public boolean hasNeighbor(Node<N> otherNode){ return this.neighbors.contains(otherNode); }

    //gets all neighbors currently connected to this Node
    public Node<N>[] getNeighborsAsArray(){
        if(this.neighbors.isEmpty())
            return null;
        Node<N>[] output = new Node[(int)this.neighbors.stream().filter(Objects::nonNull).count()];
        this.neighbors.stream().filter(Objects::nonNull).collect(Collectors.toUnmodifiableList()).toArray(output);
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

    //adds several attributes such that each new attribute does not previously exist
    public boolean addAll(Map<String, Object> newAttributes){
        if(this.attributes.keySet().stream().anyMatch(newAttributes::containsKey))
            return false;
        this.attributes.putAll(newAttributes);
        return true;
    }

    //removes a pre-existing attribute
    public boolean remove(String key){
        if(!this.attributes.containsKey(key))
            return false;
        this.attributes.remove(key);
        return true;
    }

    //removes several attributes at once
    public boolean removeAll(Collection<String> arr){
        int initialSize = this.attributes.size();
        for(String key : arr)
            if(this.attributes.containsKey(key))
                this.attributes.remove(key);
        return !(this.attributes.size() == initialSize);
    }

    //adds new neighbor Node
    public boolean addNeighbor(Node<N> otherNode){
        if(otherNode == this || this.hasNeighbor(otherNode))
            return false;
        return this.neighbors.add(otherNode);
    }

    //add several new neighbor Node at once
    public boolean addAllNeighbors(Collection<Node<N>> arr){ return this.neighbors.addAll(arr); }

    //remove pre-existing neighbor
    public boolean removeNeighbor(Node<N> otherNode){ return this.neighbors.remove(otherNode); }

    //remove several neighbor Node at once
    public boolean removeAllNeighbor(Collection<Node<N>> arr){ return this.neighbors.removeAll(arr); }

}

class Vertex<N> extends Node<N>{
    //MEMBER VARIABLES
    private Number[] coordinates;

    //MEMBER FUNCTIONS
    //default constructor
    public Vertex(){ super(); }

    //constructor applies ID and dimension
    public Vertex(N identifier, int dim){
        super(identifier);
        if(dim > 0)
            this.coordinates = new Number[dim];
    }

    //constructor applies ID and custom coordinates
    public Vertex(N identifier, Number[] coordinates){
        super(identifier);
        this.coordinates = coordinates;
    }

    //ACCESSORS
    //gets single coordinate-value corresponding to the n^th axis; x = 0, y = 1, z = 2, ...
    public Number coordinateAt(int axis){
        if(axis < 0 || axis >= this.getDimension() || this.getDimension() == 0)
            return null;
        return this.coordinates[axis] == null ? 0 : this.coordinates[axis];
    }

    //gets the coordinates as an array
    public Number[] getCoordinates(){ return this.coordinates; }

    //gets the dimension of space this vertex resides in
    public int getDimension(){ return this.coordinates.length; }

    //calculates Euclidean distance between 2 Vertex
    public static <N> double getDistance(Vertex<N> v1, Vertex<N> v2){
        if(v1.getDimension() != v2.getDimension())
            return -1;
        else if(v1 == v2 || v1.getCoordinates().equals(v2.getCoordinates()))
            return 0;
        HashMap<Number, Number> temp = new HashMap<>();
        for(int pos = 1; pos <= v1.getDimension(); pos++)
            temp.put(v1.coordinateAt(pos - 1), v2.coordinateAt(pos -1));
        return Math.sqrt(temp.entrySet().stream()
                .mapToDouble(n -> Math.pow(n.getKey().doubleValue() - n.getValue().doubleValue(), 2))
                .sum());
    }

    //calculates Manhattan distance between 2 Vertex
    public static <N> double getManhattanDistance(Vertex<N> v1, Vertex<N> v2){
        if(v1.getDimension() != v2.getDimension())
            return -1;
        else if(v1 == v2 || v1.getCoordinates().equals(v2.getCoordinates()))
            return 0;
        HashMap<Number, Number> temp = new HashMap<>();
        for(int pos = 1; pos <= v1.getDimension(); pos++)
            temp.put(v1.coordinateAt(pos - 1), v2.coordinateAt(pos -1));
        return temp.entrySet().stream()
                .mapToDouble(n -> Math.abs(n.getKey().doubleValue() - n.getValue().doubleValue()))
                .sum();
    }

    //gets new copy of this vertex but projected onto a lower dimension
    public static <N> Vertex<N> getProjection(Vertex<N> vertex, int newDim){
        if(newDim < 0 || vertex.getDimension() < newDim)
            return null;
        else if(newDim == 0)
            return new Vertex<N>(vertex.getID(), 0);
        Number[] newCoordinates = Arrays.copyOfRange(vertex.getCoordinates(), 0, newDim);
        return new Vertex<N>(vertex.getID(), newCoordinates);
    }

    //MUTATORS
    //sets new coordinates
    public boolean setCoordinates(Number[] arr){
        if(this.getDimension() == arr.length && !Arrays.asList(this.coordinates).retainAll(Arrays.asList(arr)))
            return false;
        this.coordinates = arr;
        return true;
    }

    //sets new coordinates with Collection input
    public boolean setCoordinates(Collection<Number> arr){
        if(this.getDimension() == arr.size() && !Arrays.asList(this.coordinates).retainAll(arr))
            return false;
        arr.toArray(this.coordinates);
        return true;
    }

    //projects this vertex onto a lower dimension
    public boolean project(int newDim){
        if(this.getDimension() == 0 || newDim < 0)
            return false;
        this.coordinates = Arrays.copyOfRange(this.coordinates, 0, newDim);
        return true;
    }

}

