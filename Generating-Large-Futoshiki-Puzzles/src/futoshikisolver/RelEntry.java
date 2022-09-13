package futoshikisolver;

/**
 * A 'passive' representation of a Futoshiki relation (in contrast to the 'active' RElation class);
 * the cell at (gr, gc) is greater than the one at (lr, lc)
 * 
 * @author Dr Mark C. Sinclair
 * @version November 2020
 *
 */
public class RelEntry extends UserEntry {
  /**
   * Constructor
   * 
   * @param gr row of the greater cell
   * @param gc column of the greater cell
   * @param lr row of the lesser cell
   * @param lc column of the lesser cell
   */
  public RelEntry(int gr, int gc, int lr, int lc) {
    if ((gr<1) || (gr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater row (" + gr + ")");
    if ((gc<1) || (gc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater col (" + gc + ")");
    if ((lr<1) || (lr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser row (" + lr + ")");
    if ((lc<1) || (lc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser col (" + lc + ")");
    greaterRow = gr;
    greaterCol = gc;
    lesserRow  = lr;
    lesserCol  = lc;
  }
  
  /**
   * Test equality in terms of the four attributes
   * 
   * @param obj the other RelEntry
   * @return true if equal
   */
  @Override
  public boolean equals(Object obj) {
    if ((obj != null) && (obj instanceof RelEntry)) {
      RelEntry re = (RelEntry) obj;
      if ((re.getGreaterRow() == greaterRow) &&
          (re.getGreaterCol() == greaterCol) &&
          (re.getLesserRow()  == lesserRow)  &&
          (re.getLesserCol()  == lesserCol))
        return true; 
    }
    return false;
  }

  /**
   * Retrieve the row of the greater cell
   * 
   * @return the row of the greater cell
   */
  public int getGreaterRow() {
    return greaterRow;
  }

  /**
   * Retrieve the column of the greater cell
   * 
   * @return the column of the greater cell
   */
  public int getGreaterCol() {
    return greaterCol;
  }

  /**
   * Retrieve the row of the lesser cell
   * 
   * @return the row of the lesser cell
   */
  public int getLesserRow() {
    return lesserRow;
  }
  
  /**
   * Retrieve the column of the lesser cell
   * 
   * @return the column of the lesser cell
   */
  public int getLesserCol() {
    return lesserCol;
  }
  
  /**
   * String representation of the relation (useful for debugging)
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    return "RelEntry(" + greaterRow + "," + greaterCol + "," + lesserRow + "," + lesserCol + ")";
  }

  private int greaterRow = 0; // row of the greater cell
  private int greaterCol = 0; // column of the greater cell
  private int lesserRow  = 0; // row of the lesser cell
  private int lesserCol  = 0; // column of the lesser cell
}

