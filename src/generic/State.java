package generic;

/*
   A state defines the information necessary to describe the orientations of the objects inside of the world. 
   The generic class for state is empty as each problem has a unique requirements to  define the world of the 
   problem. Furthermore, states  should be comparable to be  able to compare between  the state in the manner 
   the problem requires.
 */

public abstract class State implements Comparable<State>{
}