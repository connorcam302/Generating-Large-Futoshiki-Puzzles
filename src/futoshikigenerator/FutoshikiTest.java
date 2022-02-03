package futoshikigenerator;
import futoshikisolver.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;

import org.junit.jupiter.api.Test;

class FutoshikiTest {
//	
//	@Test
//	void test_setNum() {
//		System.out.println("-------test_setNum-------");
//		Futoshiki puzzle = new Futoshiki();
//		puzzle.assign(2,2,Futoshiki.SETSIZE);
//		int result = puzzle.getNum(2,2);
//		
//		assertEquals(Futoshiki.SETSIZE,result);
//	}
//	
//	@Test
//	void test_getSetAsString() {
//		System.out.println("-------test_getSetAsString-------");
//		Futoshiki puzzle = new Futoshiki();
//		puzzle.assign(2,2,3);
//		puzzle.addRelation(2,2,2,1);
//		String result = puzzle.getSetAsString(2,1);
//		
//		assertEquals("[1,2]",result);
//	}
//	
//	@Test
//	void test_getSet() {
//		System.out.println("-------test_getSet-------");
//		Futoshiki puzzle = new Futoshiki();
//		puzzle.assign(2,2,3);
//		puzzle.addRelation(2,2,2,1);
//		TreeSet<Integer> result = puzzle.getSet(2,1);
//		
//		TreeSet<Integer> desiredResult = new TreeSet<Integer>();
//		desiredResult.add(1);
//		desiredResult.add(2);
//		assertEquals(desiredResult,result);
//	}
//	
//	@Test
//	void test_isValidRelation() {
//		System.out.println("-------test_isValidRelation-------");
//		Futoshiki puzzle = new Futoshiki();
//		puzzle.assign(1, 1, 2);
//		puzzle.assign(1, 2, 3);
//		Boolean result = puzzle.isValidRelation(1,1,1,2);
//		
//		assertEquals(false,result);
//	}
//	
//	@Test
//	void test_ValidAssign_value() {
//		System.out.println("-------test_ValidAssign_value-------");
//		Futoshiki puzzle = new Futoshiki();
//		puzzle.assign(2,2,3);
//		Boolean result = puzzle.isValidAssign(2,4,3);
//		
//		assertEquals(false, result);
//	}
//	
//	@Test
//	void test_ValidAssign_relation() {
//		System.out.println("-------test_ValidAssign_relation-------");
//		Futoshiki puzzle = new Futoshiki();
//		puzzle.assign(2,2,3);
//		puzzle.addRelation(2,2,2,1);
//		Boolean result = puzzle.isValidAssign(2,1,4);
//		
//		assertEquals(false, result);
//	}
	
	@Test
	void test_State_isSolution() {
		System.out.println("-------test_State_isSolution-------");
		State state = new State();
		InstanceGenerator gen = new InstanceGenerator();
		gen.makeInstance();
		Assign temp;
		
		System.out.println(state.buildPuzzle().toString());
		
		temp = new Assign(1,1,1);
		state.addAssign(temp);
		temp = new Assign(1,2,2);
		state.addAssign(temp);
		temp = new Assign(1,3,3);
		state.addAssign(temp);
		System.out.println(state.buildPuzzle().toString());
//		temp = new Assign(2,1,2);
//		state.addAssign(temp);
		temp = new Assign(2,2,3);
		state.addAssign(temp);
		temp = new Assign(2,3,4);
		state.addAssign(temp);
		System.out.println(state.buildPuzzle().toString());
		temp = new Assign(3,1,3);
		state.addAssign(temp);
		temp = new Assign(3,2,4);
		state.addAssign(temp);
		temp = new Assign(3,3,1);
		state.addAssign(temp);
		System.out.println(state.buildPuzzle().toString());
		
		boolean result = state.isSolved();
		
		assertEquals(true, result);
	}
}
