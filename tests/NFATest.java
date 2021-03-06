import static org.junit.Assert.*;

import java.util.*;
import java.lang.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import nfa.*;

public class NFATest {
    
    NFA nfa;

    @Before
    public void restart(){
        nfa = new NFA();
    }

    @After 
    public void reset(){
        nfa.reset();
    }

    @Test
    public void testNewState(){
        NFA nfa1 = new NFA();

        State state1 = (State) nfa1.makeNewState();
        assertEquals(state1.toString(), "s1");
        assertEquals(nfa1.getNumStates(), 2);
        assertTrue(nfa1.getMap().containsKey(state1));
        assertEquals(state1.hashCode(), 1);
        assertEquals(nfa1.getStartState().hashCode(), 0);
    }

    @Test 
    public void testNewTransition(){
        NFA nfa1 = new NFA();

        State state0 = (State) nfa1.makeNewState();
        assertEquals(nfa1.getMap().size(), 2);
        assertTrue(nfa1.getMap().containsKey(state0));

        State state1 = (State) nfa1.makeNewState();
        assertEquals(nfa1.getMap().size(), 3);
        assertTrue(nfa1.getMap().containsKey(state1));

        nfa1.makeNewTransition(state0, 'a', state1);
    }

    @Test 
    public void testStartState(){
        NFA nfa1 = new NFA();

        // State state0 = (State) nfa1.makeNewState();
        assertEquals(nfa1.getStartState().hashCode(), 0);
    }

    @Test 
    public void testFinalState(){
        NFA nfa1 = new NFA();

        State state0 = (State) nfa1.makeNewState();
        assertEquals(nfa1.getMap().size(), 2);
        
        assertTrue(!state0.isFinal());
        nfa1.make_Final(state0);
        assertTrue(state0.isFinal());
        assertTrue(nfa1.getMap().keySet().contains(state0));
    }

    @Test 
    public void testFinalStateExtra(){
        NFA nfa1 = new NFA();

        State state0 = (State) nfa1.makeNewState();
        State state1 = (State) nfa1.makeNewState();
        State state2 = (State) nfa1.makeNewState();

        nfa1.makeNewTransition(state0, 'a', state2);
        nfa1.makeNewTransition(state1, 'b', state2);

        nfa1.make_Final(state2);
        List<Transition> s0Trans = (List<Transition>) nfa1.getMap().get(state0);
        List<Transition> s1Trans = (List<Transition>) nfa1.getMap().get(state1);

        Transition s0s2 = s0Trans.get(s0Trans.size() - 1);
        Transition s1s2 = s1Trans.get(s1Trans.size() - 1);

        State s0end = s0s2.getEndState();
        State s1end = s1s2.getEndState();

        assertTrue(s0end.isFinal());
        assertTrue(s1end.isFinal());
    }

    @Test
    public void testListStates(){
        NFA nfa1 = new NFA();

        State state0 = (State) nfa1.makeNewState();
        State state1 = (State) nfa1.makeNewState();
        State state2 = (State) nfa1.makeNewState();
        State state3 = (State) nfa1.makeNewState();
        State state4 = (State) nfa1.makeNewState();
        State state5 = (State) nfa1.makeNewState();
        State state6 = (State) nfa1.makeNewState();
        State state7 = (State) nfa1.makeNewState();
        State state8 = (State) nfa1.makeNewState();
        State state9 = (State) nfa1.makeNewState();

        nfa1.make_Final(state2);
        nfa1.make_Final(state4);
        nfa1.make_Final(state6);
        nfa1.make_Final(state8);
        nfa1.make_Final(state0);

        List<State> toCheck = new ArrayList<>();
        
        toCheck.add(nfa1.getStartState());
        toCheck.add(state0);
        toCheck.add(state1);
        toCheck.add(state2);
        toCheck.add(state3);
        toCheck.add(state4);
        toCheck.add(state5);
        toCheck.add(state6);
        toCheck.add(state7);
        toCheck.add(state8);
        toCheck.add(state9);

        List<State> toCheckFinals = new ArrayList<>();

        toCheckFinals.add(state0);
        toCheckFinals.add(state2);
        toCheckFinals.add(state4);
        toCheckFinals.add(state6);
        toCheckFinals.add(state8);

        for (State s : toCheck){
            assertTrue(nfa1.states().contains(s));
        }

        assertEquals(nfa1.states(), toCheck);

        // Manually find a list of finals using an entire different logic
        List<State> resultFinals = new ArrayList<>();
        Set<State> keys = nfa1.getMap().keySet();

        for (State s : keys){
            if (s.isFinal()){
                resultFinals.add(s);
            }
        }

        for (State s : toCheckFinals){
            assertTrue(resultFinals.contains(s));
        }

        assertEquals(toCheckFinals, resultFinals);

    }

