package nfa;
import java.util.*;

public class State {
    // Representation of a State
    private String stateName;
    private Integer id;
    private boolean isLast = false;

    /*********** Constructor ***********/

    public State(Integer n){
        stateName = "s" + Integer.toString(n);
        id = n;
    }

    /*********** Getter methods ***********/

    public String toString(){
        return stateName;
    }

    public Integer getId(){
        return id;
    }

    public boolean isFinal(){
        return isLast;
    }

    /*********** Setter methods ***********/

    public void makeFinal(){
        isLast = true;
    }

    /*********** Overriding methods ***********/

    @Override
    public boolean equals(Object s){
        
        if (this == s) return true;

        if (s == null) return false;

        if (s.getClass() != this.getClass()) return false;

        State aux = (State) s;

        return this.toString().equals(aux.toString());
    }

    @Override
    public int hashCode(){
        return id;
    }
}