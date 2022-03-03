package futoshikigenerator;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {
		InstanceGenerator gen = new InstanceGenerator();
		Backtracer back = new Backtracer();
		
		Futoshiki puzzle = gen.makeInstance();
		State state = new State();
		Level level = new Level(state);
		
		InstanceGenerator.basePuzzle.assign(4, 1, 1);
		InstanceGenerator.basePuzzle.assign(4, 4, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 3, 2, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 4, 2, 4);
		InstanceGenerator.basePuzzle.addRelation(2, 3, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 4, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 1, 3, 2);

		System.out.println("---Potential Assigns---");
		System.out.println(level.getState().getPuzzle().toString());
		level.showPA();
		System.out.println("---Assign Stack---");
		level.getState().showAS();
		System.out.println("Next assign: "+ level.nextAssign());
	}
}
 