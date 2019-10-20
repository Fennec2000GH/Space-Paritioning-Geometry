import java.util.ArrayList;
import java.util.stream.*;

public class Auxiliary{

    public static boolean isint(String str){
        char[] l = new char[str.length()];
        str.getChars(0, str.length(), l, 0);
        for(char c : l)
            if(Character.isLetter(c) == false)
                return false;
        return true;
    }

    public static boolean isletter(String str){
        ArrayList<Character> l = new ArrayList<Character>();
        for(char c : str.toCharArray())
            l.add(c);
        if(l.stream().allMatch(c -> Character.isLetter(c)))
            return true;
        return false;
    }

    public static <T> int count(ArrayList<T> l, T val){
        int counter = 0;
        for(T item : l)
            if(item == val)
                ++counter;
        return counter;
    }

    public static int count(String str, char c){
        int counter = 0;
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(0) == c)
                ++counter;
        return counter;
    }
}
