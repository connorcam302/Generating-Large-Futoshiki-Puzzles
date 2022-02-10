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
}
