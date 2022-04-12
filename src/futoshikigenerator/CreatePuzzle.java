package futoshikigenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import futoshikisolver.*;

public class CreatePuzzle {
	public static void main(String args[]) {

		InstanceGenerator gen = new InstanceGenerator();
		//gen.makeInstance();
		Backtracer back = new Backtracer();
		
		Futoshiki uniquePuzzle = new Futoshiki();
		Futoshiki uniquePuzzle2 = new Futoshiki();
		Futoshiki nonuniquePuzzle  = new Futoshiki();
		
		uniquePuzzle.assign(1, 1, 3);
		uniquePuzzle.assign(4, 4, 2);
		uniquePuzzle.addRelation(2, 3, 3, 3);
		uniquePuzzle.addRelation(2, 4, 3, 4);
		uniquePuzzle.addRelation(3, 4, 3, 3);
		uniquePuzzle.addRelation(2, 2, 2, 1);
				
		uniquePuzzle2.addRelation(1, 3, 2, 3);
		uniquePuzzle2.addRelation(1, 4, 2, 4);
		uniquePuzzle2.addRelation(2, 3, 3, 3);
		uniquePuzzle2.addRelation(3, 4, 3, 3);
		uniquePuzzle2.addRelation(3, 1, 3, 2);
		uniquePuzzle2.assign(4, 1, 1);
		uniquePuzzle2.assign(4, 4, 3);
		
		nonuniquePuzzle.addRelation(1, 3, 2, 3);
		nonuniquePuzzle.addRelation(1, 4, 2, 4);
		nonuniquePuzzle.addRelation(2, 3, 3, 3);
		nonuniquePuzzle.assign(4, 4, 3);
		
		//InstanceGenerator.basePuzzle = uniquePuzzle;
		
		Futoshiki finalPuzzle = null;
		gen.makeInstance();
		back.tracePuzzle();
		
//		while(finalPuzzle == null) {
//			try {
//				gen.makeInstance();
//				if(back.tracePuzzle()) {
//					finalPuzzle = InstanceGenerator.basePuzzle;
//				}
//			} catch(Exception e){
//				
//			}
//		}
	}
}
 