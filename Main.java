import nfa.*;

public class Main {
    public static void main(String[] args) {
      if (args.length != 2) {
        System.out.println("Usage: java Main [regex] [string]");
  	    System.out.println("       Put regex in quotes to avoid shell parsing weirdness");
  	    return;
  	}

  	Parser p = new Parser(args[0]);
    Regex r = p.parse();
    
    System.out.println(r.toString());
    
  	NFA nfa = new NFA(r);
  	if (nfa.getMatch(args[1], 1)) {
      System.out.println("yes");
    }
  	else {
      System.out.println("no");
  	}
  }
}

// public class Main {
//   public static void main(String [] args) {
//       long start = System.nanoTime();
//       testPerformance();
//       long end1 = System.nanoTime();
//       testPerformance2();
//       long end2 = System.nanoTime();
//       System.out.println("The running time of parallel program is :" + (end1 - start) + " ns");
//       System.out.println("The running time of serial program is   :" + (end2 - end1) + " ns");
//   }

//   static void testPerformance(){
//       Regex r = new RStar(new ROr(new RSeq(new RChar('a'),new RChar('b')),new RSeq(new RChar('c'),new RChar('d'))));
//       NFA nfa = new NFA(r);
//       assert  nfa.getMatch("abababababababababababababababababababababababababababababababab",1);
//       assert  nfa.getMatch("abababababababababababababababababababababababababababababababab",1);
//       assert  nfa.getMatch("abababcdcdcdababababababababababababababcdcdcdababababababababababab", 1);
//       assert !nfa.getMatch("abababababacabababababababababababababababacabababababababababab",1);
//       assert !nfa.getMatch("abababadabababababababababababababababadabababababababababababab",1);
//       assert  nfa.getMatch("cdcdcdcdcdabababababababababababcdcdcdcdcdababababababababababab", 1);
//   }

//   static void testPerformance2(){
//       Regex r = new RStar(new ROr(new RSeq(new RChar('a'),new RChar('b')),new RSeq(new RChar('c'),new RChar('d'))));
//       NFA nfa = new NFA(r);
//       assert  nfa.getMatch("abababababababababababababababababababababababababababababababab",1);
//       assert  nfa.getMatch("abababababababababababababababababababababababababababababababab",1);
//       assert  nfa.getMatch("abababcdcdcdababababababababababababababcdcdcdababababababababababab",1);
//       assert !nfa.getMatch("abababababacabababababababababababababababacabababababababababab",1);
//       assert !nfa.getMatch("abababadabababababababababababababababadabababababababababababab",1);
//       assert  nfa.getMatch("cdcdcdcdcdabababababababababababcdcdcdcdcdababababababababababab",1);
//   }
// }