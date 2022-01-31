package futoshikigenerator;

import futoshikisolver.*;
import java.util.*;

public class State {
	
	private Stack<Assign> AssignStack = new Stack<Assign>();
	
	public void addAssign(Assign desc) {
		AssignStack.push(desc);
	}
	
	public void removeLast() {
		AssignStack.pop();
	}
	
	public Futoshiki buildPuzzle() {
		Futoshiki puzzle = InstanceGenerator.basePuzzle;
		if(AssignStack.size() > 0 ) {
			for(int i = 0; i < AssignStack.size(); i++) {
				int r = AssignStack.get(i).getRow();
				int c = AssignStack.get(i).getCol();
				int v = AssignStack.get(i).getNum();
				puzzle.assign(r,c,v);
			}
		}
		
		return puzzle;
	}
}
