package futoshikisolver;

import java.util.*;

/**
 * A text based user interface to the Futoshiki puzzle for the Final Assignment.
 * 
 * @author Dr Mark C. Sinclair
 * @version November 2020
 */
public class FutoshikiUI {
  /**
   * Default constructor
   */
  public FutoshikiUI() {
    scnr   = new Scanner(System.in);
    puzzle = new Futoshiki();
  }
  
  /**
   * Main control loop.  This displays the puzzle, then enters a loop displaying a menu,
   * getting the user command, executing the command, displaying the puzzle and checking
   * if further moves are possible
   */
  public void menu() {
    String command = "";
    displayPuzzle();
    while (!command.equalsIgnoreCase("Quit") && !puzzle.isAssigned())  {
      displayMenu();
      command = getCommand();
      execute(command);
      displayPuzzle();
    }
  }
  
  /**
   * Display the Futoshiki on the console
   */
  private void displayPuzzle() {
    int row, col;
    for (row=1; row<Futoshiki.SETSIZE; row++) {
      // row with cells
      for (col=1; col<Futoshiki.SETSIZE; col++) {        
        System.out.print(puzzle.isAssigned(row,col) ? puzzle.getNum(row,col) : "*");
        if (puzzle.containsRelEntry(row,col,row,col+1))
          System.out.print(REL_RIGHT);
        else if (puzzle.containsRelEntry(row,col+1,row,col))
          System.out.print(REL_LEFT);
        else
          System.out.print(" ");
      }
      System.out.print(puzzle.isAssigned(row,col) ? puzzle.getNum(row,col) : "*"); // last cell in row
      System.out.println();
      // row between cells
      for (col=1; col<Futoshiki.SETSIZE; col++) {
        if (puzzle.containsRelEntry(row,col,row+1,col))
          System.out.print(REL_DOWN);
        else if (puzzle.containsRelEntry(row+1,col,row,col))
          System.out.print(REL_UP);
        else
          System.out.print(" ");
        System.out.print(" ");
      }
      // last relation in row
      if (puzzle.containsRelEntry(row,col,row+1,col))
        System.out.print(REL_DOWN);
      else if (puzzle.containsRelEntry(row+1,col,row,col))
        System.out.print(REL_UP);
      else
        System.out.print(" ");
      System.out.println();
    }
    // last row
    for (col=1; col<Futoshiki.SETSIZE; col++) {
      System.out.print(puzzle.isAssigned(row,col) ? puzzle.getNum(row,col) : "*");
      if (puzzle.containsRelEntry(row,col,row,col+1))
        System.out.print(REL_RIGHT);
      else if (puzzle.containsRelEntry(row,col+1,row,col))
        System.out.print(REL_LEFT);
      else
        System.out.print(" ");
    }
    System.out.print(puzzle.isAssigned(row,col) ? puzzle.getNum(row,col) : "*"); // last cell in row
    System.out.println();
  }
  
  /**
   * Display the user menu
   */
  private void displayMenu()  {
    System.out.println( "Commands are");
    System.out.println("   Next assign        [Ass]");
    System.out.println("   Next relation      [Rel]");
    System.out.println("   Undo entry        [Undo]");
    System.out.println("   Restart puzzle   [Clear]");
    System.out.println("   Save puzzle       [Save]");
    System.out.println("   Load puzzle       [Load]");    
    System.out.println("   To end program    [Quit]");    
  }
  
  /**
   * Get the user command
   * 
   * @return the user command string
   */
  private String getCommand() {
    System.out.print ("Enter command: ");
    return scnr.nextLine();
  }
  
  /**
   * Execute the user command string
   * 
   * @param command the user command string
   */
  private void execute(String command) {
    if (command.equalsIgnoreCase("Quit")) {
      System.out.println("Program closing down");
      System.exit(0);
    } else if (command.equalsIgnoreCase("Ass")) {
      assign();
    } else if (command.equalsIgnoreCase("Rel")) {
      relation();
    } else if (command.equalsIgnoreCase("Undo")) {
      System.out.println("not implemented yet");
    } else if (command.equalsIgnoreCase("Clear")) {
      System.out.println("not implemented yet");
    } else if (command.equalsIgnoreCase("Save")) {
      System.out.println("not implemented yet");
    } else if (command.equalsIgnoreCase("Load")) {
      System.out.println("not implemented yet");
    } else {
      System.out.println("Unknown command (" + command + ")");
    }
  }
  
