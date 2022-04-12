package futoshikisolver;

import java.util.*;

/**
 * A cell in a Futoshiki puzzle; this is Observable, to allow a GUI to track changes.  It works closely with the
 * Futoshiki puzzle itself, and both Constraint and Relation to handle row & column constraints and inter-cell
 * relations respectively.
 * 
 * @author Dr Mark C. Sinclair
 * @version November 2020
 *
 */
@SuppressWarnings("deprecation")
public class Cell extends Observable {
  /**
   * Constructor
   * 
   * @param g the Futoshiki puzzle
   * @param r the cell row
   * @param c the cell column
   */
  public Cell(Futoshiki g, int r, int c) {
    if (g == null)
      throw new FutoshikiException("g is null");
    if ((r<1) || (r>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid row (" + r + ")");
    if ((c<1) || (c>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid col (" + c + ")");
    grid = g;
    row  = r;
    col  = c;
    set  = new TreeSet<Integer>(); // possible cell numbers
    for (int i=1; i<=Futoshiki.SETSIZE; i++)
      set.add(i);
    constraints = new Vector<Constraint>();
    relations   = new Vector<Relation>();
  }

  /**
   * Test equality in terms of the Cell row, column and set.
   * 
   * @param obj the other Cell
   * @return true if equal
   */
  @Override
  public boolean equals(Object obj) {
    if ((obj != null) && (obj instanceof Cell)) {
      Cell c = (Cell) obj;
      	if (c.row == row && c.col == col && c.set.equals(set)) {
            return true;
      	}
    }
    return false;
  }

  /**
   * Retrieve the cell row
   * 
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Retrieve the cell column
   * 
   * @return the column
   */
  public int getCol() {
    return col;
  }

  /**
   * Retrieve the cell set size (how many of possible numbers remaining)
   * 
   * @return the cell set size
   */
  public int size() {
    return set.size();
  }

  /**
   * Has the cell been assigned? (Only one possible number remaining)
   * 
   * @return true if assigned
   */
  public boolean isAssigned() {
    return size() == 1;
  }

  /**
   * Retrieve the cell's assigned number or -1 if not assigned
   * 
   * @return assigned number (or -1 if not assigned)
   */
  public int getNum() {
    if (isAssigned())
      return set.first();
    return -1;
  }

  /**
   * Retrieve a copy of the cell's remaining possible numbers as a TreeSet
   * 
   * @return the remaining possible numbers
   */
  public TreeSet<Integer> getSet() {
    return new TreeSet<Integer>(set);
  }
  
  /**
   * Retrieve the cell's remaining possible numbers as a String (useful for printing)
   * 
   * @return the remaining possible numbers
   */
  public String getSetAsString() {
    StringBuffer buf = new StringBuffer();
    buf.append("[");
    Iterator<Integer> iter = set.iterator();
    while (iter.hasNext()) {
      buf.append(iter.next());
      if (iter.hasNext())
        buf.append(",");
    }
    buf.append("]");
    return buf.toString();
  }

  /**
   * Does the cell set contain the given possible number?
   * 
   * @param num the given possible number
   * @return true if the cell set contains num
   */
  public boolean contains(int num) {
    if ((num<1) || (num>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid number (" + num + ")");
    return set.contains(num);
  }
  
  /**
   * Retrieve the lowest number in the cell set of possible numbers
   * 
   * @return the lowest number in the cell set
   */
  public int getLowest() {
    return set.first();
  }

  /**
   * Retrieve the highest number in the cell set of possible numbers
   * 
   * @return the highest number in the cell set
   */
  public int getHighest() {
    return set.last();
  }
  
  /**
   * Remove a possible number from the cell set (as long as it is not already assigned); the method
   * checks that all this cells relations are satisfied, and also that constraints are satisfied if
   * this cell becomes assigned as a result of the number removal.  These may result in iterative
   * changes to multiple cells and the whole Futoshiki ...
   * 
   * @param num the possible number to be removed
   */
  void remove(int num) {
    trace("Cell.remove("+row+","+col+","+num+")");
    if ((num<1) || (num>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid number (" + num + ")");
    if (!set.contains(num))
      throw new FutoshikiException
      ("set doesn't contain number (" + num + ")");
    if (isAssigned())
      throw new FutoshikiException
      ("cannot remove num as cell is assigned (" + num + ")");
    set.remove(num);
    satisfyRelations();
    grid.setChanged(); // continue to solve
    if (isAssigned())  {
      setChanged();
      notifyObservers(this);  // ensure GUI is changed
      satisfyAssign();
    }
  }

  /**
   * Assign this cell to a single number (as long as it is not already assigned, and this is a possible number);
   * the method checks that all this cells relations are satisfied, and also that constraints are satisfied.
   * These may result in iterative changes to multiple cells and the whole Futoshiki ...
   * 
   * @param num the number to be assigned
   */
  void assign(int num) {
    trace("Cell.assign("+row+","+col+","+num+")");
    if ((num<1) || (num>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid number (" + num + ")");
    if (!set.contains(num))
      throw new FutoshikiException
      ("set doesn't contain number (" + num + ")");
    if (isAssigned())
      throw new FutoshikiException("cell " + row + ", " + col + " already assigned");
    set.clear();
    set.add(num);
    satisfyRelations();
    setChanged();
    notifyObservers(this);
    grid.setChanged(); // continue to solve
    satisfyAssign();
  }

  /**
   * Add a new (row or column) constraint to this cell
   * 
   * @param cn the constraint
   */
  void addConstraint(Constraint cn) {
    if (cn == null)
      throw new FutoshikiException("cannot add null Constraint");
    if (constraints.contains(cn))
      throw new FutoshikiException("cannot add Constraint twice");
    constraints.add(cn);
  }

  /**
   * Check that all this cell's constraints are satisfied
   * (this will be called just after this cell is assigned)
   */
  private void satisfyAssign() {
    if (!isAssigned())
      throw new FutoshikiException("cell must be assigned");
    for (Constraint cn : constraints)
      cn.satisfyAssign(this);
  }
  
  /**
   * Add a new relation to this cell. The method checks that all this cells relations are satisfied.
   * This may result in iterative changes to multiple cells and the whole Futoshiki ...
   * 
   * @param r the relation
   */
  void addRelation(Relation r) {
    if (r == null)
      throw new FutoshikiException("cannot add null Relation");
    if (relations.contains(r))
      throw new FutoshikiException("cannot add Relation twice");
    if (!r.contains(this))
      throw new FutoshikiException("can only add Relation that contains this Cell");
    relations.add(r);
    satisfyRelations();
  }
  
  /**
   * Check that all this cell's relations are satisfied
   */
  private void satisfyRelations() {
    for (Relation r : relations)
      r.satisfyRelation();
  }
  
  /**
   * String representation of the cell (useful for debugging)
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Cell(" + row + "," + col + ",[");
    Iterator<Integer> si = set.iterator();
    while (si.hasNext()) {
      buf.append(si.next());
      if (si.hasNext())
        buf.append(",");
    }
    buf.append("],[");
    Iterator<Relation> ri = relations.iterator();
    while (ri.hasNext()) {
      Relation r  = ri.next();
      Cell     gc = r.getGreater();
      Cell     lc = r.getLesser();
      buf.append("("+gc.getRow()+","+gc.getCol()+")>("+lc.getRow()+","+lc.getCol()+")");
      if (ri.hasNext())
        buf.append(",");
    }
    buf.append("])");
    return buf.toString();
  }
  
  /**
   * A trace method for debugging (active when traceOn is true)
   * 
   * @param s the string to output
   */
  public static void trace(String s) {
    if (traceOn)
      System.out.println("trace: " + s);
  }

  private Futoshiki          grid        = null;
  private int                row;
  private int                col;
  private TreeSet<Integer>   set         = null; // possible numbers for this cell
  private Vector<Constraint> constraints = null; // row and column constraints for this cell
  private Vector<Relation>   relations   = null; // relations for this cell
  
  private static boolean   traceOn = false;
}
