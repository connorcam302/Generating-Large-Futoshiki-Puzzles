package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

public class InstanceGenerator {
	private int constraintAmount = 0;
	private int startingValueAmount = 0;
	
	private int numCount = 2;
	private int relCount = 4;
	
	public static Futoshiki basePuzzle;
	
	private boolean testMode = false;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str);
		}
	}
	
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
			 testOutput("Assigned: " + selected + " to " + Arrays.toString(emptyCell));
		 }
		 
		 return puzzle;
	 }
	 
	public Futoshiki generateRelations(Futoshiki puzzle) {
		for(int i = 0; i < getRelCount(); i++) {
			int r = randomByMax(Futoshiki.SETSIZE)+1;
			int c = randomByMax(Futoshiki.SETSIZE)+1;
			boolean validRelation = false;
			ArrayList<Integer> validDirection = new ArrayList<Integer>(); 
			while(validDirection.size() < 1) {
				r = randomByMax(Futoshiki.SETSIZE)+1;
				c = randomByMax(Futoshiki.SETSIZE)+1;
				testOutput("finding valid direction | " + r + "," + c);
				validDirection = new ArrayList<Integer>();
				if(puzzle.isValidRelation(r,c,r+1,c)) {
					validDirection.add(1);
				}
				if(puzzle.isValidRelation(r+1,c,r,c)) {
					validDirection.add(2);
				}
				if(puzzle.isValidRelation(r,c,r,c+1)) {
					validDirection.add(3);
				}
				if(puzzle.isValidRelation(r,c+1,r,c)) {
					validDirection.add(4);
				}
				if(puzzle.isValidRelation(r,c,r-1,c)) {
					validDirection.add(5);
				}
				if(puzzle.isValidRelation(r-1,c,r,c)) {
					validDirection.add(6);
				}
				if(puzzle.isValidRelation(r,c,r,c-1)) {
					validDirection.add(7);
				}
				if(puzzle.isValidRelation(r,c-1,r,c)) {
					validDirection.add(8);
				}
			}
			
			Integer dir = validDirection.get(randomByMax(validDirection.size()));
			switch(dir) {
			case 1:
				puzzle.addRelation(r, c, r+1, c);
				break;
			case 2:
				puzzle.addRelation(r+1, c, r, c);
				break;
			case 3:
				puzzle.addRelation(r, c, r, c+1);
				break;
			case 4:
				puzzle.addRelation(r, c+1, r, c);
				break;
			case 5:
				puzzle.addRelation(r, c, r-1, c);
				break;
			case 6:
				puzzle.addRelation(r-1, c, r, c);
				break;
			case 7:
				puzzle.addRelation(r, c, r, c-1);
				break;
			case 8:
				puzzle.addRelation(r, c-1, r, c);
				break;
			}
		}
		return puzzle;
	}
	
	
	public Futoshiki makeInstance() {
		Futoshiki puzzle = new Futoshiki();
		generateRelations(puzzle);
		generateNumbers(puzzle);
		System.out.println("--------------");
		puzzle.display();
		basePuzzle = puzzle;
		return puzzle;
	}
	
}
