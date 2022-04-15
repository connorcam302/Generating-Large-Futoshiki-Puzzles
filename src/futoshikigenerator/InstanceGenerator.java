package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

public class InstanceGenerator {
	private int numCount = Futoshiki.SETSIZE-2;
	private int relCount = ((Futoshiki.SETSIZE-3)*4)+2;
	
	public static Futoshiki basePuzzle;
	
	private boolean testMode = false;
	
	public static Vector<Assign> assigns = new Vector<Assign>();
	public static Vector<RelEntry> relations = new Vector<RelEntry>();
	
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
	
	public int randomByMax(int max) {
		return (int)(Math.random() * max);
	}
	
	public Vector<Assign> getAssigns(){
		return assigns;
	}
	
	public Vector<RelEntry> getRelations(){
		return relations;
	}
	
	 public Futoshiki generateNumbers(Futoshiki puzzle) { 
		 assigns = new Vector<Assign>();
		 for(int i = 0; i < getNumCount(); i++) {
			 ArrayList<int[]> empties = puzzle.getEmptyCells();
			 if(empties.size() == 0) {
				 break;
			 }
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
			 
			 Assign ass = new Assign(er,ec,selected);
			 assigns.add(ass);
			 try {
				 puzzle.assign(er,ec,selected);
				 testOutput("Assigned: " + selected + " to " + Arrays.toString(emptyCell));
			 } catch(Exception e) {
				 i--;
			 }
		 }
		 
		 return puzzle;
	 }
	 
	public Futoshiki generateRelations(Futoshiki puzzle) {
		relations = new Vector<RelEntry>();
		for(int i = 0; i < getRelCount(); i++) {
			int r = randomByMax(Futoshiki.SETSIZE)+1;
			int c = randomByMax(Futoshiki.SETSIZE)+1;
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
			try {
				switch(dir) {
				case 1:
					relations.add(new RelEntry(r,c,r+1,c));
					puzzle.addRelation(r,c,r+1,c);
					break;
				case 2:
					relations.add(new RelEntry(r+1,c,r,c));
					puzzle.addRelation(r+1, c, r, c);
					break;
				case 3:
					relations.add(new RelEntry(r,c,r,c+1));
					puzzle.addRelation(r, c, r, c+1);
					break;
				case 4:
					relations.add(new RelEntry(r,c+1,r,c));
					puzzle.addRelation(r, c+1, r, c);
					break;
				case 5:
					relations.add(new RelEntry(r,c,r-1,c));
					puzzle.addRelation(r, c, r-1, c);
					break;
				case 6:
					relations.add(new RelEntry(r-1,c,r,c));
					puzzle.addRelation(r-1,c,r,c);
					break;
				case 7:
					relations.add(new RelEntry(r,c,r,c-1));
					puzzle.addRelation(r, c, r, c-1);
					break;
				case 8:
					relations.add(new RelEntry(r,c-1,r,c));
					puzzle.addRelation(r, c-1, r, c);
					break;
				}
			} catch(Exception e) {
				i--;
			}
			
		}
		return puzzle;
	}
	
	
	public Futoshiki makeInstance() {
		Futoshiki puzzle = new Futoshiki();
		generateRelations(puzzle);
		generateNumbers(puzzle);
		if(testMode) {
			System.out.println("--------------");
			puzzle.display();
		}
		basePuzzle = puzzle;
		return puzzle;
	}
	
	public static Futoshiki cloneBasePuzzle() {
		Futoshiki newPuzzle = new Futoshiki();
		Vector<RelEntry> rels = InstanceGenerator.relations;
		Vector<Assign> assigns = InstanceGenerator.assigns;
		for(int i = 0; i < rels.size(); i++) {
			int gr = rels.get(i).getGreaterRow();
			int gc = rels.get(i).getGreaterCol();
			int lr = rels.get(i).getLesserRow();
			int lc = rels.get(i).getLesserCol();

			newPuzzle.addRelation(gr, gc, lr, lc);
			
		}
		for(int i = 0; i < assigns.size(); i++) {
			newPuzzle.assign(assigns.get(i));
		}
		return newPuzzle;
	}
}
