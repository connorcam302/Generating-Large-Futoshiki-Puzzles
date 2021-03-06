package futoshikisolver;

import java.util.*;

/**
 * A Futoshiki puzzle.  It works closely with Cell, and both Constraint and Relation
 * to handle row & column constraints and inter-cell relations respectively.
 * 
 * @author Dr Mark C. Sinclair
 * @version November 2020
 */
public class Futoshiki {
  /**
   * Default constructor
   */
  public Futoshiki() {
    int row, col;
    cells = new Cell[SETSIZE+1][SETSIZE+1];
    for (row=1; row<=SETSIZE; row++)
      for (col=1; col<=SETSIZE; col++)
        cells[row][col] = new Cell(this, row, col);

    // row constraints
    rc = new Constraint[SETSIZE+1];
    for (row=1; row<=SETSIZE; row++) {
      rc[row] = new Constraint();
      for (col=1; col<=SETSIZE; col++) {
        rc[row].add(cells[row][col]);
        cells[row][col].addConstraint(rc[row]);
      }
    }

    // column constraints
    cc = new Constraint[SETSIZE+1];
    for (col=1; col<=SETSIZE; col++) {
      cc[col] = new Constraint();
      for (row=1; row<=SETSIZE; row++) {
        cc[col].add(cells[row][col]);
        cells[row][col].addConstraint(cc[col]);
      }
    }
    
    // relations
    rs = new Vector<Relation>();
  }
  

  /**
   * Add an Observer to all the constituent Cells.  This potentially supports a GUI.
   * 
   * @param o the Observer
   */
  @SuppressWarnings("deprecation")
  void addObserver(Observer o) {
    if (o == null)
      throw new FutoshikiException("o is null");
    for (int row=1; row<=SETSIZE; row++) {
      for (int col=1; col<=SETSIZE; col++) {
        cells[row][col].addObserver(o);
      }
    }
  }

