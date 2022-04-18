package futoshikigenerator;


import futoshikisolver.*;
import java.util.*;

/**
 *	Backtracking solver. This backtracker attempts to search through the puzzle,
 *	checking through all of the branches of the solution tree in an attempt to
 *	locate solutions.
 * 
 * @author Connor Campbell
 * @version April 2022
 */

public class Backtracker {
	private Stack<Level> levelStack = new Stack<Level>();
	//private Set<Futoshiki> solutions = new HashSet<Futoshiki>();
	private Vector<Futoshiki> solutions = new Vector<Futoshiki>();
	private int deadEndCount = 0;
	public Backtracker() {
		State startingState = new State();
		Level startingLevel = new Level(startingState);
		
		addLevel(startingLevel);
	}
	
	private boolean testMode = false;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str);
		}
	}
	
	
//	public Set<Futoshiki> getSolutions() {
//		return solutions;
//	}
	
	public Vector<Futoshiki> getSolutions() {
		return solutions;
	}
	
	public boolean testPuzzle() {
		if (getSolutions().size() != 1) {
			return false;
		}
		return true;
	}
	
	public int getDepth() {
		return levelStack.size();
	}
	
	public void addLevel(Level lvl) {
		levelStack.push(lvl);
	}
	
	public Level currentLevel() {
		return levelStack.peek();
	}
	
	public void reduceLevel() {
		levelStack.pop();
	}
	
	public Level nextLevel() {
		State newState = new State();
		Assign newAssign = currentLevel().nextAssign();
		newState.addAssign(newAssign);
		
		Level newLevel = new Level(newState);
		
		return newLevel;
	}
	
   /**
   * Traces the puzzle by checking all potential assigns at every level 
   * in order to find solutions, and returns in boolean the uniqueness of the puzzle.
   * 
   * @return boolean | true if the puzzle has verified uniquness, false if it does not
   */
	
	public boolean tracePuzzle() {
		testOutput("Depth: 0 | Beginning Trace | Next "+ currentLevel().nextAssign().toString());
		if(this.testMode) {
			currentLevel().getState().getPuzzle().display();
		} 
		
		while(getDepth() > 0) {
			testOutput("----- Starting next level -----");
			if(this.testMode) {
				currentLevel().getState().showAS();
			}
			if(this.solutions.size() >= 2) {
				if(this.testMode) {
					for(int i = 0; i < this.solutions.size(); i++) {
						this.solutions.get(i).display();
					}
				}
				break;
			}
			if(traceLevel(currentLevel())) {
				Level newLevel = currentLevel().nextLevel();
				testOutput("Depth: "+ this.levelStack.size() +" | Increasing depth.");
				if(this.testMode) {
					newLevel.getState().getPuzzle().display();
				}
				
				addLevel(newLevel);
			}
			else {
				testOutput("Depth: "+ this.levelStack.size() +" | Reducing depth.");
				reduceLevel();
			}
		}
		testOutput("Solutions found: " + solutions.size());
		testOutput("Dead ends found: " + deadEndCount);
		if(solutions.size() == 1) {
			System.out.println("Unique Puzzle Found");
			return true;
		} else {
			//System.out.println("Puzzle is not unique");
			return false;
		}
	}
	
	// Returns true if depth can be increased, false if puzzle is infeasable or solved
	public boolean traceLevel(Level lvl) {
		if(lvl.getState().testForSolution()) {
			if(!solutions.contains(lvl.getState().getPuzzle())) {
				solutions.add(lvl.getState().getPuzzle());
			}
			return false;
		}
		if(!lvl.getState().testFeasable()) {
			deadEndCount++;
			testOutput("Puzzle Infeasable");
			return false;
		}
		if(lvl.getPA().size() < 1) {
			deadEndCount++;
			testOutput("No more PA");
			return false;
		}
		testOutput("Puzzle checks complete.");
		return true;
		
	}
	
}
