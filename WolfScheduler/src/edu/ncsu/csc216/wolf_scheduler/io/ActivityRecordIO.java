/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;

/**
 * Writes activites to a file. Uses the activity class to
 * write appropriate file details depending on if its a Course or Event.
 * Exported in the correct format for correct viewing in the GUI.
 * @author Ryan
 * @version 01/25/2023
 */
public class ActivityRecordIO {

	/**
	 * Writes the given list of Activities to a file
	 * @param fileName file to save to 	
	 * @param activities list of activites to save
	 * @throws IOException if the file cannot be written
	 */
	public static void writeActivityRecords(String fileName, ArrayList<Activity> activities) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (Activity a : activities) {
			fileWriter.println(a.toString());
		}
	
		fileWriter.close();
		
	}

}