    @Test 
    public void testGetListTran(){
        NFA nfa1 = new NFA();

        State state0 = (State) nfa1.makeNewState();
        State state1 = (State) nfa1.makeNewState();
        State state2 = (State) nfa1.makeNewState();
        State state3 = (State) nfa1.makeNewState();
        State state4 = (State) nfa1.makeNewState();
        State state5 = (State) nfa1.makeNewState();
        State state6 = (State) nfa1.makeNewState();
        State state7 = (State) nfa1.makeNewState();
        State state8 = (State) nfa1.makeNewState();
        State state9 = (State) nfa1.makeNewState();

        nfa1.makeNewTransition(state0, 'a', state2);
        nfa1.makeNewTransition(state2, 'b', state8);
        nfa1.makeNewTransition(state8, 'c', state4);
        nfa1.makeNewTransition(state2, 'd', state4);
        nfa1.makeNewTransition(state4, 'e', state2);
        nfa1.makeNewTransition(state0, '#', state3);
        nfa1.makeNewTransition(state3, 'g', state5);
        nfa1.makeNewTransition(state3, 'q', state1);
        nfa1.makeNewTransition(state1, 'j', state5);
        nfa1.makeNewTransition(state4, 'e', state6);
        nfa1.makeNewTransition(state5, '#', state6);
        nfa1.makeNewTransition(state6, 'r', state7);
        nfa1.makeNewTransition(state7, 'r', state9);

        List<Map.Entry<Character, Object>> result0 = nfa1.getTransition(state0);
        List<Map.Entry<Character, Object>> result2 = nfa1.getTransition(state2);

        assertEquals(result0.size(), 2); 
        // System.out.println(result0);

        assertEquals(result2.size(), 2);
        // System.out.println(result2);
    }

    @Test 
    public void testToPostfix(){
        NFA nfa1 = new NFA();

        Parser p1 = new Parser("(a|b)(e|f)");
        Regex r1 = p1.parse();
        // System.out.println();
        // System.out.println(nfa1.getConcatenation(r1.toString()));
        
        String rp1 = nfa1.getPostfix(r1.toString());
        // System.out.println(rp1);

        // assertEquals(rp1, "a*b.c|");
    }

    @Test 
    public void testBuildNFA(){
        // NFA nfa1 = new NFA();
        Parser p1 = new Parser("(a*)*");
        Regex r1 = p1.parse();
        // System.out.println("\n" + nfa1.getPostfix(r1.toString()));

        NFA newNFA = new NFA(r1);

        /************** Printing out the map **************/
        Set<Map.Entry<State, List<Transition>>> entryS = (Set) newNFA.getMap().entrySet();

        System.out.println();

        for (Map.Entry<State, List<Transition>> entry : entryS){
            String finalString;
            if (entry.getKey().isFinal()){
                finalString = "(f)";
            }
            else{
                finalString = "";
            }
            System.out.print(entry.getKey() + finalString + ": ");
            
            List<Transition> listTran = entry.getValue();
            for (Transition t : listTran)
                System.out.print(t.getStartState().toString() + " -- " + t.getName() + " --> " + t.getEndState().toString() + "\t");
            System.out.println();
        }
        /********************************************************/
    }

    // @Test
    public void testBuildNFA2(){
        Parser p1 = new Parser("z");
        Regex r1 = p1.parse();
        NFA newNFA = new NFA(r1);

        assertEquals(newNFA.getStartState().hashCode(), 0);
        assertEquals(newNFA.getFinalStates().size(), 1);
        
        System.out.println();
        System.out.println(newNFA.getStates());
        
        /************** Printing out the map **************/
        List<Object> finals = newNFA.getFinalStates();

        Object startState = newNFA.getStartState();
        Object finalState = newNFA.getFinalStates().get(0);

        System.out.println(finalState);

        List<Map.Entry<Character, Object>> trans_1 = newNFA.getTransition(startState);
        List<Map.Entry<Character, Object>> trans_2 = newNFA.getTransition(finalState);

        System.out.println(trans_1);
        System.out.println(trans_2);
        /********************************************************/
    }
}