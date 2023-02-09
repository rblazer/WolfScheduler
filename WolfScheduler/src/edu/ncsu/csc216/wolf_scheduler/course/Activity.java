package edu.ncsu.csc216.wolf_scheduler.course;
/**
 * An general abstract class representing an Activity for a schedule.
 * Subclasses include a Course and an Event. Commoms aspects are a title,
 * meeting days, and a start and end time. Has ability to set all of these fields, display
 * arrays of child classes and check for duplicates of its instances.
 * @author Ryan Blazer
 * @version 01/25/2023
 */
public abstract class Activity implements Conflict {

	/** Upper time limit for a hour in an Activity schedule */
	private static final int UPPER_HOUR = 24;
	/** Upper time limit for a minute in an Activity schedule */
	private static final int UPPER_MINUTE = 60;
	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;	
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;
	
	/**
	 * Initializes the activity super class with base fields that all subclasses
	 * share.
	 * @param title the title of the Activity
	 * @param meetingDays the meeting days of the Activity
	 * @param startTime the start time for the Activity
	 * @param endTime the end time for the Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Returns the Activity's title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's title. If the title is null or empty an
	 * IllegalArgumentException is thrown.
	 * @param title the title to set
	 * @throws IllegalArgumentException if title parameter is invalid
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the Activity's meeting days.
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Activity's starting time.
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's ending time.
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the meeting days, start time, and end time of a Activity.
	 * @param meetingDays Activity's meeting days
	 * @param startTime start time of the Activity
	 * @param endTime end time of the Activity
	 * @throws IllegalArgumentException if meetingdays, startTime, or endTime is invalid.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

			if (endTime < startTime) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			int startHour = startTime / 100;
			int startMin = startTime % 100;
			int endHour = endTime / 100;
			int endMin = endTime % 100;
			
			if (startHour < 0 || startHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			if (startMin < 0 || startMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			if (endHour < 0 || endHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			if (endMin < 0 || endMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			this.meetingDays = meetingDays;
			this.startTime = startTime;
			this.endTime = endTime;
			
		}
	
	
	
	/**
	 * Returns a meeting string for an Activity meeting day and meeting times.
	 * @return String a string representation of meeting day and times.
	 */
	public String getMeetingString() {
		String meetingString = "";
		meetingString += this.meetingDays + " ";
		meetingString += getTimeString(this.startTime);
		meetingString += "-" + getTimeString(this.endTime);
		
		if ("A".equals(this.meetingDays)) {
			return "Arranged";
		}
			return meetingString;
	}
	
	
	
	/**
	 * Converts military time to standard time and appends start and end times.
	 * @param time military time either start or end time.
	 * @return a string representing the correct time schedule.
	 */
	private String getTimeString(int time) {
		String newTime;
		boolean isPM = false;
		int newHour = time / 100;
		if (newHour > 12) {
			newHour = newHour - 12;
			isPM = true;
		}
		if (newHour == 12) {
			isPM = true;
		}
		int newMin = time % 100;
		newTime = newHour + ":" + newMin;
		if (newMin < 10) {
			newTime += 0;
		}
		if (isPM) {
			newTime += "PM";
		} else {
			newTime += "AM";
		}
		return newTime;
	}
	
	/**
	 * Checks for conflicts between Course events and schedule.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
			String currentMeetingDays = this.getMeetingDays();
			String compareMeetingDays = possibleConflictingActivity.getMeetingDays();
			int currentEndTime = this.getEndTime();
			int currentStartTime = this.getStartTime();
			int compareStartTime = possibleConflictingActivity.getStartTime();
			int compareEndTime = possibleConflictingActivity.getEndTime();
			
			for (int i = 0; i < currentMeetingDays.length(); i++) {
				for (int j = 0; j < compareMeetingDays.length(); j++) {
					if (currentMeetingDays.charAt(i) == compareMeetingDays.charAt(j)) {
						if ("A".equals(compareMeetingDays) && "A".equals(currentMeetingDays)) {
							break;
						} else if (currentStartTime == compareStartTime && currentEndTime == compareEndTime) {
							throw new ConflictException();
						} else if (currentEndTime == compareStartTime || compareEndTime == currentStartTime) {
							throw new ConflictException();
						} else if (compareStartTime < currentEndTime && compareEndTime > currentStartTime) {
							throw new ConflictException();
						}
					} 
						
					}
				}
			}


	/**
	 * Generates a short display of activities based on the subclass.
	 * @return an array of activities
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Generates a long display of activities based on the subclass
	 * @return an array of activities.
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Checks if an Activity is a duplicate of another Activity
	 * @param activity activity we are comparing against
	 * @return true if the activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * Generates a hashCode for Activity using all fields.
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	

}