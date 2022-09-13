package futoshikigenerator;


import futoshikisolver.*;
import java.util.*;

/**
 *	Backtracking solver. This backtracker attempts to search through the puzzle,
 *	checking through all of the branches of the solution tree in an attempt to
 *	locate solutions.
 * 
 * @author Connor Campbell
 * @todo
 * @version April 2022
 */

class Backtracker {
	private Stack<Level> levelStack = new Stack<Level>();
	private Vector<Futoshiki> solutions = new Vector<Futoshiki>();
	private int deadEndCount = 0;
	public Backtracker() {
		State startingState = new State();
		Level startingLevel = new Level(startingState);
		
		addLevel(startingLevel);
	}
	
	/**
	* testOutput(String str)
	* 
	* Class specific console logging, will only log when testMode boolean is
	* true. Used purely for debugging and testing.
	*
	* @param String str   The string to be written to console.
	*/
	
	private boolean testMode = true;
	private void testOutput(String str){
		if(this.testMode) {
			System.out.println(str);
		}
	}

	/**
	* getSolutions()
	* 
	* Returns a vector containing all of the futoshiki classed as complete solutions.
	*
	* @return Vector<Futoshiki>  $[var]   [Description]
	*/
	
	public Vector<Futoshiki> getSolutions() {
		return this.solutions;
	} 
	
	/**
	* testPuzzle()
	* 
	* Tests if the puzzle has more than 1 solution and if it does return false.
	*
	* @return boolean
	*/
	
	public boolean testPuzzle() {
		if (getSolutions().size() != 1) {
			return false;
		}
		return true;
	}

	/**
	* getDepth() 
	* 
	* Returns the size of the level stack to see how many nodes deep the solver is
	* down the solving tree.
	*
	* @return int
	*/

	public int getDepth() {
		return this.levelStack.size();
	}
	
	/**
	* addLevel(Level lvl)
	* 
	* Adds a Level to the top of the levelStack.
	*
	* @param Level lvl   The level to be added to the stack.
	*/
	
	private void addLevel(Level lvl) {
		this.levelStack.push(lvl);
	}
	
	/**
	* currentLevel() 
	* 
	* Gets the Level at the top of the levelStack as this is the current Level the solver
	* should be acting on
	*
	* @return Level
	*/
	
	private Level currentLevel() {
		return this.levelStack.peek();
	}
	
	/**
	* reduceLevel()
	* 
	* Removes the level from the top of the stack in order to return the solver to the
	* previous node.
	*/
	
	private void reduceLevel() {
		this.levelStack.pop();
	}
	
   /**
   * Traces the puzzle by checking all potential assigns at every level 
   * in order to find solutions, and returns in boolean the uniqueness of the puzzle.
   * Provided there is at least 1 Level in the levelStack, the currentLevel will be 
   * traced to see if the depth should be increased or reduced
   * 
   * @return boolean | true if the puzzle has verified uniquness, false if it does not
   */
	
	boolean tracePuzzle() {
		testOutput("Depth: 0 | Beginning Trace | Next "+ currentLevel().nextAssign().toString());
		if(this.testMode) {
			//currentLevel().getState().getPuzzle().display();
		} 
		
		while(getDepth() > 0) {
			testOutput("----- Starting next level -----");
			if(this.testMode) {
				currentLevel().getState().showAS();
				String fullString = "PA Stack: ";
				for(int i = 0; i < currentLevel().getPA().size(); i++) {
					fullString += "(" + currentLevel().getPA().get(i).getRow() + "," +  currentLevel().getPA().get(i).getCol() + "|" + currentLevel().getPA().get(i).getNum() + ")";
				}
				System.out.println(fullString);
			}
			if(this.solutions.size() >= 2) {
				if(this.testMode) {
					for(int i = 0; i < this.solutions.size(); i++) {
						//this.solutions.get(i).display();
					}
				}
				break;
			}
			if(traceLevel(currentLevel())) {
				Level newLevel = currentLevel().nextLevel();
				testOutput("Depth: "+ this.levelStack.size() +" | Increasing depth | PA Remaining "+ currentLevel().getPA().size());
				addLevel(newLevel);
			}
			else {
				testOutput("Depth: "+ this.levelStack.size() +" | Reducing depth.");
				reduceLevel();
			}
		}
		testOutput("Solutions found: " + this.solutions.size());
		testOutput("Dead ends found: " + this.deadEndCount);
		if(this.solutions.size() == 1) {
			System.out.println("Unique Puzzle Found");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	* traceLevel(Level lvl)
	* 
	* Checks the supplied Level to dictate the next move in the solver. 3 tests are performed, a feasibility test,
	* a solution test, and a check for more PA. If the puzzle is deemed infeasible, false is returned. If the current
	* Level contains a solution, the solution is added to the solutions vector and false is returned. If the PA stack
	* size is less than 1, false is returned. If all of these checks are passed, true is returned.
	*
	* @param Level lvl
	*
	* @return boolean
	*/
	
	private boolean traceLevel(Level lvl) {
		if(lvl.getState().testForSolution()) {
			if(!this.solutions.contains(lvl.getState().getPuzzle())) {
				this.solutions.add(lvl.getState().getPuzzle());
			}
			return false;
		}
		if(!lvl.getState().testFeasable()) {
			this.deadEndCount++;
			testOutput("Puzzle Infeasable");
			return false;
		}
		if(lvl.getPA().size() < 1) {
			this.deadEndCount++;
			testOutput("No more PA");
			return false;
		}
		testOutput("Puzzle checks complete.");
		return true;
	}
	
}
