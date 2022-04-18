package futoshikigenerator;

import futoshikisolver.*;

/**
 * The state of the level. Acts as a flyweight for a Futoshiki for optimisation.
 * Stores all information required to build the puzzle.
 * 
 * @author Connor Campbell
 * @version April 2022
 */
import java.util.*;

class State {
	
	private Stack<Assign> assignStack = new Stack<Assign>();
	private Futoshiki cachePuzzle;
	
	private boolean testMode = false;
	
  /**
   * A trace method for debugging (active when testMode is true)
   * 
   * @param str the string to output
   */
	
	public void testOutput(String str){
		if(this.testMode) {
			System.out.println(str); 
		}
	}
	
  /**
   * Creates a deep clone of the state.
   * 
   * @return State | the clone
   */
	
	@SuppressWarnings("unchecked")
	@Override
	public State clone() {
		State newState = new State();
		Stack<Assign> newAS = (Stack<Assign>) getAS().clone();
		newState.cachePuzzle = null;
		newState.setAS(newAS);
		
		return newState;
	}
	
	public void testPuzzleBuild() {
		try {
			buildPuzzle();
		} catch(Exception e) {
			System.out.println("Puzzle cannot be built: "+ e); //$NON-NLS-1$
		}
	}
	
	void addAssign(Assign desc) {
		testOutput("assign to assign stack: " + desc.toString()); //$NON-NLS-1$
		this.assignStack.push(desc);
		this.cachePuzzle = null;
	}
	
	private Futoshiki buildPuzzle() {
		testOutput("Building puzzle"); //$NON-NLS-1$
		Futoshiki puzzle = InstanceGenerator.cloneBasePuzzle();
		testOutput("stack size " + this.assignStack.size()); //$NON-NLS-1$
		if(this.assignStack.size() > 0 ) {
			for(int i = 0; i < this.assignStack.size(); i++) {
				int r = this.assignStack.get(i).getRow();
				int c = this.assignStack.get(i).getCol();
				int v = this.assignStack.get(i).getNum();
				testOutput(r + ", " + c + " assigned " + v + " from stack."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				puzzle.assign(r,c,v);
			} 
		}
		return puzzle;
	}
	
	public Futoshiki getPuzzle() {
		if(Objects.isNull(this.cachePuzzle)) {
			this.cachePuzzle = buildPuzzle();
			testOutput("Puzzle not cached"); //$NON-NLS-1$
		} else { testOutput("Puzzle found in cache");} //$NON-NLS-1$

		return this.cachePuzzle;
	}
	
	public Stack<Assign> getAS(){
		return this.assignStack;
	}
	
	public void setAS(Stack<Assign> AS) {
		this.assignStack = AS;
	}
	
	void showAS() {
		Stack<Assign> as = getAS();
		for(int i = 0; i < as.size(); i++) {
			
			System.out.print(as.get(i).toString() + System.lineSeparator());
		}
	}
	
	public boolean testFeasable() {
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(getPuzzle().getSet(r, c).size() < 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean testForSolution() {
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(getPuzzle().isAssigned(r,c) == false) {
					return false;
				}
			}
		}
		return true;
	}
}
