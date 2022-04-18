package futoshikigenerator;
import futoshikisolver.*;
import java.util.Collections;
import java.util.*;

/**
 * Creates instances of a puzzle without
 * 
 * @author Connor Campbell
 * @version April 2022
 */

public class InstanceGenerator {
	private int numCount = Futoshiki.SETSIZE-2;
	private int relCount = 50;
	
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
	
	 /**
	  * Takes a puzzle and adds random PA to it.
	  * 
	  * @param Futoshiki | the puzzle to be modified.
	  * 
	  * @return Futoshiki | the modified version of the puzzle.
	  */
	
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
	
	 /**
	  * Takes a puzzle and generates adds relations to it.
	  * 
	  * @param Futoshiki | the puzzle to be modified.
	  * 
	  * @return Futoshiki | the modified version of the puzzle.
	  */
	 
		public Futoshiki generateRelations(Futoshiki puzzle) {
			relations = new Vector<RelEntry>();
			for(int i = 0; i < this.relCount; i++) {
				Vector<Integer[]> allRelations = new Vector<Integer[]>();
				Vector<RelEntry> validRelations = new Vector<RelEntry>();
				int r = randomByMax(Futoshiki.SETSIZE)+1;
				int c = randomByMax(Futoshiki.SETSIZE)+1;
				testOutput("finding valid direction | " + r + "," + c);
				allRelations.add(new Integer[] {r,c,r+1,c});
				allRelations.add(new Integer[] {r+1,c,r,c});
				allRelations.add(new Integer[] {r,c,r,c+1});
				allRelations.add(new Integer[] {r,c+1,r,c});
				allRelations.add(new Integer[] {r,c,r-1,c});
				allRelations.add(new Integer[] {r-1,c,r,c});
				allRelations.add(new Integer[] {r,c,r,c-1});
				allRelations.add(new Integer[] {r,c-1,r,c});
				
				for(int j = 0; j < allRelations.size(); j++) {
					Futoshiki testPuzzle = puzzle.clone();
					try {
						testPuzzle.addRelation(	allRelations.get(j)[0], allRelations.get(j)[1], 
												allRelations.get(j)[2], allRelations.get(j)[3]);
						validRelations.add(new RelEntry(allRelations.get(j)[0],allRelations.get(j)[1],
														allRelations.get(j)[2],allRelations.get(j)[3]));
					} catch(Exception e) {}
				}
				
				Collections.shuffle(validRelations);
				RelEntry chosenRelation = validRelations.get(0);
				puzzle.addRelation(chosenRelation.getGreaterRow(), chosenRelation.getGreaterCol(), chosenRelation.getLesserRow(), chosenRelation.getLesserCol());
				relations.add(chosenRelation);
			}
			return puzzle;
		}
	 
//		public Futoshiki generateRelations(Futoshiki puzzle) {
//			relations = new Vector<RelEntry>();
//			for(int i = 0; i < getRelCount(); i++) {
//				int r = randomByMax(Futoshiki.SETSIZE)+1;
//				int c = randomByMax(Futoshiki.SETSIZE)+1;
//				boolean validRelation = false;
//				ArrayList<Integer> validDirection = new ArrayList<Integer>(); 
//				while(validDirection.size() < 1) {
//					Futoshiki testPuzzle = puzzle.clone();
//					r = randomByMax(Futoshiki.SETSIZE)+1;
//					c = randomByMax(Futoshiki.SETSIZE)+1;
//					testOutput("finding valid direction | " + r + "," + c);
//					validDirection = new ArrayList<Integer>();
//					try {
//						testPuzzle.addRelation(r,c,r+1,c);
//						validDirection.add(1);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r+1,c,r,c);
//						validDirection.add(2);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r,c,r,c+1);
//						validDirection.add(3);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r,c+1,r,c);
//						validDirection.add(4);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r,c,r-1,c);
//						validDirection.add(5);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r-1,c,r,c);
//						validDirection.add(6);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r,c,r,c-1);
//						validDirection.add(7);
//					} catch(Exception e) {}
//					testPuzzle = puzzle.clone();
//					try {
//						testPuzzle.addRelation(r,c-1,r,c);
//						validDirection.add(8);
//					} catch(Exception e) {
//					}
//				}
//				System.out.println(validDirection.toString());
//				Integer dir = validDirection.get(randomByMax(validDirection.size()));
//				Futoshiki testPuzzle = puzzle.clone();
//				boolean success = false;
//				try {
//					switch(dir) {
//					case 1:
//						testPuzzle.addRelation(r,c,r+1,c);
//						relations.add(new RelEntry(r,c,r+1,c));
//						success = true;
//						break;
//					case 2:
//						testPuzzle.addRelation(r+1, c, r, c);
//						relations.add(new RelEntry(r+1,c,r,c));
//						success = true;
//						break;
//					case 3:
//						testPuzzle.addRelation(r, c, r, c+1);
//						relations.add(new RelEntry(r,c,r,c+1));
//						success = true;
//						break;
//					case 4:
//						
//						testPuzzle.addRelation(r, c+1, r, c);
//						relations.add(new RelEntry(r,c+1,r,c));
//						success = true;
//						break;
//					case 5:
//						testPuzzle.addRelation(r, c, r-1, c);
//						relations.add(new RelEntry(r,c,r-1,c));
//						success = true;
//						break;
//					case 6:
//						
//						testPuzzle.addRelation(r-1,c,r,c);
//						relations.add(new RelEntry(r-1,c,r,c));
//						success = true;
//						break;
//					case 7:
//						
//						testPuzzle.addRelation(r, c, r, c-1);
//						relations.add(new RelEntry(r,c,r,c-1));
//						success = true;
//						break;
//					case 8:
//						testPuzzle.addRelation(r, c-1, r, c);
//						relations.add(new RelEntry(r,c-1,r,c));
//						success = true;
//						break;
//					}
//				} catch(Exception e) {
//					i--;
//				}
//				if(success) {
//					puzzle.addRelation(	relations.get(relations.size()-1).getGreaterRow(), relations.get(relations.size()-1).getGreaterCol(), 
//										relations.get(relations.size()-1).getLesserRow(), relations.get(relations.size()-1).getLesserRow());
//				}
//				
//			}
//			return puzzle;
//		}
	
	
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
	
	public boolean testBasePuzzle() {
		boolean working = true;
		try {
			Futoshiki testPuzzle = cloneBasePuzzle();
		} catch(Exception e) {
			working = false;
		}
		return working;
	}
}
