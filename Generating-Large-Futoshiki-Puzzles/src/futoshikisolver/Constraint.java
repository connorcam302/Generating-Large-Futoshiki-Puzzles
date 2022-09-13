package futoshikisolver;

import java.util.*;

/**
 * A row or column constraint for Futoshiki (similar to Sudoku); if a cell in a constraint
 * is assigned a number, that number is not allowed in any other cell in the constraint
 *
 * @author Dr Mark C. Sinclair
 * @version November 2020
 *
 */
public class Constraint {
  /**
   * Default constructor
   */
  public Constraint() {
    cells = new Vector<Cell>();
  }

  /**
   * Add a Cell to this constraint
   * 
   * @param c the cell to add
   */
  void add(Cell c) {
    if (c == null)
      throw new FutoshikiException("cannot add null Cell");
    if (cells.contains(c))
      throw new FutoshikiException("cannot add Cell twice");
    cells.add(c);
  }

  /**
   * Retrieve a copy of the constraints vector of cells
   * 
   * @return the vector of cells
   */
  public Vector<Cell> getCells() {
    return new Vector<Cell>(cells);
  }

  /**
   * Does the constraint contain the given cell?
   * 
   * @param c the given cell
   * @return true if this constraint contains c
   */
  boolean contains(Cell c) {
    return cells.contains(c);
  }

  /**
   * Modify all the cells in this constraint so they don't
   * contain the single number of assigned cell c. This
   * may result in iterative changes to multiple cells and
   * the whole Futoshiki ...
   * 
   * @param c the assigned cell
   */
  void satisfyAssign(Cell c) {
    if (c == null)
      throw new FutoshikiException("cannot check assignment satisfied for null Cell");
    if (!cells.contains(c))
      throw new FutoshikiException("c is not in this Constraint");
    if (!c.isAssigned())
      throw new FutoshikiException("c has not yet been assigned a single number");
    int num  = c.getNum(); // assigned number
    for (Cell curr : cells) {
      if (!curr.equals(c))
        if (curr.contains(num))
          curr.remove(num);
    }
  }

  /**
   * Check if there is an unassigned Cell that is the
   * only one with a particular number; if so, assign it.
   * This may result in iterative changes to multiple
   * cells and the whole Futoshiki ...
   */
  void checkUnique() {
    ArrayList<Vector<Cell>> location = new ArrayList<Vector<Cell>>(Futoshiki.SETSIZE+1);
    for (int i=0; i<=Futoshiki.SETSIZE; i++)
      location.add(new Vector<Cell>());
    for (Cell c : cells)
      for (int num : c.getSet())
        location.get(num).add(c);
    for (int i=1; i<=Futoshiki.SETSIZE; i++)
      if (location.get(i).size() == 1) {
        Cell c = location.get(i).firstElement();
        if (!c.isAssigned()) {
          c.assign(i);
        }
      }
  }
  
  /**
   * Check if there are two Cells with identical pairs
   * if so, these numbers can be removed from other Cells
   * in the Constraint. This may result in iterative changes
   * to multiple cells and the whole Futoshiki ...
   */
  void checkPairs() {
    Hashtable<TreeSet<Integer>,Vector<Cell>> hash = new Hashtable<TreeSet<Integer>,Vector<Cell>>();
    for (Cell c : cells) {
      TreeSet<Integer> set = c.getSet();
      if (set.size() == 2) {
        Vector<Cell> vc = hash.get(set);
        if (vc == null)
          vc = new Vector<Cell>();
        vc.add(c);
        hash.put(set, vc);
      }
    }
    if (!hash.isEmpty()) {
      Enumeration<Vector<Cell>> e = hash.elements();
      while (e.hasMoreElements()) {
        Vector<Cell> vc = e.nextElement();
        if (vc.size() == 2) { // two identical pairs!
          trace("two identical pairs: " + vc);         
          Cell one             = vc.firstElement();
          Cell two             = vc.lastElement();
          TreeSet<Integer> set = one.getSet();
          int n                = set.first();
          int m                = set.last();
          for (Cell c : cells) {
            if (c != one && c != two) {
              if (c.contains(n))
                c.remove(n);
              if (c.contains(m))
                c.remove(m);
            }
          }
          return; // as Hashtable probably invalid now
        }
      }
    }
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
  
  /**
   * String representation of the constraint (useful for debugging)
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Constraint(");
    Iterator<Cell> iter = cells.iterator();
    while (iter.hasNext()) {
      buf.append(iter.next());
      if (iter.hasNext())
        buf.append(",");
    }
    buf.append(")");
    return buf.toString();
  }

  private Vector<Cell> cells;
  
  private static boolean traceOn = false;
}
