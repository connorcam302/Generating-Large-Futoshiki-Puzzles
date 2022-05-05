package futoshikigenerator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import futoshikisolver.*;

public class Testing {
	
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
	
	static void addRel(Futoshiki puzzle, int gc, int lc) {
		int gcr;
		int gcc;
		int lcr;
		int lcc;
		
		gcr = (gc / Futoshiki.SETSIZE) + 1;
		gcc = (gc % Futoshiki.SETSIZE) + 1;
		lcr = (lc / Futoshiki.SETSIZE) + 1;
		lcc = (lc % Futoshiki.SETSIZE) + 1;
		
		puzzle.addRelation(gcr, gcc, lcr, lcc);
	}
	
	static void addRelEntry(int gc, int lc) {
		int gcr;
		int gcc;
		int lcr;
		int lcc;
		
		gcr = (gc / Futoshiki.SETSIZE) + 1;
		gcc = (gc % Futoshiki.SETSIZE) + 1;
		lcr = (lc / Futoshiki.SETSIZE) + 1;
		lcc = (lc % Futoshiki.SETSIZE) + 1;
		
		InstanceGenerator.relations.add(new RelEntry(gcr,gcc,lcr,lcc));
	}
	
	public static void main(String args[]) {
		for(int i = 0; i < 10; i++) {
			long startTime = System.currentTimeMillis();
			Futoshiki finalPuzzle = null;
			InstanceGenerator gen = new InstanceGenerator();
			while(finalPuzzle == null) {
				try {
					boolean validPuzzle = false;
					while(!validPuzzle) {
						gen.makeInstance();
						if(gen.testBasePuzzle()) {
							validPuzzle = true;
						}
						finalPuzzle = InstanceGenerator.basePuzzle;
					}
				} catch(Exception e){}
			}
			System.out.print(millisToShortDHMS( System.currentTimeMillis() - startTime ));
			System.out.print(System.getProperty("line.separator"));
		}
	}
}
