package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class Backtracer {
	private Stack<Level> levelStack = new Stack<Level>();
	private Vector<Futoshiki> solutions = new Vector<Futoshiki>();
	private int deadEndCount = 0;
	public Backtracer() {
		State startingState = new State();
		Level startingLevel = new Level(startingState);
		
		levelStack.push(startingLevel);
	}
	
	private boolean testMode = false;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str);
		}
	}
	
	
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
	
	public void tracePuzzle() {
		State startingState = new State();
		Level startingLevel = new Level(startingState);
		addLevel(startingLevel);
		
		while(getDepth() > 0) {
			if(traceLevel(currentLevel())) {
				addLevel(nextLevel());
			}
			else reduceLevel();
		}
		System.out.println("Solutions found: " + solutions.size());
		System.out.println("Dead ends found: " + deadEndCount);
	}
	
	// Returns true if depth can be increased, false if puzzle is infeasable or solved
	public boolean traceLevel(Level lvl) {
		if(lvl.getState().testForSolution() || !lvl.getState().testFeasable()) {
			if(!solutions.contains(lvl.getState().getPuzzle())) {
				solutions.add(lvl.getState().getPuzzle());
			}
			return false;
		}
		if(lvl.getPA().size() < 1) {
			deadEndCount++;
			return false;
		}
		return true;
	}
	
}
