package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * Reads, creates, and exports activity schedules using a course catalog.
 * Manages course information and displays and exports final schedules for the user.
 * Allows the import of any valid file with the correct course record format.
 * Allows creation of event and interactions between events and courses in a schedule.
 * @author Ryan Blazer
 * @version 01/12/2023
 */
public class WolfScheduler {
	
	/** A catalog of courses */
	private ArrayList<Course> catalog;
	/** A schedule built off of activities */
	private ArrayList<Activity> schedule;
	/** A title for the schedule */
	private String title;
	
	/**
	 * Constructor for the WolfScheduler that takes in data from a file.
	 * @param fileName the file containing activity records.
	 */
	public WolfScheduler(String fileName) {
		this.schedule = new ArrayList<Activity>();
		this.title = "My Schedule";
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
		
	}
	
	/**
	 * Retrieves the course catalog and converts to a 2D array with
	 * as many rows as courses and 3 columns for the name, section, and
	 * title.
	 * @return catalogArray a 2D array representation of catalog.
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][3];
		
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			catalogArray[i] = c.getShortDisplayArray();
		}
		return catalogArray;
	}
	
	/**
	 * Retrieves the activities from the schedule and creates a activity schedule
	 * in the form of a 2D array with as many rows as the schedule has and 4 columns
	 * for different parameters based on if its a Course or Event.
	 * @return activitySchedule a 2D array representation of the schedule.
	 */
	public String[][] getScheduledActivities() {
		String[][] activitySchedule = new String[schedule.size()][4];
		for (int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			activitySchedule[i] = a.getShortDisplayArray();
		}
		return activitySchedule;
	}
	
	/**
	 * Retrieves the activities from the schedule and creates a full activities schedule
	 * in the form of a 2D array with as many rows as courses and 7 columns
	 * with contents suited for a Course or Event.
	 * @return fullActivitySchedule a 2D array representation of the full schedule.
	 */
	public String[][] getFullScheduledActivities() {
		String[][] fullActivitySchedule = new String[schedule.size()][7];
		for (int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			fullActivitySchedule[i] = a.getLongDisplayArray();
		}
		return fullActivitySchedule;
	}

	/**
	 * Retrieves a course from the catalog.
	 * @param name name of the course
	 * @param section the course's section
	 * @return Course the course desired
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < this.catalog.size(); i++) {
			if (this.catalog.get(i).getName().equals(name) && this.catalog.get(i).getSection().equals(section)) {
				return this.catalog.get(i);
			} 
		}
		return null;
	}
	
	/**
	 * Adds a course to the schedule. Checks if the course exists in the catalog, if
	 * it doesn't it returns false. Also checks if it is already in the schedule, if 
	 * it is then it throws an IllegalArgumentException.
	 * @param name name of the course
	 * @param section section of the course
	 * @return true if the course was successfully added, false otherwise
	 * @throws IllegalArgumentException if the course is already in the schedule
	 */
	public boolean addCourseToSchedule(String name, String section) {
		Course courseToAdd = getCourseFromCatalog(name, section);
		if (courseToAdd == null) {
			return false;
		}
		
		for (int i = 0; i < this.schedule.size(); i++) {
			boolean isADuplicate = courseToAdd.isDuplicate(this.schedule.get(i));
			if (isADuplicate) {
				throw new IllegalArgumentException("You are already enrolled in " + name);
			}
		}
			
		for (int i = 0; i < this.schedule.size(); i++) {
			try {
				courseToAdd.checkConflict(this.schedule.get(i));
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		} 
		
		this.schedule.add(courseToAdd);
		return true;
	}
	
	/**
	 * Adds an event to the schedule.
	 * @param eventTitle title of the Event
	 * @param eventMeetingDays meeting days of the Event
	 * @param eventStartTime start time of the Event
	 * @param eventEndTime end time of the Event
	 * @param eventDetails details of the Event
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
		Event newEvent = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		
		for (int i = 0; i < this.schedule.size(); i++) {
			boolean isADuplicate = newEvent.isDuplicate(this.schedule.get(i));
			if (isADuplicate) {
				throw new IllegalArgumentException("You have already created an event called " + eventTitle);
			}
		}
		
		for (int i = 0; i < this.schedule.size(); i++) {
			try {
				newEvent.checkConflict(this.schedule.get(i));
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The event cannot be added due to a conflict.");
			}
		}
		
		this.schedule.add(newEvent);
	}
	
	/**
	 * Removes a activity from the schedule.
	 * @param idx index of the activity in the schedule.
	 * @return true if the activity was successfully removed, false otherwise
	 */
	public boolean removeActivityFromSchedule(int idx) {
		try {
			schedule.remove(idx);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Resets the schedule to a blank list.
	 */
	public void resetSchedule() {
		this.schedule = new ArrayList<Activity>();
		
	}
	
	/**
	 * Returns the schedule's title.
	 * @return the schedule's title
	 */
	public String getScheduleTitle() {
		return this.title;
	}
	
	/**
	 * Sets the schedule's title. if title is null, then an
	 * IllegalArgumentException is thrown.
	 * @param title title of the schedule
	 * @throws IllegalArgumentException if the title parameter is null
	 */
	public void setScheduleTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
		
	}
	
	/**
	 * Exports the schedule to a file. If the file cannot be saved then 
	 * an IllegalArgumentException is thrown.
	 * @param fileName the name of the file to be exported
	 * @throws IllegalArgumentException if the fileName cannot be saved/is invalid
	 */
	public void exportSchedule(String fileName) {
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		} catch (Exception e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
		
	}

}
