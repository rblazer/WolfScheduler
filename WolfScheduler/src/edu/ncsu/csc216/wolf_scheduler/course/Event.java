package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Creates an Event object to represent an event in a student's schedule.
 * Relevant aspects are the details of the event, other fields and behaivors are
 * extended from Activity. Unique ability to set and get its only unique field and 
 * detect duplicates and unique array displays
 * @author Ryan Blazer
 * @version 01/25/2023
 */
public class Event extends Activity {
	
	/** Represents the details of an Event */
	private String eventDetails;

	/**
	 * Constructs an Event object for all relevant fields.
	 * @param title the title of the Event
	 * @param meetingDays meeting days for an Event
	 * @param startTime starting time for an Event
	 * @param endTime end time for an Event
	 * @param eventDetails details of the Event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * Returns the Event's details.
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets the Event's details.
	 * @param eventDetails the eventDetails to set
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}
	
	/**
	 * Creates an array of length 4 of two empty strings then , title and meeting days
	 * and times.
	 * @return shotDisplayArray an array of length 4 containing the mentioned elements
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplayArray = {"", "", getTitle(), getMeetingString()};
		return shortDisplayArray;
	}
	
	/**
	 * Creates an array of length 7 of 2 empty strings , title, 2 more empty strings, meeting days
	 * and times, event details, and 7th element from Event.
	 * @return shotDisplayArray an array of length 7 containing the mentioned elements
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplayArray = {"", "", getTitle(), "", "", getMeetingString(), this.eventDetails};
		return longDisplayArray;
	}
	
	/**
	 * Returns a comma separated value String of all Event fields.
	 * @return String representation of Event
	 */
	@Override
	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() + "," + this.eventDetails;
	}
	
	/**
	 * Sets meeting times for an Event based on the days of the week
	 * @param meetingDays the meeting days of the Event
	 * @param startTime the starting time of the Event
	 * @param endTime the starting time of the Event
	 * @throws IllegalArgumentException if meetingDays is null or empty.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		char[] days = {'M', 'T', 'W', 'H', 'F', 'S', 'U'};
			int monCount = 0;
			int tuesCount = 0;
			int wedCount = 0;
			int thursCount = 0;
			int friCount = 0;
			int satCount = 0;
			int sunCount = 0;
			for (int i = 0; i < meetingDays.length(); i++) {
				if(meetingDays.charAt(i) == days[0]) {
					monCount++;
				} else if (meetingDays.charAt(i) == days[1]) {
					tuesCount++;
				} else if (meetingDays.charAt(i) == days[2]) {
					wedCount++;
				} else if (meetingDays.charAt(i) == days[3]) {
					thursCount++;
				} else if (meetingDays.charAt(i) == days[4]) {
					friCount++;
				} else if (meetingDays.charAt(i) == days[5]) {
					satCount++;
				} else if (meetingDays.charAt(i) == days[6]) {
					sunCount++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			if (monCount > 1 || tuesCount > 1 || wedCount > 1 || thursCount > 1 || friCount > 1 || satCount > 1 || sunCount > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
	
	/**
	 * Checks if an activity is a duplicate of an Event by comparing the name of the activity
	 * with the name of the event.
	 * @param activity The activity to check for duplication
	 * @return true if activity is a duplicate of an event.
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Event) {
			Event other = (Event) activity;
			if (this.getTitle().equals(other.getTitle())) {
				return true;
			}
		}
		
		return false;
	}
	
}
	
	
	
	

