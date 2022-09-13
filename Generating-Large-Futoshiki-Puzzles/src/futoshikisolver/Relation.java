package futoshikisolver;

/**
 * A 'active' representation of a Futoshiki relation (in contrast to the 'passive' RelEntry class);
 * the Cell gc is greater than lc.  Creating a Relation will result in iterative changes in not only
 * the two Cells, but the whole Futoshiki - be careful.
 *
 * @author Dr Mark C. Sinclair
 * @version November 2020
 *
 */
public class Relation {
  /**
   * Constructor; calls satisfyRelation() which may make iterative changes to both Cells
   * and the whole Futoshiki
   * 
   * @param gc greater cell
   * @param lc lesser cell
   */
  public Relation(Cell gc, Cell lc) {
    if (gc == null)
      throw new FutoshikiException("cannot add null greater Cell");
    if (lc == null)
      throw new FutoshikiException("cannot add null lesser Cell");
    if (gc.equals(lc))
      throw new FutoshikiException("gc and lc cannot be the same Cell");
    greater = gc;
    lesser  = lc;
    satisfyRelation();
    greater.addRelation(this);
    lesser.addRelation(this);
  }

  /**
   * Test equality in terms of the equality of both Cells of the two Relations
   * 
   * @param obj the other Relation
   * @return true if equal
   */
  @Override
  public boolean equals(Object obj) {
    if ((obj != null) && (obj instanceof Relation)) {
      Relation r = (Relation) obj;
      if ((r.getGreater().equals(greater)) && (r.getLesser().equals(lesser)))
        return true;
    }
    return false;
  }

  /**
   * Retrieve the greater cell
   * 
   * @return the greater cell
   */
  Cell getGreater() {
    return greater;
  }
  
  /**
   * Retrieve the lesser cell
   * 
   * @return the lesser cell
   */
  Cell getLesser() {
    return lesser;
  }
  
  /**
   * Construct a RelEntry representing this Relation
   * 
   * @return the RelEntry representation
   */
  public RelEntry getRelEntry() {
    return new RelEntry(greater.getRow(),greater.getCol(),lesser.getRow(),lesser.getCol());
  }
  
  /**
   * Does this Relation contain (relate to) the given cell?
   * 
   * @param c the given cell
   * @return true if this Relation contains the cell
   */
  boolean contains(Cell c) {
    return greater.equals(c) || lesser.equals(c);
  }
  
  /**
   * Iteratively modifies the two Cells to cause them to satisfy the Relation;
   * as remove() is called on the Cells to modify them, this will in turn cause
   * further cell relations (and any resulting assignments) to be checked ...
   */
  void satisfyRelation() {
    Futoshiki.trace("Relation.satifyRelation() on "+this);
    if (greater.getHighest() <= lesser.getLowest())
      throw new FutoshikiException("highest in greater Cell must be greater than the lowest in lesser Cell");
    if (greater.isAssigned() && lesser.isAssigned())
      return; // no changes can be made
    boolean trimmed = false;
    do {
      trimmed = false;
      // lowest number in greater Cell must be greater than lowest in lesser Cell
      int gl = greater.getLowest();
      if (gl <= lesser.getLowest()) {
        greater.remove(gl);
        trimmed = true;
      }
      // highest number in lesser Cell must be smaller than highest in greater Cell
      int lh = lesser.getHighest();
      if (lh >= greater.getHighest()){
        lesser.remove(lh);
        trimmed = true;
      }
    } while (trimmed);
  }

  /**
   * String representation of the relation (useful for debugging)
   * 
   * @return the String representation
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Relation(");   
    buf.append(greater);
    buf.append(",");
    buf.append(lesser);
    buf.append(")");
    return buf.toString();
  }

  private Cell greater;
  private Cell lesser;
}
