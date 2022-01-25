package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class Backtracer {
	private Stack<Vector<Integer[]>> SolveStack = new Stack<Vector<Integer[]>>();
	private Vector<ArrayList<Integer>> CN = new Vector<ArrayList<Integer>>();
	private Vector<ArrayList<Integer>> unusedCN = new Vector<ArrayList<Integer>>();
	
	public void setCN(Futoshiki puzzle) {
		int count = 1;
		CN.add(0, null);
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				ArrayList<Integer> candidates = new ArrayList<>(puzzle.getSet(r, c));
				this.CN.add(count, candidates);
				//Futoshiki.trace(count + " | r"+r+", c "+c+ " | " + candidates.toString());
				count++;
			}
		}
		for(int i = 1; i <= Futoshiki.SETSIZE*Futoshiki.SETSIZE; i++) {
			//this.usedCN.add(i, null);
			Futoshiki.trace(i + " | " + CN.get(i).toString());
		}
		this.unusedCN = CN;
	}
	
	public void assignNextCN(Futoshiki puzzle, int location) {
		ArrayList<Integer> currentCN = unusedCN.get(location);
		int assignVal = currentCN.get(0);
		
		puzzle.assign(assignVal, location, assignVal);
	}
	
	public int[] getRC(int val) {
		int c = val%Futoshiki.SETSIZE;
		int r = (val - c) / Futoshiki.SETSIZE;
		
		
		
		return array;
	}
}
