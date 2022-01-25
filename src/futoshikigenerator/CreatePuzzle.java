package futoshikigenerator;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {
		InstanceGenerator gen = new InstanceGenerator();
		Backtracer back = new Backtracer();
		
		Futoshiki puzzle = gen.makeInstance();
		back.setCN(puzzle);
		back.assignNextCN(puzzle, 2);
	}
}
