package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * An exception class for conflicts when they occur.
 * Allows the user to set a custom exception message.
 * Otherwise, uses a default message and the default serial UID for serialization.
 * @author Ryan
 * @version 01/23/2023
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor for ConflictException
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
	
	/**
	 * Constructor for ConflictException with a custom message.
	 * @param message the custom message
	 */
	public ConflictException(String message) {
		super(message);
	}
}
