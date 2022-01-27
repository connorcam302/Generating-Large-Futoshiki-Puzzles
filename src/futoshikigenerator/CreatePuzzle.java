package futoshikigenerator;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {
		InstanceGenerator gen = new InstanceGenerator();
		Backtracer back = new Backtracer();
		
		Futoshiki puzzle = gen.makeInstance();

		back.showAllCN(puzzle);
		System.out.println(back.isSolved(puzzle));
		
		System.out.println("Finished.");
	}
}
