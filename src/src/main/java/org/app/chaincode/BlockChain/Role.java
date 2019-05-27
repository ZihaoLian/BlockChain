
import java.util.*;

/**
 * 
 */
public class Role {

    /**
     * Default constructor
     */
    public Role(String Name) {
        setName(Name);
    }

    /**
     * 
     */
    private String name;

    public void setName(String Name){
        name = Name;
    }

    public String getName(){
        return name;
    }
}