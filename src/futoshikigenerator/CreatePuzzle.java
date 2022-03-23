package futoshikigenerator;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {

		InstanceGenerator gen = new InstanceGenerator();
		
		Backtracer back = new Backtracer();
		
		Futoshiki puzzle = gen.makeInstance();
		State state = new State();
		Level level = new Level(state);

//		System.out.println("---Potential Assigns---");
//		System.out.println(level.getState().getPuzzle().toString());
//		level.showPA();
//		System.out.println("---Assign Stack---");
//		level.getState().showAS();
//		System.out.println("Next assign: "+ level.nextAssign());
//		level.getState().getPuzzle().display();
//		
//		Level level2 = level.nextLevel();
//		
//		level2.getState().getPuzzle().display();
//		
//		System.out.println("---Puzzle 1---");
//		InstanceGenerator.basePuzzle.display();
//		System.out.println("---Puzzle 2---");
//		Futoshiki clone = InstanceGenerator.basePuzzle.clone(); 
//		clone.assign(1, 2, 1);
//		clone.display();

		InstanceGenerator.basePuzzle.addRelation(1, 3, 2, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 4, 2, 4);
		InstanceGenerator.basePuzzle.addRelation(2, 3, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 4, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 1, 3, 2);
//		InstanceGenerator.basePuzzle.assign(4, 1, 1);
//		InstanceGenerator.basePuzzle.assign(1, 1, 2);
//		InstanceGenerator.basePuzzle.assign(1, 2, 1);
//		InstanceGenerator.basePuzzle.assign(2, 3, 2);
//		InstanceGenerator.basePuzzle.assign(2, 4, 1);
//		InstanceGenerator.basePuzzle.assign(3, 3, 1);
//		InstanceGenerator.basePuzzle.assign(4, 4, 2);
//		InstanceGenerator.basePuzzle.assign(4, 4, 3);
		
		back.tracePuzzle();
	}
}
 