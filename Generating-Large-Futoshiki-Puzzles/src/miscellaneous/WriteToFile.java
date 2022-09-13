package miscellaneous;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.time.LocalTime;


/**
* WriteToFile
* 
* Used to write puzzles to files. Places the files inside of the instances folder.
*
* @author Connor Campbell
* @todo
*
* @version April 2020
*/


public class WriteToFile {
	
	private FileWriter myWriter;
	
	/**
	* WriteToFile() 
	* 
	* Names the file after the current time, replaces : with . for file formatting restrictions.
	*/
	
	public WriteToFile() {
		  String currTime = LocalTime.now().toString();
		  
		  currTime = currTime.replaceAll(":", ".");
		    	
	      try {
			this.myWriter = new FileWriter(System.getProperty("user.dir") + "\\instances\\Generation"+ currTime + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
  /**
  * writeToFile(String str)
  * 
  * Takes a string and appends it to the text file.
  *
  * @param String str   The string that is to be appended to the file.
  */
  
  
  public void writeToFile(String str) {
	  try {
		this.myWriter.write(str);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  /**
  * closeWriter()
  * 
  * FileWriters must be closed in order to save the content that has been written to the 
  * file. This is invoked after all writing is complete.
  */
  
  public void closeWriter() {
	  try {
		this.myWriter.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}