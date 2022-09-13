                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

/**
* Level
* 
* Creates a level for Backtracker to work at, these act as nodes in the solving tree.
*
* @author Connor Campbell W18003255
* @todo
*
* @version April 2020
*/

class Level {
	private State levelState = new State();
	private Stack<Assign> potentialAssigns;
	
	Level(State state) {
		this.levelState = state;
	}
	
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
	
   /* 
   * clone()
   *
   * Creates a deep clone of the level.
   * 
   * @return Level | the clone
   */
	
	public Level clone() {
		Level newLevel = new Level(getState().clone());
		return newLevel;
	}

	/**
	* makeTestPuzzle()
	* 
	* Creates a copy of the base puzzle and assigns it all of the assigns relavent
	* to the current level based on this level's state.
	*
	* @return Futoshiki
	*/
	
	private Futoshiki makeTestPuzzle() {
		Futoshiki testPuzzle = InstanceGenerator.cloneBasePuzzle();
		Stack<Assign> toAssign = getState().getAS();
		for(int i = 0; i < toAssign.size();i++) {
			testPuzzle.assign(toAssign.get(i));
		}
		
		return testPuzzle;
	}
	
	/**
	* buildPA()
	* 
	* Retrieves the set of potential assigns for each cell, tests each one for validity
	* at this level by attempting to add it to a test puzzle. All of the assigns that are
	* able to be added without throwing an error are added to potentialAssigns
	*/
	
	private void buildPA() {
		testOutput("----- Building PA -----");
		Stack<Assign> newPA = new Stack<Assign>();
		Futoshiki puzzle = getState().getPuzzle();													
		Futoshiki testPuzzle;	
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				ArrayList<Integer> candidates = new ArrayList<>(puzzle.getSet(r, c));				
				if(candidates.size() > 1) {															
					for(int i = 0; i < candidates.size(); i++) {
						Assign assign = new Assign(r,c,candidates.get(i));							
						testPuzzle = makeTestPuzzle();												
						try { 																		
							testPuzzle.assign(assign);
							
							newPA.push(assign);														
							testOutput(assign.toString() + "added to PA stack.");
						} catch(Exception e) {
							testOutput(assign.toString() + "could not be added to PA stack.");
						}
					}
				}
			}
		}
		this.potentialAssigns = newPA;
	}
	
	/**
	* getPA()
	* 
	* If potentialAssigns is empty, runs buildPA. Returns potentialAssigns.
	*
	* @return Stack<Assign>
	*/
	
	public Stack<Assign> getPA() {
		if(Objects.isNull(this.potentialAssigns)) {
			testOutput("PA not built.");
			buildPA();
		}
		testOutput("PA built.");
		return this.potentialAssigns;
	}

	/**
	* getPA()
	* 
	* If potentialAssigns is empty, runs buildPA. Gets all of the potential assigns for a 
	* specific cell and returns them in a Stack.
	*
	* @return Stack<Assign>
	*/

	private Stack<Assign> getPA(int r, int c) {
		if(Objects.isNull(this.potentialAssigns)) {
			testOutput("PA not built.");
			buildPA();
		}
		testOutput("PA built.");
		
		Stack<Assign> cellPA = new Stack<Assign>();
		Assign currAssign;
		
		for(int i = 0; i < this.potentialAssigns.size();i++) {
			currAssign = this.potentialAssigns.get(i);
			if(currAssign.getRow() == r && currAssign.getCol() == c) {
				cellPA.add(currAssign);
			}
		}
		
		return cellPA;
	}
	
	/**
	* getSingles()
	* 
	* Checks if any of the empty cells have only 1 potential assign from the potentialAssign
	* stack. Returns a Vector<Assign> of these singles.
	*
	* @return Vector<Assign>
	*/
	
	public Vector<Assign> getSingles() {
		testOutput("Getting singles");
		Stack<Assign> currCellPA;
		Vector<Assign> singles = new Vector<Assign>();
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				currCellPA = getPA(r,c);
				if(currCellPA.size() == 1) {
					singles.add(currCellPA.get(0));
				}
			}
		}
		return singles;
	}
	
	/**
	* getState()
	* 
	* Retrieves the state associated with this level.
	*
	* @return State
	*/
	
	public State getState() {
		return this.levelState;
	}
	
	/**
	* nextAssign()
	* 
	* Checks for singles and will attempt to return these first, if no singles are available, returns
	* the assign at the top of the stack.
	*
	* @return Assign
	*/
	
	public Assign nextAssign() {
		testOutput("Getting next assign");
		Stack<Assign> assigns = getPA();
		Vector<Assign> singles = getSingles();
		if(singles.size() > 0) {
			return singles.get(0);
		}
		Assign newAssign = assigns.pop();
		return newAssign;
	}
	
	/**
	* nextLevel()
	* 
	* Creates the next level by using one of the potential assigns to create the state at the next level.
	*
	* @return Level
	*/
	
	public Level nextLevel() {
		testOutput("Making next level");
		State newState = getState().clone();
		newState.addAssign(nextAssign());
		this.potentialAssigns.remove(nextAssign());
		
		Level lvl = new Level(newState);
		return lvl;
	}

}
