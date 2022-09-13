package futoshikisolver;

/**
 * Assign.java
 * 
 * Captures the details of one cell assignment; used to create an undo function on the GUI interface
 * 
 * @author Dr Mark C. Sinclair
 * @version November 2020
 *
 */
public class Assign extends UserEntry {
  /**
   * Constructor
   * 
   * @param r the row of the cell to be assigned
   * @param c the column of the cell to be assigned
   * @param n the number to assign
   */
  public Assign(int r, int c, int n) {
    if ((r<1) || (r>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid row (" + r + ")");
    if ((c<1) || (c>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid col (" + c + ")");
    if ((n<1) || (n>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid num (" + n + ")");
    row = r;
    col = c;
    num = n;
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
   * Retrieve the assigned number
   * 
   * @return the assigned number
   */
  public int getNum() {
    return num;
  }
  
  /**
   * String representation of the assignment (useful for debugging)
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    return "Assign(" + row + "," + col + "," + num + ")";
  }

  private int row = 0;
  private int col = 0;
  private int num = 0;
}
