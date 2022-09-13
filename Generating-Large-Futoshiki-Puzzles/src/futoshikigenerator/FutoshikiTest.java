package futoshikigenerator;
import futoshikisolver.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;
import java.util.Vector;

import javax.print.attribute.standard.MediaSize.NA;

import org.junit.jupiter.api.Test;

class FutoshikiTest {
	FutoshikiTest(){
		Futoshiki.SETSIZE = 4;
	}
	
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
	void test_puzzleEqual_true() {
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		
		puzzle1.assign(1, 1, 3);
		puzzle1.assign(4, 4, 2);
		puzzle1.addRelation(2, 3, 3, 3);
		puzzle1.addRelation(2, 4, 3, 4);
		puzzle1.addRelation(3, 4, 3, 3);
		puzzle1.addRelation(2, 2, 2, 1);
		
		puzzle2.assign(1, 1, 3);
		puzzle2.assign(4, 4, 2);
		puzzle2.addRelation(2, 3, 3, 3);
		puzzle2.addRelation(2, 4, 3, 4);
		puzzle2.addRelation(3, 4, 3, 3);
		puzzle2.addRelation(2, 2, 2, 1);
		
		assertEquals(true, puzzle1.equals(puzzle2));
	}
	
	@Test
	void test_puzzleEqual_false() {
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		
		puzzle1.assign(1, 1, 3);
		puzzle1.assign(4, 4, 2);
		puzzle1.addRelation(2, 3, 3, 3);
		puzzle1.addRelation(2, 4, 3, 4);
		puzzle1.addRelation(3, 4, 3, 3);
		puzzle1.addRelation(2, 2, 2, 1);
		
		puzzle2.assign(1, 1, 3);
		puzzle2.assign(4, 4, 2);
		puzzle2.addRelation(2, 3, 3, 3);
		puzzle2.addRelation(2, 4, 3, 4);
		puzzle2.addRelation(3, 4, 3, 3);

		assertEquals(false, puzzle1.equals(puzzle2));
	}
	
	@Test
	void test_cellEqual_true() {
		Boolean result = false;
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		
		puzzle1.assign(2, 2, 4);
		puzzle2.assign(2, 2, 4);
		
		if(puzzle1.getCells()[2][2].equals((puzzle2).getCells()[2][2])) {
			result = true;
		}
		
		assertEquals(true, result);
	}
	
	@Test
	void test_cellEqual_false() {
		Boolean result = false;
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		
		puzzle1.assign(2, 2, 3);
		puzzle2.assign(2, 2, 4);
		
		if(puzzle1.getCells()[2][2].equals((puzzle2).getCells()[2][2])) {
			result = true;
		}
		
		assertEquals(false, result);
	}
	
	@Test
	void test_relationsEqual_false() {
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		
		puzzle1.addRelation(2, 3, 3, 3);
		puzzle1.addRelation(2, 4, 3, 4);
		puzzle1.addRelation(3, 4, 3, 3);
		puzzle1.addRelation(2, 2, 2, 1);
		
		puzzle2.addRelation(2, 3, 3, 3);
		puzzle2.addRelation(2, 4, 3, 4);
		puzzle2.addRelation(3, 4, 3, 3);

		assertEquals(false, puzzle1.compareRelations(puzzle2.getRelations()));
	}
	
	
	@Test
	void test_relationsEqual_true() {
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		
		puzzle1.addRelation(2, 3, 3, 3);
		puzzle1.addRelation(2, 4, 3, 4);
		puzzle1.addRelation(3, 4, 3, 3);
		puzzle1.addRelation(2, 2, 2, 1);
		
		puzzle2.addRelation(2, 3, 3, 3);
		puzzle2.addRelation(2, 4, 3, 4);
		puzzle2.addRelation(3, 4, 3, 3);
		puzzle2.addRelation(2, 2, 2, 1);

		assertEquals(true, puzzle1.compareRelations(puzzle2.getRelations()));
	}
	
	@Test
	void test_clone_true() {
		Futoshiki puzzle1 = new Futoshiki();
		Futoshiki puzzle2 = new Futoshiki();
		puzzle1.addRelation(2, 3, 3, 3);
		puzzle1.addRelation(2, 4, 3, 4);
		puzzle1.addRelation(3, 4, 3, 3);
		puzzle1.addRelation(2, 2, 2, 1);
		
		puzzle2 = puzzle1.clone();
		
		assertEquals(true, puzzle1.equals(puzzle2));
	}
	
	@Test
	void test_cloneBasePuzzle() {
		Futoshiki puzzle1 = new Futoshiki();
		puzzle1.addRelation(2, 3, 3, 3);
		puzzle1.addRelation(3, 4, 3, 3);
		puzzle1.addRelation(2, 2, 2, 1);
		puzzle1.assign(1, 1, 1);
		puzzle1.assign(4, 4, 4);
		
		Futoshiki puzzle2 = InstanceGenerator.cloneBasePuzzle();
		assertEquals(true, puzzle1.equals(puzzle2));
	}
	
	@Test
	void test_testBasePuzzle() {
		InstanceGenerator ig = new InstanceGenerator();
		InstanceGenerator.relations = new Vector<RelEntry>();
		InstanceGenerator.relations.add(new RelEntry(2, 3, 3, 3));
		InstanceGenerator.relations.add(new RelEntry(3, 4, 3, 3));
		InstanceGenerator.relations.add(new RelEntry(2, 2, 2, 1));
		InstanceGenerator.assigns = new Vector<Assign>();
		InstanceGenerator.assigns.add(new Assign(1, 1, 1));
		InstanceGenerator.assigns.add(new Assign(4, 4, 4));
		InstanceGenerator.assigns.add(new Assign(3, 4, 4));
		
		assertEquals(false, ig.testBasePuzzle());
	}
	
	@Test
	void test_nextLevel() {
		InstanceGenerator.relations = new Vector<RelEntry>();
		InstanceGenerator.relations.add(new RelEntry(2, 3, 3, 3));
		InstanceGenerator.relations.add(new RelEntry(3, 4, 3, 3));
		InstanceGenerator.relations.add(new RelEntry(2, 2, 2, 1));
		InstanceGenerator.assigns = new Vector<Assign>();
		InstanceGenerator.assigns.add(new Assign(1, 1, 1));
		InstanceGenerator.assigns.add(new Assign(4, 4, 4));
		State state = new State();
		Level level = new Level(state);
		
		boolean ans = false;
		Assign ca = new Assign(2,4,1);
		Assign na = level.nextAssign();
		
		if(ca.getCol() == na.getCol() && ca.getRow() == na.getRow() && ca.getNum() == na.getNum()) {
			ans = true;
		}
		assertEquals(true, ans);
	}
}
