package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

public class InstanceGenerator {
	private int constraintAmount = 0;
	private int startingValueAmount = 0;
	
	private int numCount = 4;
	private int relCount = 5;
	
	
	// -- Getters and Setters --
	
	public int getNumCount() {
		return this.numCount;
	}
	
	public int getRelCount() {
		return this.relCount;
	}
	
	public void setConstraintAmount(int i) {
		this.constraintAmount = i;
	}
	
	public int getConstraintAmount() {
		return this.constraintAmount;
	}
	
	public void setStartingValueAmount(int i) {
		this.startingValueAmount = i;
	}
	
	public int getStartingValueAmount() {
		return this.startingValueAmount;
	}
	
	public int randomByMax(int max) {
		return (int)(Math.random() * max);
	}
	
	
	 public Futoshiki generateNumbers(Futoshiki puzzle) { 
		 for(int i = 0; i < getNumCount(); i++) {
			 ArrayList<int[]> empties = puzzle.getEmptyCells();
			 int[] emptyCell = empties.get(randomByMax(empties.size()));
			 int er = emptyCell[0];
			 int ec = emptyCell[1];
			 
			 TreeSet<Integer> options = puzzle.getSet(er, ec);
			 int randomValue = new Random().nextInt(options.size()); 
			 int selected = -1;
			 int j=0;
			 for(int obj : options) {
				    if (j == randomValue) {
				        selected = obj;
				    }
				   j++;
				}
			 
			 puzzle.assign(er,ec,selected);
			 //Futoshiki.trace("Assigned: " + selected + " to " + Arrays.toString(emptyCell));
		 }
		 return puzzle;
	 }
	
	public Futoshiki makeInstance() {
		Futoshiki puzzle = new Futoshiki();

		generateNumbers(puzzle);
		return puzzle;
	}
}
