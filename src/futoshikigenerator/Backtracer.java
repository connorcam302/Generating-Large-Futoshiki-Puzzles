package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class Backtracer {
	private Stack<Level> levelStack = new Stack<Level>();
	//private Set<Futoshiki> solutions = new HashSet<Futoshiki>();
	private Vector<Futoshiki> solutions = new Vector<Futoshiki>();
	private int deadEndCount = 0;
	public Backtracer() {
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
	
	public boolean tracePuzzle() {
		testOutput("Depth: 0 | Beginning Trace | Next "+ currentLevel().nextAssign().toString());
		if(testMode) {
			currentLevel().getState().getPuzzle().display();
		}
		while(getDepth() > 0) {
			testOutput("----- Starting next level -----");
			if(testMode) {
				currentLevel().getState().showAS();
			}
			if(solutions.size() >= 2) {
				if(testMode) {
					for(int i = 0; i < solutions.size(); i++) {
						solutions.get(i).display();
					}
				}
				break;
			}
			if(traceLevel(currentLevel())) {
				Level newLevel = currentLevel().nextLevel();
				testOutput("Depth: "+ levelStack.size() +" | Increasing depth.");
				if(testMode) {
					newLevel.getState().getPuzzle().display();
				}
				
				addLevel(newLevel);
			}
			else {
				testOutput("Depth: "+ levelStack.size() +" | Reducing depth.");
				reduceLevel();
			}
		}
		testOutput("Solutions found: " + solutions.size());
		testOutput("Dead ends found: " + deadEndCount);
		if(solutions.size() == 1) {
			System.out.println("Unique Puzzle Found");
			return true;
		} else {
			System.out.println("Puzzle not Unique");
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
