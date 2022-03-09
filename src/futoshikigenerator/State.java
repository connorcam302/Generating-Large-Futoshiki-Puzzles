package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class State {
	
	private Stack<Assign> assignStack = new Stack<Assign>();
	private Boolean	validSolution = false;
	private Boolean invalidPuzzle = false;
	private Futoshiki cachePuzzle;
	
	public State() {
		
	}
	
	public State(Stack<Assign> AS) {
		assignStack = AS;
	}
	
	private boolean testMode = true;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str); 
		}
	}
	
	public void testPuzzleBuild() {
		try {
			buildPuzzle();
		} catch(Exception e) {
			System.out.println("Puzzle cannot be built: "+ e);
		}
	}
	
	public void addAssign(Assign desc) {
		testOutput("assign to stack: " + desc.toString());
		assignStack.push(desc);
		cachePuzzle = null;
	}
	
	public void removeLast() {
		assignStack.pop();
	}
	
	private Futoshiki buildPuzzle() {
		Futoshiki puzzle = InstanceGenerator.basePuzzle;
		testOutput("stack size " + assignStack.size());
		if(assignStack.size() > 0 ) {
			do {
				for(int i = 0; i < assignStack.size(); i++) {
					int r = assignStack.get(i).getRow();
					int c = assignStack.get(i).getCol();
					int v = assignStack.get(i).getNum();
					puzzle.assign(assignStack.get(i));
					testPuzzle();
					testOutput(r + ", " + c + " assigned " + v);
				} 
			} while(validSolution == false && invalidPuzzle == false);
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
	
	public int solvedCellCount() {
		Futoshiki puzzle = new Futoshiki();
		puzzle = getPuzzle();
		int count = 0;
		
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				int setSize = puzzle.getSet(r, c).size();
				if(setSize == 1) {
					count++;
				}
			}
		}
		return count;
	}
	
	public boolean isSolved() {
		int solvedCount = solvedCellCount();
		if(solvedCount == Futoshiki.SETSIZE*Futoshiki.SETSIZE) {
			validSolution = true;
		}
		return validSolution;
	}
	
	public Stack<Assign> getAS(){
		return assignStack;
	}
	
	public void setAS(Stack<Assign> AS) {
		assignStack = AS;
	}
	
	public void showAS() {
		for(int i = 0; i < assignStack.size(); i++) {
			System.out.println(assignStack.get(i));
		}
	}
	
	public void testPuzzleFeasable() {
		boolean quality = true;
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(getPuzzle().getSet(r, c).size() < 1) {
					quality =  false;
				}
			}
		}
		invalidPuzzle = quality;
	}
	
	public void testPuzzleSolved() {
		boolean quality = true;
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(getPuzzle().isAssigned(r,c) == false) {
					quality = false;
				}
			}
		}
		validSolution = quality;
	}
	
	public void testPuzzle() {
		testPuzzleFeasable();
		testPuzzleSolved();
		
		if(validSolution == true) {
			System.out.print("Solution has been found.");
		}
		if(invalidPuzzle == true) {
			System.out.print("Puzzle deemed invalid.");
		}
	}
}
