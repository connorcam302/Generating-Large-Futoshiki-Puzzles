package futoshikigenerator;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {
		InstanceGenerator gen = new InstanceGenerator();
		Backtracer back = new Backtracer();
		
		Futoshiki puzzle = gen.makeInstance();
		
//		InstanceGenerator.basePuzzle.addRelation(1, 3, 1, 2);
//		InstanceGenerator.basePuzzle.addRelation(3, 3, 3, 4);
//		InstanceGenerator.basePuzzle.addRelation(3, 4, 2, 4);
//		InstanceGenerator.basePuzzle.addRelation(3, 4, 4, 4);
//		InstanceGenerator.basePuzzle.addRelation(4, 2, 4, 1);

		Level tier = new Level();
		
		tier.showPA();
		
		System.out.println("Finished.");
	}
}
