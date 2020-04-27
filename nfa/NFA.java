package nfa;

import java.util.*;
import java.util.Map.Entry;

import java.lang.*;

public class NFA {

  /**************** Package to represent subNFA that goes inside stack ****************/

  private class Package {
    private State packageStart;
    private List<State> packageFinals;

    public Package(State start, List<State> finals){
      packageStart = start;
      packageFinals = finals;
    }

    public State getPackageStart(){
      return packageStart;
    }

    public List<State> getPackageFinals(){
      return packageFinals;
    }
  }

  /*********************************************************************************/

  private HashMap <State, List<Transition>> graph;
  private static State startState;
  private static List<State> finalStates;
  private static int numStates = 0;

  public NFA() {
    graph = new HashMap<State, List<Transition>>();
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
    buildNFA(re);
  }

  public List<Object> states() {
    List<Object> listOfStates = new ArrayList<Object>();
    for (State s : graph.keySet()){
      listOfStates.add(s);
    }
    return listOfStates;
  }

  public Object start_state() {
    return startState;
  }

  public List<Object> final_states() {
    List<Object> listOfFinalStates = new ArrayList<Object>();
    for (State s : finalStates){
      listOfFinalStates.add(s);
    }
    return listOfFinalStates;
  }

  public List<Map.Entry<Character, Object>> transition(Object state) {
    List<Transition> listOfTrans= graph.get((State) state);
    List<Map.Entry<Character, Object>> result = new ArrayList<>();

    // Get Transitions coming out of the state
    for (Transition tran : listOfTrans){
      Map.Entry<Character, Object> entry = new AbstractMap.SimpleEntry<Character, Object>(tran.getName(), tran.getStartState());
      result.add(entry);
    }

    // Get Transitions coming into the state
    Set<Map.Entry<State, List<Transition>>> intoState = graph.entrySet();
    for (Entry e : intoState){
      List<Transition> listPerState = (List) e.getValue();
      for (Transition tran : listPerState){
        if (tran.getEndState().equals(state)){
          Map.Entry<Character, Object> entry = new AbstractMap.SimpleEntry<Character, Object>(tran.getName(), tran.getStartState());
          result.add(entry);
        }
      }
    }
    return result;
  }

  boolean match(String s, int nthreads) {
    throw new UnsupportedOperationException();
  }
  /*********** NFA Helper functions ***********/

  // Add '.' as a operation for sequencing
  String addConcatenation(String reg){
    String result = ""; 

    for (int i = 0; i < reg.length(); i++){
      result = result + Character.toString(reg.charAt(i));

      int lookahead = i + 1;
      if (lookahead < reg.length()){
        if (reg.charAt(i) == '(' || reg.charAt(i) == '|') continue;

        if(reg.charAt(lookahead) == ')' || reg.charAt(lookahead) == '|' || reg.charAt(lookahead) == '*')
          continue;
        
        result = result + Character.toString('.');
      }
    }

    return result;
  }

  // Transform an infix regex to postfix regex
  String infixToPostfix(String r){
    Stack<String> opStack = new Stack<>();
    String reg = addConcatenation(r);
    String result = "";

    if (reg.length() == 0){
      return result;
    }

    // Add first letter to result
    if (reg.charAt(0)  == '(' || reg.charAt(0) == ')' || reg.charAt(0) == '|' || reg.charAt(0) == '*'){
      opStack.push(Character.toString(reg.charAt(0)));
    }
    else {
      result = result + Character.toString(reg.charAt(0));
    }

    for (int i = 1; i < reg.length(); i++){
      String letter = Character.toString(reg.charAt(i));

      if (letter.equals("(") || letter.equals(".")){
        opStack.push(letter);
      }
      else if (letter.equals("|")){
        String top = opStack.peek();
        while (top.equals(".")){
          String popS = opStack.pop();
          result = result + popS;
          top = opStack.peek();
        }
        opStack.push(letter);
      }
      else if (letter.equals("*")){
        result = result + letter;
      }
      else if (letter.equals(")")){
        String top = opStack.peek();
        while (!top.equals("(")){
          String popS = opStack.pop();
          result = result + popS;
          top = opStack.peek();
        }
      }
      else {
          result = result + letter;
      }
    }

    while(!opStack.isEmpty()){
      String op = opStack.pop();
      if (!op.equals("(")){
        result = result + op; 
      }
    }

    return result;
  }

