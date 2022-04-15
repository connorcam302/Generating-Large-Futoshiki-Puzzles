package filewriting;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.time.LocalTime;

public class WriteToFile {
	
	FileWriter myWriter;
	
	public WriteToFile() {
		  String currTime = LocalTime.now().toString();
		  
		  currTime = currTime.replaceAll(":", ".");
		    	
	      try {
			myWriter = new FileWriter("C:\\Users\\Connor\\Documents\\School\\University\\Third Year\\Individual Computing Project\\Generations\\Generation"+ currTime + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
  public void main(String[] args) {
    try {
	      myWriter.close();
	      System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
    }
  }
  
  public void writeToFile(String str) {
	  try {
		myWriter.write(str);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public void closeWriter() {
	  try {
		myWriter.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}