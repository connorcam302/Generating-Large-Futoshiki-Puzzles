package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class Backtracer {
	private Stack<Decision> SolveStack = new Stack<Decision>();
	
	public int[] getRC(int val) {
		int c = val%Futoshiki.SETSIZE;
		int r = ((val - c) / Futoshiki.SETSIZE)+1;
		int[] array = {r,c};
		
		return array;
	}
	
	public Vector<ArrayList<Integer>> getCNVector(Futoshiki puzzle) {
		Vector<ArrayList<Integer>> CN = new Vector<ArrayList<Integer>>();
		int count = 1;
		CN.add(0, null);
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				ArrayList<Integer> candidates = new ArrayList<>(puzzle.getSet(r, c));
				CN.add(count, candidates);
				//Futoshiki.trace(count + " | r"+r+", c "+c+ " | " + candidates.toString());
				count++;
			}
		} 
		return CN;
	}
	
	public ArrayList<Integer> getCN(Futoshiki puzzle, int location) {
		return this.getCNVector(puzzle).get(location);
	}
	

	public void showAllCN(Futoshiki puzzle) {
		for(int i = 1; i <= Futoshiki.SETSIZE*Futoshiki.SETSIZE; i++) {
			Futoshiki.trace(i + " | " + getCNVector(puzzle).get(i).toString());
		}
	}
	
	public void removeLastDecision(Futoshiki puzzle) {
		Stack<Decision> newSolveStack = this.SolveStack;
		Futoshiki newPuzzle = InstanceGenerator.basePuzzle;
		newSolveStack.pop();
		
		for(int i = 0; i < newSolveStack.size(); i++) {
			Decision currDecision = newSolveStack.get(0);
			newPuzzle.assign(currDecision.getRow(), currDecision.getCol(), currDecision.getValue());
		}
		puzzle = newPuzzle;
		this.SolveStack = newSolveStack;
	}
	
	public void addDecision(Futoshiki puzzle, int row, int col, int val) {
		Decision desc = new Decision();
		desc.setRow(row);
		desc.setCol(col);
		desc.setValue(val);
		
		SolveStack.push(desc);
		puzzle.assign(row, col, val);
	}
	
	public boolean isSolved(Futoshiki puzzle) {
		boolean check = true;
		for(int i = 1; i <= Futoshiki.SETSIZE;i++){
			if(getCN(puzzle, i).size() != 1) {
				check = false;
			}
		}
		
		return check;
	}
}
