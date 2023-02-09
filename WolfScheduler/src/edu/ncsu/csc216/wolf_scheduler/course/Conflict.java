package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Interface for handling scheduling conflicts between Courses and Events.
 * Checks conflict of a Course or event object with another activity-based object.
 * Throws a ConflictException if there is a conflict between two objects.
 * @author Ryan
 * @version 01/23/2023
 */
public interface Conflict {
	/**
	 * checks if a Conflict occured in Activity's subclasses
	 * @param possibleConflictingActivity the activity that might be causing a conflict
	 * @throws ConflictException if activity is the culprit of conflict
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
