package futoshikigenerator;

import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import futoshikisolver.*;
import miscellaneous.*;

@SuppressWarnings("ucd")
class CreatePuzzle {
	private static Futoshiki finalPuzzle = null;
	private static long generationStartTime = System.currentTimeMillis();
	private static InstanceGenerator gen = new InstanceGenerator();
	private static Backtracker back = new Backtracker();
	private static int counter = 0;
	
	/**
	* testOutput(String str)
	* 
	* Class specific console logging, will only log when testMode boolean is
	* true. Used purely for debugging and testing.
	*
	* @param String str   The string to be written to console.
	*/
	
	private static boolean testMode = false;
	public static void testOutput(String str){
		if(testMode) {
			System.out.println(str);
		}
	}

	/**
	* millisToShortDHMS(long duration)
	* 
	* Converts a time from milliseconds to a days:hours:minutes:seconds:milliseconds format
	* Credit to Yash on StackOverflow for this function.
	* (https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java)
	*
	* @param long duration   The number of milliseconds to be converted.
	*/

	public static String millisToShortDHMS(long duration) {
	    String res = "";    // java.util.concurrent.TimeUnit;
	    long days       = TimeUnit.MILLISECONDS.toDays(duration);
	    long hours      = TimeUnit.MILLISECONDS.toHours(duration) -
	                      TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
	    long minutes    = TimeUnit.MILLISECONDS.toMinutes(duration) -
	                      TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
	    long seconds    = TimeUnit.MILLISECONDS.toSeconds(duration) -
	                      TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
	    long millis     = TimeUnit.MILLISECONDS.toMillis(duration) - 
	                      TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
	    if (days == 0)      res = String.format("%02d:%02d:%02d.%04d", hours, minutes, seconds, millis);
	    else                res = String.format("%dd %02d:%02d:%02d.%04d", days, hours, minutes, seconds, millis);
	    return res;
	}

	/**
	* generateFinalPuzzle() 
	* 
	* Generates a puzzle and tests its uniqueness. Generates an instance and tests it to make sure its valid, will
	* repeat generation until it's valid. Will then check if the puzzle has been solved by 
	* solver methods and if not it will pass it to the backtracker.
	*
	* @param [type] $[var]   [Description]
	*/

	public static void generateFinalPuzzle() {
		try {
			boolean validPuzzle = false;
			while(!validPuzzle) {
				gen.makeInstance();
				if(gen.testBasePuzzle()) {
					validPuzzle = true;
				}
			}
			InstanceGenerator.basePuzzle.display();
			back = new Backtracker();
			testOutput("Puzzle " + counter++ + " | " + millisToShortDHMS( System.currentTimeMillis() - generationStartTime ));
			if(InstanceGenerator.basePuzzle.isAssigned()) {
				finalPuzzle = InstanceGenerator.basePuzzle;
			}
			if(back.tracePuzzle()) {
				finalPuzzle = InstanceGenerator.basePuzzle;
			}
		} catch(Exception e){}
	}

	/**
	*  main(String args[])
	* 
	* Generates a unique puzzle. Uses ExecutorService as a timer to set a timeout for when the puzzle should be
	* abandoned. The generation process is repeated until a unique puzzle is found. The puzzle is then printed 
	* to a .txt file using WriteToFile in the JPOP style format.
	*/
	
	public static void main(String args[]) {
		counter = 0;
		long startTime = System.currentTimeMillis();
		WriteToFile FW = new WriteToFile();
		
		for(int j = 0; j < 1; j++) {
		while(finalPuzzle == null) {
			ExecutorService service = Executors.newSingleThreadExecutor();
			try {
			    Runnable r = new Runnable() {
			        @Override
			        public void run() {
			        	generateFinalPuzzle();
			        }
			    };

			    Future<?> f = service.submit(r);
			    f.get(10, TimeUnit.MINUTES);
			}
			catch (final InterruptedException e) {
				testOutput("Timeout thread error: " + e.toString());
			}
			catch (final TimeoutException e) {
			    testOutput("Puzzle generation time exceeded.");
			}
			catch (final ExecutionException e) {
				testOutput("Error within generateFinalPuzzle(): " + e.toString());
			}
			finally {
			    service.shutdown();
			}
		}
		Vector<Assign> assignsToWrite = new Vector<Assign>();
		for(int i = 0; i < gen.getAssigns().size();i++) {
			assignsToWrite.add(gen.getAssigns().get(i));
		}
		String currentCell = "";
		FW.writeToFile(Futoshiki.SETSIZE + System.getProperty("line.separator"));
		for(int r = 1; r <= Futoshiki.SETSIZE;r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE;c++) {
				if(assignsToWrite.size() != 0) {
					for(int i = 0; i < assignsToWrite.size();i++) {
						if(assignsToWrite.get(i).getRow() == r && assignsToWrite.get(i).getCol() == c) {
							currentCell = assignsToWrite.get(i).getNum() + " ";
							assignsToWrite.remove(i);
							break;
						} else currentCell = "-1 ";
					} 
				} else currentCell = "-1 ";
				FW.writeToFile(currentCell);
			}
			FW.writeToFile(System.getProperty("line.separator"));
		}
		FW.writeToFile(gen.getRelations().size() + System.getProperty("line.separator"));
		for(int i = 0; i < gen.getRelations().size();i++) {
			int gc = ((gen.getRelations().get(i).getGreaterRow()-1)*4) + (gen.getRelations().get(i).getGreaterCol()-1);
			int lc = ((gen.getRelations().get(i).getLesserRow()-1)*4) + (gen.getRelations().get(i).getLesserCol()-1);
			FW.writeToFile(gc + ">" + lc + System.getProperty("line.separator"));
		}
		System.out.println("Build Order:");
		for(int i = 0; i < gen.getRelations().size();i++) {
			System.out.println(gen.getRelations().get(i).toString());	
		}
		for(int i = 0; i < gen.getAssigns().size();i++) {
			System.out.println(gen.getAssigns().get(i).toString());
		}
		System.out.print("Runtime: " + millisToShortDHMS( System.currentTimeMillis() - startTime ));
		System.out.print(" | Generations: " + counter);
		FW.writeToFile(System.getProperty("line.separator"));
		FW.writeToFile("Runtime " + millisToShortDHMS( System.currentTimeMillis() - startTime)+System.getProperty("line.separator"));
		FW.writeToFile("Generations " + counter +System.getProperty("line.separator"));
		finalPuzzle = null;
		}
		FW.closeWriter();
	}
}
 