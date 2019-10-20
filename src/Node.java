import java.util.Iterator;
import java.util.Map;
import javolution.util.FastMap;

public class Node<N>{
    //CONSTRUCTORS
    public Node(N identifier){
        this.id = identifier;
    }

    //ACCESSORS
    public N getID(){
        return this.id;
    }

    public String getInfo(String key){
        return this.info.get(key).get();
    }

    public Variant getInfoAsVariant(String key){
        return this.info.get(key);
    }

    public void printNode(){
        System.out.println(this.id + " (Node) ");
        for(int i = 1; i <= String.valueOf(this.id).length() + 8; i++)
            System.out.print("-");
        System.out.println("");
        System.out.println("ID: " + this.id);
        System.out.println("Info: ");
        Iterator<Map.Entry<String, Variant>> it = this.info.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Variant> entry = it.next();
            System.out.println("\t" + entry.getKey() + " : " + entry.getValue().get());
        }
        return;

    }

    //MUTATORS
    public void setID(N identifier){
        this.id = identifier;
    }

    public <T> boolean addInfo(String key, T val){
        if(this.info.keySet().contains(key))
            return false;
        this.info.put(key, new Variant(val));
        return true;
    }

    public boolean addInfo(String key, Variant val){
        if(this.info.keySet().contains(key))
            return false;
        this.info.put(key, val);
        return true;
    }

    //MEMBER VARIABLES
    protected N id;
    protected FastMap<String, Variant> info;

};









