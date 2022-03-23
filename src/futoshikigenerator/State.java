package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class State {
	
	private Stack<Assign> assignStack = new Stack<Assign>();
	private Futoshiki cachePuzzle;
	
	public State() {
		
	}
	
	public State(Stack<Assign> AS) {
		assignStack = AS;
	}
	
	private boolean testMode = false;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str); 
		}
	}
	
	@SuppressWarnings("unchecked")
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
			System.out.println("Puzzle cannot be built: "+ e);
		}
	}
	
	public void addAssign(Assign desc) {
		testOutput("assign to assign stack: " + desc.toString());
		assignStack.push(desc);
		cachePuzzle = null;
	}
	
	public void removeLast() {
		assignStack.pop();
	}
	
	private Futoshiki buildPuzzle() {
		testOutput("Building puzzle");
		Futoshiki puzzle = InstanceGenerator.basePuzzle.clone();
		testOutput("stack size " + assignStack.size());
		if(assignStack.size() > 0 ) {
			for(int i = 0; i < assignStack.size(); i++) {
				int r = assignStack.get(i).getRow();
				int c = assignStack.get(i).getCol();
				int v = assignStack.get(i).getNum();
				testOutput(r + ", " + c + " assigned " + v + " from stack.");
				puzzle.assign(r,c,v);
				
			} 
		}
		return puzzle;
	}
	
	public Futoshiki getPuzzle() {
		if(Objects.isNull(cachePuzzle)) {
			cachePuzzle = buildPuzzle();
			testOutput("Puzzle not cached");
		} else { testOutput("Puzzle found in cache");}

		return cachePuzzle;
	}
	
	public Stack<Assign> getAS(){
		return assignStack;
	}
	
	public void setAS(Stack<Assign> AS) {
		assignStack = AS;
	}
	
	public void showAS() {
		Stack<Assign> as = getAS();
		for(int i = 0; i < as.size(); i++) {
			
			System.out.print(as.get(i).toString() + System.lineSeparator());
		}
	}
	
	public boolean testFeasable() {
		boolean quality = true;
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(getPuzzle().getSet(r, c).size() < 1) {
					quality = false;
				}
			}
		}
		return quality;
	}
	
	public boolean testForSolution() {
		boolean quality = true;
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(getPuzzle().isAssigned(r,c) == false) {
					quality = false;
				}
			}
		}
		return quality;
	}
}
