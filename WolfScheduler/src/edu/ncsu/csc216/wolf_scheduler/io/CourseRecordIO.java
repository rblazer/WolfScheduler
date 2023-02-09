/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * Reads Course records from text files. Write a set of CourseRecords to a file for
 * use in the GUI. Filters out invalid courses and invalid formats.
 * @author Ryan Blazer
 * @version 1/16/23
 */
public class CourseRecordIO {
	
	/**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    ArrayList<Course> courses = new ArrayList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return courses;
	}
	/**
	 * Processes each line from the course file.
	 * @param line the next line in the text file.
	 * @return a valid course to add to the list
	 * @throws IllegalArgumentException if file has more tokens after read lines.
	 */
	private static Course readCourse(String line) {
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");
		try {
			String courseName = lineReader.next();
			String courseTitle = lineReader.next();
			String courseSection = lineReader.next();
			int courseCredits = lineReader.nextInt();
			String courseInstructor = lineReader.next();
			String courseMeetingDays = lineReader.next();
			
			if ("A".equals(courseMeetingDays)) {
				if (lineReader.hasNext()) {
					lineReader.close();
					throw new IllegalArgumentException("Invalid course.");
				} else {
					lineReader.close();
					return new Course(courseName, courseTitle, courseSection, courseCredits, courseInstructor, courseMeetingDays);
				}
			} else {
				int courseStartTime = lineReader.nextInt();
				int courseEndTime = lineReader.nextInt();
				if (lineReader.hasNext()) {
					lineReader.close();
					throw new IllegalArgumentException("Invalid course.");
				}
				lineReader.close();
				return new Course(courseName, courseTitle, courseSection, courseCredits, courseInstructor, courseMeetingDays, courseStartTime, courseEndTime);
			}
			
		} catch (Exception e){
			
			throw new IllegalArgumentException("Invalid course.");
		}
	}

}
