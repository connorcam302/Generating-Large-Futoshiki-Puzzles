package futoshikigenerator;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {

		InstanceGenerator gen = new InstanceGenerator();
		gen.makeInstance();
		Backtracer back = new Backtracer();
		
		InstanceGenerator.basePuzzle.addRelation(1, 3, 2, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 4, 2, 4);
		InstanceGenerator.basePuzzle.addRelation(2, 3, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 4, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 1, 3, 2);
		InstanceGenerator.basePuzzle.assign(4, 1, 1);
		InstanceGenerator.basePuzzle.assign(4, 4, 3);
		
		Futoshiki testPuzzle = new Futoshiki();
		
		testPuzzle.addRelation(1, 3, 2, 3);
		testPuzzle.addRelation(1, 4, 2, 4);
		testPuzzle.addRelation(2, 3, 3, 3);
		testPuzzle.addRelation(3, 4, 3, 3);
		testPuzzle.addRelation(3, 1, 3, 2);
		testPuzzle.assign(4, 1, 1);
		testPuzzle.assign(4, 4, 3);
		
		Futoshiki testPuzzle2 = InstanceGenerator.basePuzzle.clone();
		
		Set<Futoshiki> puzzles = new HashSet<Futoshiki>();
		
		puzzles.add(testPuzzle);
		puzzles.add(testPuzzle2);
		
		
		back.tracePuzzle();
		System.out.print(puzzles.size());
	}
}
 