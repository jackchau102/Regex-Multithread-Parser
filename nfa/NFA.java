package nfa;

import java.util.*;
import java.lang.*;

public class NFA {

  private HashMap <State, List<Transition>> graph;
  private static State startState;
  private static List<State> finalStates;
  private static int numStates = 0;

  public NFA() {
    graph = new HashMap<>();
    finalStates = new ArrayList<>();
  }

  Object newState() {
    // Create new state and its list of transitions
    State s = new State(numStates);
    List<Transition> listOfTrans = new ArrayList<>();

    // If it is the first state ever, make it the starting state
    if (numStates == 0){
      startState = s;
    }

    // Add to NFA
    graph.put(s, listOfTrans);

    // Increment numStates
    numStates++;
    
    return s;
  }

  void newTransition(Object start, char c, Object end) {
    
    if (start == null || end == null){
      throw new RuntimeException("States cannot be null");
    }

    // Check that both states have to exist
    if (!graph.containsKey((State) start) || !graph.containsKey((State) end)){
      throw new RuntimeException("States are not found in NFA");
    }
    // Make the new transition
    Transition trans = new Transition((State) start, c, (State) end);

    // Add the transition to the NFA by adding to the List of transitions at that key
    graph.get((State) start).add(trans);
  }

  /*******************************************Need to check with campuswire about behavior***************************/
  void makeFinal(Object s) {
    if (s == null){
      throw new RuntimeException("Final state cannot be null");
    }

    if (!graph.containsKey(s)){
      throw new RuntimeException("Final state is not found in map");
    }

    // Remove the old value of s and inser the new s, which is now final into graph
    List<Transition> keyValue = graph.remove((State) s);
    State newS = (State) s;
    newS.makeFinal();
    graph.put((State) newS, keyValue);

    // Add s to the list of final states
    if (!finalStates.contains(newS)){
      finalStates.add((State) newS);
    }
  }

  NFA(Regex re) {
    throw new UnsupportedOperationException();
  }

  public List<Object> states() {
    throw new UnsupportedOperationException();
  }

  public Object start_state() {
    throw new UnsupportedOperationException();
  }

  public List<Object> final_states() {
    throw new UnsupportedOperationException();
  }

  public List<Map.Entry<Character, Object>> transition(Object state) {
    throw new UnsupportedOperationException();
  }

  boolean match(String s, int nthreads) {
    throw new UnsupportedOperationException();
  }

  /*********** Getter methods for testing ***********/
  public HashMap getMap(){
    return graph;
  }

  public State getStartState() {
    return startState;
  }

  public int getNumStates(){
    return numStates;
  }

  public List<State> getFinalStates(){
    return finalStates;
  }

  /*********** Psuedo public methods for testing ***********/

  public Object makeNewState() {
    return newState();
  }

  public void makeNewTransition(Object a, char c, Object b){
    newTransition(a, c, b);
  }

  /*********** Reset - used for testing only***********/
  public void reset(){
    graph.clear();
    startState = null;
    finalStates.clear();
    numStates = 0;
  }
}
