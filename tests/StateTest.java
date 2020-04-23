import static org.junit.Assert.*;
import org.junit.Test;
import nfa.*;

public class StateTest {
    @Test 
    public void testName(){
        State s = new State(1);
        assertEquals(s.toString(), "s1");
    }
}