  /**
   * Retrieve the assigned number of a cell (or -1)
   * 
   * @param row the cell row
   * @param col the cell column
   * @return the cell number (or -1 if not assigned)
   */
  public int getNum(int row, int col) {
    if ((row<1) || (row>SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    return cells[row][col].getNum();
  }

  /**
   * Is this Futoshiki assigned i.e. all its cells assigned,
   * and thus the puzzle solved?
   * 
   * @return true if all Cells are assigned
   */
  public boolean isAssigned() {
    for (int row=1; row<=SETSIZE; row++)
      for (int col=1; col<=SETSIZE; col++)
        if (!cells[row][col].isAssigned())
          return false;
    return true;
  }

  /**
   * Is a given cell assigned i.e. only a single number possible?
   * 
   * @param row the cell row
   * @param col the cell column
   * @return true if the cell is assigned
   */
  public boolean isAssigned(int row, int col) {
    if ((row<1) || (row>SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    return cells[row][col].isAssigned();
  }

  /**
   * Retrieve (a copy of) the set of possible numbers for a given cell
   * 
   * @param row the cell row
   * @param col the cell column
   * @return the set of possible numbers
   */
  
  public TreeSet<Integer> getSet(int row, int col) {
    if ((row<1) || (row>SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    return cells[row][col].getSet();
  }
  
  public ArrayList<int[]> getEmptyCells() {
	  ArrayList<int[]> emptyCells = new ArrayList<int[]>();
	  for(int r = 1; r <= SETSIZE; r++) {
		  for(int c = 1; c <= SETSIZE; c++) {
			  if(getNum(r,c) == -1) {
				  int[] array = {r,c};
				  emptyCells.add(array);
			  }
		  }
	  }
	  return emptyCells;
  }
  
  /**
   * Retrieve a String representation of the set of possible
   * numbers for a given cell
   * 
   * @param row the cell row
   * @param col the cell column
   * @return the representation of the set as a String
   */
  public String getSetAsString(int row, int col) {
    if ((row<1) || (row>SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    return cells[row][col].getSetAsString();
  }

  /**
   * Is this a valid assignment of a cell to a single number?
   * 
   * @param a the potential assignment
   * @return true if valid
   */
  public boolean isValidAssign(Assign a) {
    if (a == null)
      throw new FutoshikiException("cannot have null a");
    return isValidAssign(a.getRow(), a.getCol(), a.getNum());
  }
  
  /**
   * Is this a valid assignment of a cell to a single number?
   * 
   * @param row the cell row
   * @param col the cell column
   * @param num the potential number to be assigned
   * @return true if valid
   */
  public boolean isValidAssign(int row, int col, int num) {
    if ((row<1) || (row>SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    if ((num<1) || (num>SETSIZE))
      throw new FutoshikiException("invalid number (" + num + ")");
    return cells[row][col].contains(num);
  }
  
  /**
   * Assign a cell to a single value.  This may result in iterative
   * changes to multiple cells and the whole Futoshiki ...
   * 
   * @param a the assignment
   */
  public void assign(Assign a) {
    if (a == null)
      throw new FutoshikiException("cannot have null a");
    assign(a.getRow(), a.getCol(), a.getNum());
  }

  /**
   * Assign a cell to a single value.  This may result in iterative
   * changes to multiple cells and the whole Futoshiki ...
   * 
   * @param row the cell row
   * @param col the cell column
   * @param num the assigned number
   */
  public void assign(int row, int col, int num) {
	int cellNum = ((row-1)*SETSIZE + col);
    //trace("Futoshiki.assign("+row+","+col+","+num+")");
    trace(cellNum + " assigned " + num);
    if ((row<1) || (row>SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    if ((num<1) || (num>SETSIZE))
      throw new FutoshikiException("invalid number (" + num + ")");
    if (!isValidAssign(row, col, num))
      throw new FutoshikiException("invalid assign (" + row + "," + col + "," + num + ")");
    cells[row][col].assign(num);
  }
  
  /**
   * Is this a valid relation?
   * 
   * @param re the RelEntry to check
   * @return true if valid
   */
  boolean isValidRelation(RelEntry re) {
    if (re == null)
      throw new FutoshikiException("cannot have null re");
    return isValidRelation(re.getGreaterRow(), re.getGreaterCol(), re.getLesserRow(), re.getLesserCol());
  }
  
  /**
   * Is this a valid relation?
   * 
   * @param gr row of the greater cell
   * @param gc column of the greater cell
   * @param lr row of the lesser cell
   * @param lc column of the lesser cell
   * @return true if valid
   */
  public boolean isValidRelation(int gr, int gc, int lr, int lc) {
    if ((gr<1) || (gr>Futoshiki.SETSIZE))
    	throw new FutoshikiException("invalid greater row (" + gr + ")");
    if ((gc<1) || (gc>Futoshiki.SETSIZE))
    	throw new FutoshikiException("invalid greater col (" + gc + ")");
    if ((lr<1) || (lr>Futoshiki.SETSIZE))
    	throw new FutoshikiException("invalid lesser row (" + lr + ")");
    if ((lc<1) || (lc>Futoshiki.SETSIZE))
    	return false;
    if ((gr == lr) && (gc == lc)) // the two cells are the same
      return false;
    if (cells[gr][gc].getHighest() <= cells[lr][lc].getLowest()) // can't satisfy relation
      return false;
    return !containsRelEntry(gr, gc, lr, lc); // duplicate relation?
  }
  
  /**
   * Add a Relation to the Futoshiki.  This may result in iterative
   * changes to multiple cells and the whole Futoshiki ...
   * 
   * @param re the RelEntry describing the new Relation
   */
  void addRelation(RelEntry re) {
    if (re == null)
      throw new FutoshikiException("cannot have null re");
    addRelation(re.getGreaterRow(), re.getGreaterCol(), re.getLesserRow(), re.getLesserCol());
  }
  
  /**
   * Add a Relation to the Futoshiki.  This may result in iterative
   * changes to multiple cells and the whole Futoshiki ...
   * 
   * @param gr row of the greater cell
   * @param gc column of the greater cell
   * @param lr row of the lesser cell
   * @param lc column of the lesser cell
   */
  public void addRelation(int gr, int gc, int lr, int lc) {
    trace("Futoshiki.addRelation("+gr+","+gc+","+lr+","+lc+")");
    if ((gr<1) || (gr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater row (" + gr + ")");
    if ((gc<1) || (gc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater col (" + gc + ")");
    if ((lr<1) || (lr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser row (" + lr + ")");
    if ((lc<1) || (lc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser col (" + lc + ")");
    if (containsRelEntry(gr, gc, lr, lc))
      throw new FutoshikiException("cannot add the same Relation twice");
    Cell     greater = cells[gr][gc];
    Cell     lesser  = cells[lr][lc];
    Relation r       = new Relation(greater, lesser);
    rs.add(r);
  }
  
  /**
   * Does the Futoshiki contain a Relation that matches the given RelEntry?
   * (A check cannot be made directly with a Relation, as creating a 
   * Relation for the check would trigger iterative changes in the Futoshiki.)
   * 
   * @param re the given RelEntry
   * @return true if the Futoshiki contains a matching Relation
   */
  public boolean containsRelEntry(RelEntry re) {
    if (re == null)
      throw new FutoshikiException("cannot have null re");
    return containsRelEntry(re.getGreaterRow(), re.getGreaterCol(), re.getLesserRow(), re.getLesserCol());
  }
  
  /**
   * Does the Futoshiki contain a matching Relation?
   * 
   * @param gr row of the greater cell
   * @param gc column of the greater cell
   * @param lr row of the lesser cell
   * @param lc column of the lesser cell
   * @return true if the Futoshiki contains a matching Relation
   */
  public boolean containsRelEntry(int gr, int gc, int lr, int lc) {
    if ((gr<1) || (gr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater row (" + gr + ")");
    if ((gc<1) || (gc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater col (" + gc + ")");
    if ((lr<1) || (lr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser row (" + lr + ")");
    if ((lc<1) || (lc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser col (" + lc + ")");
    RelEntry re = new RelEntry(gr,gc,lr,lc);
    for (Relation r : rs) {
      if (r.getRelEntry().equals(re))
        return true;
    }
    return false;
  }

  /**
   * Attempt to solve the Futoshiki using the encoded solution
   * approaches, checking for unique unassigned values in a constraint,
   * and chjecking for matched pairs in a constraint ... additional
   * methods could be added to solve more tricky Futoshiki instances.
   * 
   */
  public void solve() {
    do {
      changed = false;
      checkUnique();
      checkPairs();
    } while(changed);
  }

  /**
   * Check for unique values on all constraints
   */
  void checkUnique() {
    for (int row=1; row<=SETSIZE; row++)
      rc[row].checkUnique();
    for (int col=1; col<=SETSIZE; col++)
      cc[col].checkUnique();
  }
  
  /**
   * Check for matched pairs on all constraints
   */
  void checkPairs() {
    for (int row=1; row<=SETSIZE; row++)
      rc[row].checkPairs();
    for (int col=1; col<=SETSIZE; col++)
      cc[col].checkPairs();
  }
  
  /**
   * Has the Futoshiki been changed, and thus opportunities might yet
   * remain for a solution
   * 
   * @return true if changed
   */
  public boolean getChanged() {
    return changed;
  }

  /**
   * Set the changed flag, allowing automatic solution to continue
   */
  void setChanged() {
    changed = true;
  }

  /**
   * String representation of the Futoshiki game (useful for debugging)
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Futoshiki("+ SETSIZE + ",");
    for (int row=1; row<=SETSIZE; row++)
      for (int col=1; col<=SETSIZE; col++) {
        buf.append(cells[row][col]);
        if ((row != SETSIZE) || (col != SETSIZE))
          buf.append(",");
        if (col == SETSIZE)
          buf.append("\n");
      }
    buf.append(")");
    return buf.toString();
  }
  
  public void display() {
	  int row, col;
	  for (row=1; row<Futoshiki.SETSIZE; row++) {
		  // row with cells
		  for (col=1; col<Futoshiki.SETSIZE; col++) {        
			  System.out.print(isAssigned(row,col) ? getNum(row,col) : "*");
			  if (containsRelEntry(row,col,row,col+1))
				  System.out.print(REL_RIGHT);
			  else if (containsRelEntry(row,col+1,row,col))
				  System.out.print(REL_LEFT);
			  else
				  System.out.print(" ");
		  }
		  System.out.print(isAssigned(row,col) ? getNum(row,col) : "*"); // last cell in row
		  System.out.println();
		  // row between cells
		  for (col=1; col<Futoshiki.SETSIZE; col++) {
			  if (containsRelEntry(row,col,row+1,col))
				  System.out.print(REL_DOWN);
			  else if (containsRelEntry(row+1,col,row,col))
				  System.out.print(REL_UP);
			  else
				  System.out.print(" ");
			  System.out.print(" ");
		  }
		  // last relation in row
		  if (containsRelEntry(row,col,row+1,col))
			  System.out.print(REL_DOWN);
		  else if (containsRelEntry(row+1,col,row,col))
			  System.out.print(REL_UP);
		  else
			  System.out.print(" ");
		  System.out.println();
	  }
	  // last row
	  for (col=1; col<Futoshiki.SETSIZE; col++) {
		  System.out.print(isAssigned(row,col) ? getNum(row,col) : "*");
		  if (containsRelEntry(row,col,row,col+1))
			  System.out.print(REL_RIGHT);
		  else if (containsRelEntry(row,col+1,row,col))
			  System.out.print(REL_LEFT);
		  else
			  System.out.print(" ");
	  }
	  System.out.print(isAssigned(row,col) ? getNum(row,col) : "*"); //last cell in row
	  System.out.println();
  }
  
  public Futoshiki clone() {
	  Futoshiki clone = new Futoshiki();
	  
	  //Check for inequalities
	  //-Check below
	  for(int r = 1; r <= Futoshiki.SETSIZE-1; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(containsRelEntry(r+1,c,r,c)) {
					clone.addRelation(r+1, c, r, c);
				} else if(containsRelEntry(r,c,r+1,c)){
					clone.addRelation(r, c, r+1, c);
				} 
			}
	  }
	  //-Check right
	  for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE-1; c++) {
				if(containsRelEntry(r,c+1,r,c)) {
					clone.addRelation(r, c+1, r, c);
				} else if(containsRelEntry(r,c,r,c+1)) {
					clone.addRelation(r, c, r, c+1);
				}
			}
		}
	  
	  //Check for values
	  for(int r = 1; r <= Futoshiki.SETSIZE; r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE; c++) {
				if(isAssigned(r, c)) {
					try {
						clone.assign(r,c,getNum(r, c));
					} catch(Exception e) {
						if(clone.getNum(r, c) != getNum(r,c)) {
							throw new FutoshikiException("cell " + r + ", " + c + " autofilled a different value in clone. Value " + clone.getNum(r, c) + "assigned, looking for "+ getNum(r, c));
						}
					}
				}
			}
	  }
	  
	  return clone;
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
  
  // Set Futoshiki size.
  public void setSETSIZE(int i) {
	  Futoshiki.SETSIZE = i;
  }
  
  @Override
  public boolean equals(Object obj) {
	  if ((obj != null) && (obj instanceof Futoshiki)) {
		  Futoshiki puzzle = (Futoshiki) obj;
		  if( Arrays.deepEquals(cells, puzzle.getCells()) && compareRelations(puzzle.getRelations())){
			  return true;
		  }	
	  }
	  return false;
  }

  public Cell[][] getCells(){
	  return cells;
  }
  
  public Vector<Relation> getRelations() {
	  return rs;
  }
  
  public Boolean compareRelations(Vector<Relation> rs2) {
	  if(rs.size() != rs2.size()) {
		  return false;
	  }
	  for(int i = 0; i < rs.size(); i++) {
		  if(!rs.contains(rs2.get(i))) {
			  return false;
		  }
	  }
	  return true;
  }
  
  public void setCells(Cell[][] newCells) {
	  cells = newCells;
  }
  
  public void setRC(Constraint[] newRC) {
	  rc = newRC;
  }
  
  public void setCC(Constraint[] newCC) {
	  cc = newCC;
  }
  
  public void setRS(Vector<Relation> newRS) {
	  rs = newRS;
  }

  public void setChanged(boolean newChanged) {
	  changed = newChanged;
  }
  

  private Cell[][]         cells   = null;
  private Constraint[]     rc      = null; // row constraints
  private Constraint[]     cc      = null; // col constraints
  private Vector<Relation> rs      = null; // relations
  private boolean          changed = false;
  
  public static final String REL_RIGHT = ">";
  public static final String REL_LEFT  = "<";
  public static final String REL_UP    = "^";
  public static final String REL_DOWN  = "v";
  
  private static boolean 	traceOn = false;

  public static int SETSIZE = 9;
}
