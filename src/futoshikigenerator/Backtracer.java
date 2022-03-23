package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class Backtracer {
	private Stack<Level> levelStack = new Stack<Level>();
	private HashSet<Futoshiki> solutions = new HashSet<Futoshiki>();
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
	
	
	public HashSet<Futoshiki> getSolutions() {
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
	
	public void tracePuzzle() {
		System.out.println("Depth: 0 | Beginning Trace | Next "+ currentLevel().nextAssign().toString());
		currentLevel().getState().getPuzzle().display();
		while(getDepth() > 0) {
			System.out.println("----- Starting next level -----");
			currentLevel().getState().showAS();
			if(traceLevel(currentLevel())) {
				Level newLevel = currentLevel().nextLevel();
				System.out.println("Depth: "+ levelStack.size() +" | Increasing depth.");
				newLevel.getState().getPuzzle().display();
				
				addLevel(newLevel);
			}
			else {
				System.out.println("Depth: "+ levelStack.size() +" | Reducing depth.");
				reduceLevel();
			}
		}
		System.out.println("Solutions found: " + solutions.size());
		System.out.println("Dead ends found: " + deadEndCount);
	}
	
	// Returns true if depth can be increased, false if puzzle is infeasable or solved
	public boolean traceLevel(Level lvl) {
		if(lvl.getState().testForSolution()) {
			solutions.add(lvl.getState().getPuzzle());
			
			System.out.println("Solution Found");
			return false;
		}
		if(!lvl.getState().testFeasable()) {
			System.out.println("Puzzle Infeasable");
			return false;
		}
		if(lvl.getPA().size() < 1) {
			deadEndCount++;
			System.out.println("No more PA");
			return false;
		}
		System.out.println("Puzzle checks complete.");
		return true;
		
	}
	
}
