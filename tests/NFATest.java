import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import jdk.jfr.Timestamp;
import nfa.*;

public class NFATest {
    
    NFA nfa1;

    @Before
    public void restart(){
        nfa1 = new NFA();
    }

    @After 
    public void reset(){
        nfa1.reset();
    }

    @Test
    public void testNewState(){
        State state0 = (State) nfa1.makeNewState();
        assertEquals(state0.toString(), "s0");
        assertEquals(nfa1.getNumStates(), 1);
        assertTrue(nfa1.getMap().containsKey((State) state0));
    }

    @Test 
    public void testNewTransition(){
        State state0 = (State) nfa1.makeNewState();
        assertEquals(nfa1.getMap().size(), 1);
        assertTrue(nfa1.getStartState().equals(state0));
        assertTrue(nfa1.getMap().containsKey((State) state0));

        State state1 = (State) nfa1.makeNewState();
        assertEquals(nfa1.getMap().size(), 2);
        assertTrue(nfa1.getMap().containsKey((State) state1));

        nfa1.makeNewTransition(state0, 'a', state1);
    }

    @Test 
    public void testStartState(){
        State state0 = (State) nfa1.makeNewState();
        assertTrue(nfa1.getStartState().equals(state0));
    }
}