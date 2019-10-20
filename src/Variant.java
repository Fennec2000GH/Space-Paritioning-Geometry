public class Variant{
    //CONSTRUCTORS
    public <T> Variant(T val){
        this.set(val);
    }

    //ACCESSORS
    public String get(){
        if(d.length == 1)
            return Double.toString(d[0]);
        else if(i.length == 1)
            return Integer.toString(i[0]);
        else if(c.length == 1)
            return Character.toString(c[0]);
        else if(s.length() == 1)
            return s;
        return null;
    }

    //MUTATORS
    public void clear(){
        d = null;
        i = null;
        c = null;
        s = "";
        return;
    }

    public <T> void set(T val){
        this.clear();
        if(Double.class.isInstance(val)){
            d = new double[1];
            d[0] = (double)val;
        } else if(Integer.class.isInstance(val)){
            i = new int[0];
            i[0] = (int)val;
        } else if(Character.class.isInstance(val)){
            c = new char[1];
            c[0] = (char)val;
        } else {
            s = (String)val;
        }
        return;

    }

    //MEMBER VARIABLES
    private double[] d;
    private int[] i;
    private char[] c;
    private String s;

}
