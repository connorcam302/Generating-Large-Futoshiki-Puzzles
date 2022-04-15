package futoshikigenerator;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import futoshikisolver.*;
import filewriting.*;

public class CreatePuzzle {
	
	private static boolean testMode = false;
	
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
	
	public static void main(String args[]) {

		InstanceGenerator gen = new InstanceGenerator();
		//gen.makeInstance();
		Backtracker back = new Backtracker();
		
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
		
		Futoshiki finalPuzzle = null;
		//gen.makeInstance();
		//back.tracePuzzle();
		WriteToFile FW = new WriteToFile();
	
		int counter = 0;
		long startTime = System.currentTimeMillis();
		System.out.println(LocalTime.now());
		for(int j = 0; j < 1; j++) {
		while(finalPuzzle == null) {
			try {
				gen.makeInstance();
				back = new Backtracker();
				System.out.println("Puzzle " + counter++ + " | " + millisToShortDHMS( System.currentTimeMillis() - startTime ));
				InstanceGenerator.basePuzzle.display();
				if(back.tracePuzzle()) {
					finalPuzzle = InstanceGenerator.basePuzzle;
				}
			} catch(Exception e){
				
			}
		}
		
		long endTime = System.currentTimeMillis();
		finalPuzzle.display();
		System.out.println("Build Order:");
		for(int i = 0; i < gen.getRelations().size();i++) {
			System.out.println(gen.getRelations().get(i).toString());
			FW.writeToFile(gen.getRelations().get(i).toString()+ System.getProperty("line.separator"));
			
		}
		for(int i = 0; i < gen.getAssigns().size();i++) {
			System.out.println(gen.getAssigns().get(i).toString());
			FW.writeToFile(gen.getAssigns().get(i).toString()+ System.getProperty("line.separator"));
		}
		System.out.print("Runtime: " + millisToShortDHMS( System.currentTimeMillis() - startTime ));
		FW.writeToFile("Runtime: " + millisToShortDHMS( System.currentTimeMillis() - startTime )+ System.getProperty("line.separator"));
		FW.writeToFile(System.getProperty("line.separator"));
		finalPuzzle = null;
		}
		FW.closeWriter();
	}
}
 