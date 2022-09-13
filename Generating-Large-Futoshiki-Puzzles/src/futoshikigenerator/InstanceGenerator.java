package futoshikigenerator;
import futoshikisolver.*;

import java.util.*;

/**
 * Creates instances of a puzzle without
 * 
 * @author Connor Campbell
 * @version April 2022
 */

class InstanceGenerator {
	private int numCount = 3;
	private int relCount = 15;
	private int puzzleSize = 5;
	
	static Futoshiki basePuzzle;
	static Vector<Assign> assigns = new Vector<Assign>();
	static Vector<RelEntry> relations = new Vector<RelEntry>();
	
	/**
	* testOutput(String str)
	* 
	* Class specific console logging, will only log when testMode boolean is
	* true. Used purely for debugging and testing.
	*
	* @param String str | The string to be written to console.
	*/
	
	private boolean testMode = false;
	public void testOutput(String str){
		if(this.testMode) {
			System.out.println(str);
		}
	}
	
	/**
	* getNumCount()
	* 
	* Get the number of values to be inserted into the puzzle
	*
	* @return int 
	*/
	
	public int getNumCount() {
		return this.numCount;
	}
	
	/**
	* getRelCount()
	* 
	* Get the number of relations to be added to the puzzle.
	*
	* @return int
	*/
	
	public int getRelCount() {
		return this.relCount;
	}
	
	/**
	* randomByMax(int max)
	* 
	* Generates a random number between 0 and the given value.
	*
	* @param int max  The highest number that can be generated.
	*/
	
	private int randomByMax(int max) {
		return (int)(Math.random() * max);
	}
	
	/**
	* getAssigns()
	* 
	* Get the assigns used to build the basePuzzle.
	*
	* @return Vector<Assign>
	*/
	
	public Vector<Assign> getAssigns(){
		return assigns;
	}
	
	/**
	* getRelations()
	* 
	* Get the relations used to build the basePuzzle.
	*
	* @return Vector<RelEntry>
	*/
	
	public Vector<RelEntry> getRelations(){
		return relations;
	}
	
	 /**
	  * Takes a puzzle and adds random PA to it.
	  * 
	  * @param Futoshiki puzzle | the puzzle to be modified.
	  * 
	  * @return Futoshiki | the modified version of the puzzle.
	  */
	
	 private Futoshiki generateNumbers(Futoshiki puzzle) { 
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
	 
		private Futoshiki generateRelations(Futoshiki puzzle) {
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
	
	/**
	* makeInstance()
	* 
	* Creates an instance of a puzzle with unverified uniqueness
	*
	* @return Futoshiki
	*/

	Futoshiki makeInstance() {
		Futoshiki.SETSIZE = this.puzzleSize;
		Futoshiki puzzle = new Futoshiki();
		generateRelations(puzzle);
		generateNumbers(puzzle);
		if(this.testMode) {
			System.out.println("--------------");
			puzzle.display();
		}
		basePuzzle = puzzle;
		return puzzle;
	}

	/**
	* cloneBasePuzzle()
	* 
	* Using the build order stored in relations and assigns, creates a new version of the current puzzle.
	*
	* @return Futoshiki
	*/
	
	static Futoshiki cloneBasePuzzle() {
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
	
	/**
	* testBasePuzzle()
	* 
	* Attempts to build current puzzle in order to test if the puzzle is valid. Used as a final catch.
	*
	* @return boolean
	*/
	
	public boolean testBasePuzzle() {
		boolean working = true;
		try {
			@SuppressWarnings("unused")
			Futoshiki testPuzzle = cloneBasePuzzle();
		} catch(Exception e) {
			working = false;
		}
		return working;
	}
}
