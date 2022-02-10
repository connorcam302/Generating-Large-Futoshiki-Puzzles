package futoshikigenerator;
import futoshikisolver.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;

import org.junit.jupiter.api.Test;

class FutoshikiTest {
	
	@Test
	void test_setNum() {
		Futoshiki puzzle = new Futoshiki();
		puzzle.assign(2,2,Futoshiki.SETSIZE);
		int result = puzzle.getNum(2,2);
		
		assertEquals(Futoshiki.SETSIZE,result);
	}
	
	@Test
	void test_getSetAsString() {
		Futoshiki puzzle = new Futoshiki();
		puzzle.assign(2,2,3);
		puzzle.addRelation(2,2,2,1);
		String result = puzzle.getSetAsString(2,1);
		
		assertEquals("[1,2]",result);
	}
	
	@Test
	void test_getSet() {
		Futoshiki puzzle = new Futoshiki();
		puzzle.assign(2,2,3);
		puzzle.addRelation(2,2,2,1);
		TreeSet<Integer> result = puzzle.getSet(2,1);
		
		TreeSet<Integer> desiredResult = new TreeSet<Integer>();
		desiredResult.add(1);
		desiredResult.add(2);
		assertEquals(desiredResult,result);
	}
	
	@Test
	void test_isValidRelation() {
		Futoshiki puzzle = new Futoshiki();
		puzzle.assign(1, 1, 2);
		puzzle.assign(1, 2, 3);
		Boolean result = puzzle.isValidRelation(1,1,1,2);
		
		assertEquals(false,result);
	}
	
	@Test
	void test_ValidAssign_value() {
		Futoshiki puzzle = new Futoshiki();
		puzzle.assign(2,2,3);
		Boolean result = puzzle.isValidAssign(2,4,3);
		
		assertEquals(false, result);
	}
	
	@Test
	void test_ValidAssign_relation() {
		Futoshiki puzzle = new Futoshiki();
		puzzle.assign(2,2,3);
		puzzle.addRelation(2,2,2,1);
		Boolean result = puzzle.isValidAssign(2,1,4);
		
		assertEquals(false, result);
	}
	
	@Test
	void test_State_isSolution() {
		State state = new State();
		InstanceGenerator gen = new InstanceGenerator();
		gen.makeInstance();
		
		InstanceGenerator.basePuzzle.assign(4, 1, 1);
		InstanceGenerator.basePuzzle.assign(4, 4, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 3, 2, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 4, 2, 4);
		InstanceGenerator.basePuzzle.addRelation(2, 3, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 4, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 1, 3, 2);
		
		Assign assign = new Assign(1,1,2);
		state.addAssign(assign);
		
		boolean result = state.isSolved();
		
		assertEquals(true, result);
	}
	
	@Test
	void test_State_solvedCellCount() {
		State state = new State();
		InstanceGenerator gen = new InstanceGenerator();
		gen.makeInstance();
		
		InstanceGenerator.basePuzzle.assign(4, 1, 1);
		InstanceGenerator.basePuzzle.assign(4, 4, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 3, 2, 3);
		InstanceGenerator.basePuzzle.addRelation(1, 4, 2, 4);
		InstanceGenerator.basePuzzle.addRelation(2, 3, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 4, 3, 3);
		InstanceGenerator.basePuzzle.addRelation(3, 1, 3, 2);
		
		int result = state.solvedCellCount();
		
		assertEquals(2, result);
	}
}
