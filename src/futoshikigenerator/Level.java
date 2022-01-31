package futoshikigenerator;
import futoshikisolver.*;
import java.util.*;

public class Level {
	private State levelState = new State();
	private Vector<Assign> potentialAssigns = new Vector<Assign>();
	
	public Level() {
		Futoshiki puzzle = levelState.buildPuzzle();
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
	
	public void addAssign(Assign desc) {
		potentialAssigns.add(desc);
	}
	
	public void showPA() {
		for(int i = 1; i < potentialAssigns.size(); i++) {
			System.out.println(potentialAssigns.get(i).toString());
		}
	}
}
