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
	
	/**
	* testOutput(String str)
	* 
	* Class specific console logging, will only log when testMode boolean is
	* true. Used purely for debugging and testing.
	*
	* @param String str  |  The string to be written to console.
	*/
	
	private boolean testMode = false;
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
	
	/**
	* addAssign(Assign desc)
	* 
	* Adds an assign to the AssignStack and resets the cachePuzzle.
	*
	* @param Assign desc  |  The assign to be added.
	*/
	
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
	
	/**
	* getPuzzle() 
	* 
	* Attempts to retrieve the puzzle from cachePuzzle, if this is empty, builds the puzzle and
	* sets it to cachePuzzle. Then returns cachePuzzle.
	*
	* @return Futoshiki 
	*/
	
	public Futoshiki getPuzzle() {
		if(Objects.isNull(this.cachePuzzle)) {
			this.cachePuzzle = buildPuzzle();
			testOutput("Puzzle not cached"); //$NON-NLS-1$
		} else { testOutput("Puzzle found in cache");} //$NON-NLS-1$

		return this.cachePuzzle;
	}

	/**
	* getAS()
	* 
	* Returns the current assignStack to the user.
	*
	* @return Stack<Assign>
	*/
	
	public Stack<Assign> getAS(){
		return this.assignStack;
	}
	
	/**
	* setAS(Stack<Assign> AS) 
	* 
	* Sets the assignStack to a new Stack<Assign>.
	*
	* @param Stack<Assign> AS  | The new assign stack.
	*/
	
	public void setAS(Stack<Assign> AS) {
		this.assignStack = AS;
	}
	
	/**
	* showAS() 
	* 
	* Shows the assignStack to the user in console. Used for debugging.
	*/
	
	void showAS() {
		Stack<Assign> as = getAS();
		String fullString = "Assign Stack: ";
		for(int i = 0; i < as.size(); i++) {
			fullString += "(" + as.get(i).getRow() + "," +  as.get(i).getCol() + "|" + as.get(i).getNum() + ")";
		}
		System.out.println(fullString);
	}
	
	/**
	* testFeasable()
	* 
	* Checks if any of the cells have no potential assigns, if any have no potential assigns, the puzzle is
	* infeasible and false is returned.
	*
	* @return boolean
	*/
	
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
	
	/**
	* testForSolution()
	* 
	* Tests if all of the cells are assigned, if they are the puzzle is solved and true is returned.
	*
	* @return boolean
	*/
	
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