  /**
   * Assign a cell in the Futoshiki puzzle to a single number
   */
  private void assign() {
    System.out.print("Enter row (1 to " + Futoshiki.SETSIZE + "): ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid row");
      return;
    }
    int row = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((row<1) || (row>Futoshiki.SETSIZE)) {
      System.out.println("invalid row");
      return;
    }
    System.out.print("Enter column (1 to " + Futoshiki.SETSIZE + "): ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid column");
      return;
    }
    int col = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((col<1) || (col>Futoshiki.SETSIZE)) {
      System.out.println("invalid column");
      return;
    }
    if (puzzle.isAssigned(row, col)) {
      System.out.println("the cell at (" + row + "," + col + ") is assigned (" + puzzle.getNum(row,col) + ")");
      return;
    }
    System.out.print("Enter assigned value " + puzzle.getSetAsString(row, col) + ": ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid value");
      return;
    }
    int num = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((num<1) || (num>Futoshiki.SETSIZE)) {
      System.out.println("invalid value");
      return;
    }
    if (!puzzle.isValidAssign(row, col, num)) {
      System.out.println("invalid value assignment");
      return;
    }
    assign(row, col, num);
  }
  
  /**
   * Add a relation to the Futoshiki puzzle
   */
  private void relation() {
    System.out.print("Enter  row of greater cell (1 to " + Futoshiki.SETSIZE + "): ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid greater row");
      return;
    }
    int gr = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((gr<1) || (gr>Futoshiki.SETSIZE)) {
      System.out.println("invalid greater row");
      return;
    }
    System.out.print("Enter column of greater cell (1 to " + Futoshiki.SETSIZE + "): ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid greater column");
      return;
    }
    int gc = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((gc<1) || (gc>Futoshiki.SETSIZE)) {
      System.out.println("invalid greater column");
      return;
    }
    System.out.print("Enter row of lesser cell (1 to " + Futoshiki.SETSIZE + "): ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid lesser row");
      return;
    }
    int lr = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((lr<1) || (lr>Futoshiki.SETSIZE)) {
      System.out.println("invalid lesser row");
      return;
    }
    System.out.print("Enter column of lesser cell (1 to " + Futoshiki.SETSIZE + "): ");
    if (!scnr.hasNextInt()) {
      scnr.nextLine(); // clear the line
      System.out.println("invalid lesser column");
      return;
    }
    int lc = scnr.nextInt();
    scnr.nextLine(); // clear the line
    if ((lc<1) || (lc>Futoshiki.SETSIZE)) {
      System.out.println("invalid lesser column");
      return;
    }
    if (!puzzle.isValidRelation(gr, gc, lr, lc)) {
      System.out.println("invalid relation");
      return;
    }
    addRelEntry(gr, gc, lr, lc);
  }
  
  /**
   * Assign a cell to a single value.  This may result in iterative
   * changes to multiple cells and the whole Futoshiki ...
   * 
   * @param row the cell row
   * @param col the cell column
   * @param num the assigned number
   */
  void assign(int row, int col, int num) {
    if ((row<1) || (row>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid row (" + row + ")");
    if ((col<1) || (col>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid col (" + col + ")");
    if ((num<1) || (num>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid number (" + num + ")");
    if (!puzzle.isValidAssign(row, col, num))
      throw new FutoshikiException("invalid assign (" + row + "," + col + "," + num + ")");
    Assign a = new Assign(row, col, num);
    if (!puzzle.isValidAssign(a)) {
      System.out.println("invalid value assignment");
      return;
    }
    puzzle.assign(a);
    puzzle.solve();
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
  void addRelEntry(int gr, int gc, int lr, int lc) {
    if ((gr<1) || (gr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater row (" + gr + ")");
    if ((gc<1) || (gc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid greater col (" + gc + ")");
    if ((lr<1) || (lr>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser row (" + lr + ")");
    if ((lc<1) || (lc>Futoshiki.SETSIZE))
      throw new FutoshikiException("invalid lesser col (" + lc + ")");
    if (puzzle.containsRelEntry(gr, gc, lr, lc))
      throw new FutoshikiException("cannot add the same Relation twice");
    RelEntry re = new RelEntry(gr, gc, lr, lc);
    if (!puzzle.isValidRelation(re)) {
      System.out.println("invalid relation");
      return;
    }
    puzzle.addRelation(re);
    puzzle.solve();
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

  public static void main(String[] args) {
    FutoshikiUI ui = new FutoshikiUI();
    ui.menu();
  }
  
  private Scanner          scnr   = null;
  private Futoshiki        puzzle = null;
  
  public static final String REL_RIGHT = ">";
  public static final String REL_LEFT  = "<";
  public static final String REL_UP    = "^";
  public static final String REL_DOWN  = "v";
  
  private static boolean   traceOn = false; // for debugging
}
