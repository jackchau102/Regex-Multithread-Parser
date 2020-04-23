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
    State s = new State(numStates);

    // If it is the first state ever, make it the starting state
    if (numStates == 0){
      startState = s;
    }

    // Add to NFA
    graph.put(s, null);

    // Increment numStates
    numStates++;
    
    return s;
  }

  void newTransition(Object start, char c, Object end) {
    
    if (start == null || end == null) 
      return new RuntimeException("States cannot be null");

    // Check that both states have to exist
    if (!graph.containsKey((State) start) || !graph.containsKey((State) end)) 
      return new RuntimeException("States are not found in NFA");

    // Make the new transition
    Transition trans = new Transition((State) start, c, (State) end);

    // Add the transition to the NFA by adding to the List of transitions at that key
    graph.get((State) start).add(trans);
  }

  void makeFinal(Object s) {
    throw new UnsupportedOperationException();
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
}
