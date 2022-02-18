package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class Backtracer {
	private Stack<Level> LevelStack = new Stack<Level>();
	private int solutionCount = 0;
	
	public Backtracer() {
		State startingState = new State();
		Level startingLevel = new Level(startingState);
		
		LevelStack.push(startingLevel);
	}
	
	private boolean testMode = false;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str);
		}
	}
	
	
	public int getSolutionCount() {
		return solutionCount;
	}
	
	public boolean testPuzzle() {
		if (solutionCount > 1) {
			return false;
		}
		return true;
	}
	
	public void addLevel(Level lvl) {
		LevelStack.push(lvl);
	}
	
	public Level currentLevel() {
		return LevelStack.peek();
	}
	
	public void reduceLevel() {
		LevelStack.pop();
	}
	
	public Level nextLevel() {
		State newState = new State();
		Assign newAssign = currentLevel().nextAssign();
		newState.addAssign(newAssign);
		
		Level newLevel = new Level(newState);
		
		return newLevel;
	}
	
	public boolean checkCurrentLevel() {
		if(this.currentLevel().getState().isSolved()) {
			solutionCount++;
			testOutput("Solution Found");
			return false;
		}
		if(this.currentLevel().getPA().size() != 0) {
			testOutput("More solutions possible");
			return true;
		}
		testOutput("No more solutions possible");
		return false;
	}
	
	public void backtraceLevel() {
			
	}
	
	public Futoshiki backtracePuzzle() {
		do {
			
		} while (testPuzzle());
	}
}
