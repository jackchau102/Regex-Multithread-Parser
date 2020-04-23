package nfa;
import java.util.*;

public class Transition {
    // Rep. of a transition
    private State start;
    private State end;
    private Character transitionName;

    public Transition(State s1, Character c, State s2){
        start = s1;
        transitionName = c;
        end = s2;
    }

    /*********** Getter methods ***********/

    public State getStartState(){
        return start;
    }

    public State getEndState(){
        return end;
    }

    public Character getName(){
        return transitionName;
    }


}