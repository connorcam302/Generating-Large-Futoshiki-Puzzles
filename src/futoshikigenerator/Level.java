                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

public class Level {
	private State levelState = new State();
	private Stack<Assign> potentialAssigns;
	
	public Level(State state) {
		levelState = state;
	}
	
	private boolean testMode = true;
	
	public void testOutput(String str){
		if(testMode) {
			System.out.println(str);
		}
	}
	

	public void buildPA() {
		Stack<Assign> newPA = new Stack<Assign>();
		Futoshiki puzzle = getState().getPuzzle();										//Takes current puzzle for testing.
		Futoshiki testPuzzle;
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				ArrayList<Integer> candidates = new ArrayList<>(puzzle.getSet(r, c));	//Creates list of candidates for current cell.
				if(candidates.size() > 1) {												//Because a single candidate is an assign not a candidate, cells with a single number are ignored.
					for(int i = 0; i < candidates.size(); i++) {
						Assign assign = new Assign(r,c,candidates.get(i));				//Creates the assign.
						testPuzzle = puzzle.clone();									//Creates an instance of the current puzzle to test on.
						try { 															//Tries to assign the value.
							testPuzzle.assign(assign);
							
							newPA.push(assign);											//If the value can be assigned without error, it is added to the potential assign stack.
							testOutput(assign.toString() + "added to stack.");
						} catch(Exception e) {
							testOutput(assign.toString() + "could not be added to stack.");
						}
					}
				testOutput(" ");
				}
			}
		}
		potentialAssigns = newPA;
		assignSingles();
	}
	
	public void showPA() {
		Stack<Assign> PA = getPA();
		for(int i = 0; i < PA.size(); i++) {
			System.out.println(PA.get(i).toString());
		}
	}
	
	public void showPA(int r, int c) {
		Stack<Assign> PA = getPA(r,c);
		for(int i = 0; i < PA.size(); i++) {
			System.out.println(PA.get(i).toString());
		}
	}
	
	public Stack<Assign> getPA() {
		if(Objects.isNull(potentialAssigns)) {
			testOutput("PA not built.");
			buildPA();
		}
		testOutput("PA built.");
		return potentialAssigns;
	}
	
	public Stack<Assign> getPA(int r, int c) {
		if(Objects.isNull(potentialAssigns)) {
			testOutput("PA not built.");
			buildPA();
		}
		testOutput("PA built.");
		
		Stack<Assign> cellPA = new Stack<Assign>();
		Assign currAssign;
		
		for(int i = 0; i < potentialAssigns.size();i++) {
			currAssign = potentialAssigns.get(i);
			if(currAssign.getRow() == r && currAssign.getCol() == c) {
				cellPA.add(currAssign);
			}
		}
		
		return cellPA;
	}
	
	public void assignSingles() {
		Stack<Assign> currCellPA;
		Vector<Assign> singles = new Vector<Assign>();
		//Assign the singles
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				currCellPA = getPA(r,c);
				if(currCellPA.size() == 1) {
					getState().addAssign(currCellPA.get(0));
					Integer test = getPA().indexOf(currCellPA.get(0));
					singles.add(currCellPA.get(0));
				}
			}
		}
		//Remove singles from PA
		testOutput("Singles to be added:");
		for(int i = 0; i < singles.size(); i++) {
			System.out.println("Removing: " + singles.get(i).toString());
			potentialAssigns.remove(singles.get(i));
		}
		
		testOutput("------New PA-------");
		showPA();
		testOutput("------New PA-------");
	}

	
	public State getState() {
		return this.levelState;
	}
	
	public void removeAssign() {
		potentialAssigns.pop();
	}
	
	public Assign nextAssign() {
		return potentialAssigns.peek();
	}
	
	public Level nextLevel() {
		State newState = new State(getState().getAS());
		newState.addAssign(nextAssign());
		
		Level lvl = new Level(newState);
		return lvl;
	}
}