  void buildNFA(Regex reg){
    String postFixReg = infixToPostfix(reg.toString());
    Stack<Package> stackPackage = new Stack<>();

    for (int i = 0; i < postFixReg.length(); i++){
      char curr = postFixReg.charAt(i);
      if (curr == '*'){
        Package e = stackPackage.pop();
        Package toPush = buildStar(e);
        stackPackage.push(toPush);
      }
      else if (curr == '|'){
        Package e = stackPackage.pop();
        Package f = stackPackage.pop();
        Package toPush = buildDisjunction(e, f);
        stackPackage.push(toPush);
      }
      else if (curr == '.'){
        Package e = stackPackage.pop();
        Package f = stackPackage.pop();
        Package toPush = buildSequence(e, f);
        stackPackage.push(toPush);
      }
    }

    // At the end of the scan, there should always be one package left in the stack
    Package lastPackage = stackPackage.pop();
    startState = lastPackage.getPackageStart();
    finalStates = lastPackage.getPackageFinals();
  }

  /*********** Methods to help build NFA ***********/

  Package buildCharacter(char c){

    // Create State
    State start = (State) newState();
    State end = (State) newState();
    newTransition(start, c, end);

    // Create package
    List<State> finals = new ArrayList<>();
    finals.add(end);

    Package result = new Package(start, finals);

    return result;
  }

  Package buildSequence(Package start, Package end){

    // E
    State startStartState = (State) start.getPackageStart();
    List<State> startFinalStates = start.getPackageFinals();

    // F
    State endStartState = (State) end.getPackageStart();
    List<State> endFinalStates = end.getPackageFinals();

    // Link two package, make final states unfinal
    // Make unfinal is not necessary because all this is transitional
    // Actual final list will be constructed later

    for (State s : startFinalStates){
      newTransition(s, '#', endStartState);
    }

    // Create new package as wrapper
    Package result = new Package(startStartState, endFinalStates);

    return result;
  }

  Package buildDisjunction(Package e, Package f){
    // E
    State eStartState = (State) e.getPackageStart();
    List<State> eFinalStates = e.getPackageFinals();

    // F
    State fStartState = (State) f.getPackageStart();
    List<State> fFinalStates = f.getPackageFinals();

    // Make new state that points to both
    State disjuncEF = (State) newState();
    newTransition(disjuncEF, '#', eStartState);
    newTransition(disjuncEF, '#', fStartState);

    // Make new Package as a wrapper
    List<State> finals = eFinalStates;
    finals.addAll(fFinalStates);

    Package result = new Package(disjuncEF, finals);

    return result;
  }

  Package buildStar(Package e){
    // E
    State eStartState = (State) e.getPackageStart();
    List<State> eFinalStates = e.getPackageFinals();

    // Build a Star sequence
    State starStart = (State) newState();
    newTransition(starStart, '#', eStartState);

    for (State s : eFinalStates){
      newTransition(s, '#', starStart);
    }

    // Build new Package as a wrapper
    List<State> finals = new ArrayList<>();
    finals.add(eStartState);

    Package result = new Package(eStartState, finals);

    return result;
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

  public void make_Final(Object o){
    makeFinal(o);
  }

  public List<Map.Entry<Character, Object>> getTransition(Object state){
    return transition(state);
  }

  public String getPostfix(String r){
    return infixToPostfix(r);
  }

  public String getConcatenation(String s){
    return addConcatenation(s);
  }

  /*********** Reset - used for testing only***********/
  public void reset(){
    graph.clear();
    startState = null;
    finalStates.clear();
    numStates = 0;
  }
}
