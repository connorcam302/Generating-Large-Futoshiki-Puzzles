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
		Futoshiki puzzle = levelState.getPuzzle();
		Stack<Assign> newPA = new Stack<Assign>();
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				ArrayList<Integer> candidates = new ArrayList<>(puzzle.getSet(r, c));
				if(candidates.size() > 1) {
					for(int i = 0; i < candidates.size(); i++) {
						Assign desc = new Assign(r,c,candidates.get(i));
						newPA.add(desc);
					}
				}
			}
		}
		potentialAssigns = newPA;
	}
	
	public void showPA() {
		if(Objects.isNull(potentialAssigns)) {
			testOutput("PA already built.");
			buildPA();
		}
		for(int i = 0; i < potentialAssigns.size(); i++) {
			System.out.println(potentialAssigns.get(i).toString());
		}
	}
	
	public Stack<Assign> getPA() {
		if(Objects.isNull(potentialAssigns)) {
			testOutput("PA not built.");
			buildPA();
		}
		testOutput("PA already built.");
		return potentialAssigns;
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
}
