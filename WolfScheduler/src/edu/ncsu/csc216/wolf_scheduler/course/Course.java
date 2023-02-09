package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Creates a course object to represent a course in a student's schedule.
 * Relevant aspects are its name, section, credits and instructor. All other
 * functionality is extended from Activity and overridden in various ways to suit a
 * Course object.
 * @author Ryan Blazer
 * @version 1/25/2023
 */
public class Course extends Activity {
	
	/** Course's minimum name length */
	private static final int MIN_NAME_LENGTH = 5;
	/** Course's maximum name length */
	private static final int MAX_NAME_LENGTH = 8;
	/** Course's minimum letter count */
	private static final int MIN_LETTER_COUNT = 1;
	/** Course's maximum letter count */
	private static final int MAX_LETTER_COUNT = 4;
	/** Course number digit count */
	private static final int DIGIT_COUNT = 3;
	/** Course section acceptable length */
	private static final int SECTION_LENGTH = 3;
	/** Maximum credits for Course */
	private static final int MAX_CREDITS = 5;
	/** Minimum credits for Course */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	
	/**
	 * Constructs a Course object with values for all fields.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meeting days for Course as series of chars
	 * @param startTime start time for Course
	 * @param endTime end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}
	
	
	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}


	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more than 8,
	 * does not contain a space between letter characters and number characters, has less than 1
	 * or more than 4 letter characters, and not exactly three trailing digit characters, an
	 * IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		int letterCount = 0;
		int digitCount = 0;
		boolean spaceFound = false;
		for (int i = 0; i < name.length(); i++) {
			if (!spaceFound) {
				if (Character.isLetter(name.charAt(i))) {
					letterCount++;
				} else if (name.charAt(i) == ' ') {
					spaceFound = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (spaceFound) {
				if (Character.isDigit(name.charAt(i))) {
					digitCount++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
				
			}
				
			
		}
		if (letterCount < MIN_LETTER_COUNT || letterCount > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		if (digitCount != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		this.name = name;
	}
	
	/**
	 * Returns the Course's section.
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the Course's section. If the section is null or is not exactly three
	 * digits then an IllegalArgumentException is thrown.
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section parameter is invalid
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		int digitCount = 0;
		for (int i = 0; i < section.length(); i++) {
			if (Character.isDigit(section.charAt(i))) {
				digitCount++;
			}
		}
		
		if (digitCount != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		this.section = section;
	}
	
	/**
	 * Returns the Course's credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets the Course's credits. If credit is less than the minimum or
	 * greater than the maximum then an IllegalArgumentException is 
	 * thrown.
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits parameter is invalid.
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}
	
	/**
	 * Returns the Course's instructor.
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets the Course's instructor. If instructorId is null or empty then
	 * an IllegalArgumentException is thrown.
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorId parameter is invalid.
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Sets meeting times for an Course based on the days of the week
	 * @param meetingDays the meeting days of the Course
	 * @param startTime the starting time of the Course
	 * @param endTime the starting time of the Course
	 * @throws IllegalArgumentException if meetingDays is null or empty.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		char[] days = {'M', 'T', 'W', 'H', 'F', 'A'};
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			super.setMeetingDaysAndTime(meetingDays, 0, 0);
		} else {
			int monCount = 0;
			int tuesCount = 0;
			int wedCount = 0;
			int thursCount = 0;
			int friCount = 0;
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == days[0]) {
					monCount++;
				} else if (meetingDays.charAt(i) == days[1]) {
					tuesCount++;
				} else if (meetingDays.charAt(i) == days[2]) {
					wedCount++;
				} else if (meetingDays.charAt(i) == days[3]) {
					thursCount++;
				} else if (meetingDays.charAt(i) == days[4]) {
					friCount++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			if (monCount > 1 || tuesCount > 1 || wedCount > 1 || thursCount > 1 || friCount > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
	}
	


	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	/**
	 * Generates a hashCode for Course using all fields.
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj the object to compare
	 * @return true if the objects are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Creates an array of length 4 of the name, section, title and meeting days
	 * and times.
	 * @return shotDisplayArray an array of length 4 containing the mentioned elements
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplayArray = {this.name, this.section, getTitle(), getMeetingString()};
		return shortDisplayArray;
	}

	/**
	 * Creates an array of length 7 of the name, section, title, credits, meeting days
	 * and times, instructor, and 7th element from Event.
	 * @return shotDisplayArray an array of length 7 containing the mentioned elements
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplayArray = {this.name, this.section, getTitle(), this.credits + "", getInstructorId(), getMeetingString(), ""};
		return longDisplayArray;
	}

	/**
	 * Checks if an activity is a duplicate of a course by comparing the name of the activity
	 * with the name of the course.
	 * @param activity The activity to check for duplication
	 * @return true if activity is a duplicate of a course
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course other = (Course) activity;
			if (this.getName().equals(other.getName())) {
				return true;
			}
		}
		return false;
	}
	
	
	
	


}
