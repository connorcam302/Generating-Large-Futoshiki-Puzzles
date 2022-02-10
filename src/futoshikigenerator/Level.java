package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

public class Level {
	private State levelState = new State();
	private Stack<Assign> potentialAssigns = new Stack<Assign>();
	
	public Level(State state) {
		levelState = state;
	}
	

	public void buildPA() {
		Futoshiki puzzle = levelState.getPuzzle();
		for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				ArrayList<Integer> candidates = new ArrayList<>(puzzle.getSet(r, c));
				for(int i = 0; i < candidates.size(); i++) {
					Assign desc = new Assign(r,c,candidates.get(i));
					potentialAssigns.add(desc);
				}
			}
		}
	}
	
	public void showPA() {
		for(int i = 0; i < potentialAssigns.size(); i++) {
			System.out.println(potentialAssigns.get(i).toString());
		}
	}
	
	public Vector<Assign> getPA() {
		if(Objects.isNull(potentialAssigns)) {
			buildPA();
		}
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
