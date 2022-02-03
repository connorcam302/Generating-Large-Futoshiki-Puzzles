package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class State {
	
	private Stack<Assign> AssignStack = new Stack<Assign>();
	private Boolean	validSolution = false;
	
	public State() {

	}
	
	public void addAssign(Assign desc) {
		System.out.println("assign to stack: " + desc.toString());
		AssignStack.push(desc);
	}
	
	public void removeLast() {
		AssignStack.pop();
	}
	
	public Futoshiki buildPuzzle() {
		Futoshiki puzzle = InstanceGenerator.basePuzzle;
		System.out.println("stack size " + AssignStack.size());
		if(AssignStack.size() > 0 ) {
			for(int i = 0; i < AssignStack.size(); i++) {
				int r = AssignStack.get(i).getRow();
				int c = AssignStack.get(i).getCol();
				int v = AssignStack.get(i).getNum();
				puzzle.assign(r,c,v);
				System.out.println(r + ", " + c + " assigned " + v);
			}
		}
		
		return puzzle;
	}
	
	public int solvedCellCount() {
		Futoshiki puzzle = new Futoshiki();
		puzzle = buildPuzzle();
		Futoshiki.trace(puzzle.toString());
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
}
