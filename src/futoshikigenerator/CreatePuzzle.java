package futoshikigenerator;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import futoshikisolver.*;
import miscellaneous.*;

public class CreatePuzzle {
	
	
	
	private static boolean testMode = false;
	private static Futoshiki finalPuzzle = null;
	private static long generationStartTime = System.currentTimeMillis();
	private static long waitTime = 1000;
	private static InstanceGenerator gen = new InstanceGenerator();
	private static Backtracker back = new Backtracker();
	private static int counter = 0;
	private static boolean startTimer = false;
	private static boolean resetGeneration = false;

	
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
	
	public static void startTimer() {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + waitTime;
		
		while(startTime < endTime) {
			resetGeneration = true;
		}
	}
	
	public static void generateFinalPuzzle() {
		long endTime = 1;
		long traceStartTime = 0;
		
		traceStartTime = System.currentTimeMillis();
		endTime = traceStartTime + waitTime;
		try {
			boolean validPuzzle = false;
			while(!validPuzzle) {
				gen.makeInstance();
				if(gen.testBasePuzzle()) {
					validPuzzle = true;
				} else System.out.println("puzzle was invalid");
			}
			System.out.println("started at: " + millisToShortDHMS(traceStartTime));
			System.out.println("finish at: " + millisToShortDHMS(endTime));
			back = new Backtracker();
			System.out.println("Puzzle " + counter++ + " | " + millisToShortDHMS( System.currentTimeMillis() - generationStartTime ));
			InstanceGenerator.basePuzzle.display();
			if(InstanceGenerator.basePuzzle.isAssigned()) {
				System.out.println("setting final");
				finalPuzzle = InstanceGenerator.basePuzzle;
			}
			if(back.tracePuzzle()) {
				System.out.println("setting final");
				finalPuzzle = InstanceGenerator.basePuzzle;
			}
		} catch(Exception e){
		}
	}
	
	public static void main(String args[]) {
		long startTime = System.currentTimeMillis();
		
		Futoshiki uniquePuzzle = new Futoshiki();
		Futoshiki uniquePuzzle2 = new Futoshiki();
		Futoshiki nonuniquePuzzle  = new Futoshiki();
		
		uniquePuzzle.assign(1, 1, 3);
		uniquePuzzle.assign(4, 4, 2);
		uniquePuzzle.addRelation(2, 3, 3, 3);
		uniquePuzzle.addRelation(2, 4, 3, 4);
		uniquePuzzle.addRelation(3, 4, 3, 3);
		uniquePuzzle.addRelation(2, 2, 2, 1);
				
		uniquePuzzle2.addRelation(1, 3, 2, 3);
		uniquePuzzle2.addRelation(1, 4, 2, 4);
		uniquePuzzle2.addRelation(2, 3, 3, 3);
		uniquePuzzle2.addRelation(3, 4, 3, 3);
		uniquePuzzle2.addRelation(3, 1, 3, 2);
		uniquePuzzle2.assign(4, 1, 1);
		uniquePuzzle2.assign(4, 4, 3);
		
		nonuniquePuzzle.addRelation(1, 3, 2, 3);
		nonuniquePuzzle.addRelation(1, 4, 2, 4);
		nonuniquePuzzle.addRelation(2, 3, 3, 3);
		nonuniquePuzzle.assign(4, 4, 3);
		
		//InstanceGenerator.basePuzzle = uniquePuzzle;
		//gen.makeInstance();
		//back.tracePuzzle();
		WriteToFile FW = new WriteToFile();
		
		System.out.println(LocalTime.now());
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

			    f.get(5, TimeUnit.MINUTES);     // attempt the task for two minutes
			    //f.get(5, TimeUnit.SECONDS);     // attempt the task for two minutes
			}
			catch (final InterruptedException e) {
			    // The thread was interrupted during sleep, wait or join
			}
			catch (final TimeoutException e) {
			    // Took too long!
			}
			catch (final ExecutionException e) {
			    // An exception from within the Runnable task
			}
			finally {
			    service.shutdown();
			}
		}

		finalPuzzle.display();
		Vector<Assign> assignsToWrite = new Vector<Assign>();
		for(int i = 0; i < gen.getAssigns().size();i++) {
			assignsToWrite.add(gen.getAssigns().get(i));
		}
		String currentCell = "";
		FW.writeToFile(Futoshiki.SETSIZE + System.getProperty("line.separator"));
		for(int r = 1; r <= Futoshiki.SETSIZE;r++) {
			for(int c = 1; c <= Futoshiki.SETSIZE;c++) {
				for(int i = 0; i < assignsToWrite.size();i++) {
					if(assignsToWrite.get(i).getRow() == r && assignsToWrite.get(i).getCol() == c) {
						currentCell = assignsToWrite.get(i).getNum() + " ";
						assignsToWrite.remove(i);
						break;
					} else currentCell = "-1 ";
				}
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
//			FW.writeToFile(gen.getRelations().get(i).toString()+ System.getProperty("line.separator"));
			
		}
		for(int i = 0; i < gen.getAssigns().size();i++) {
			System.out.println(gen.getAssigns().get(i).toString());
//			FW.writeToFile(gen.getAssigns().get(i).toString()+ System.getProperty("line.separator"));
		}
		System.out.print("Runtime: " + millisToShortDHMS( System.currentTimeMillis() - startTime ));
//		FW.writeToFile("Runtime: " + millisToShortDHMS( System.currentTimeMillis() - startTime )+ System.getProperty("line.separator"));
		FW.writeToFile(System.getProperty("line.separator"));
		finalPuzzle = null;
		}
		FW.closeWriter();
	}
}
 