package ada.osc.taskie.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Task implements Serializable{

	private static int sID = 0;

	int mId=sID;
	private String mTitle;
	private String mDescription;
	private boolean mCompleted;
	private TaskPriority mPriority;
	private Calendar mPickedDate;

	public Task(String title, String description, TaskPriority priority, Calendar PickedDate) {
		mTitle = title;
		mDescription = description;
		mCompleted = false;
		mPriority = priority;
		mPickedDate=PickedDate;
	}

	public Task(String title, String description, TaskPriority priority) {
		mTitle = title;
		mDescription = description;
		mCompleted = false;
		mPriority = priority;
	}

	public int getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public boolean isCompleted() {
		return mCompleted;
	}

	public void setCompleted(boolean completed) {
		mCompleted = completed;
	}

	public TaskPriority getPriority() {
		return mPriority;
	}

	public void setPriority(TaskPriority priority) {
		mPriority = priority;
	}

	public String getPickedDateString() {
		if(mPickedDate!=null) {
			String day = String.valueOf(mPickedDate.get(Calendar.DATE));
			String month = String.valueOf(mPickedDate.get(Calendar.MONTH)+1);
			String year = String.valueOf(mPickedDate.get(Calendar.YEAR));

			return day + "." + month + "." + year + ".";
		}else{
			return "No end date";
		}
	}

	public Calendar getPickedDate() {
		return mPickedDate;
	}

	public void setPickedDate(Calendar mPickedDate) {
		this.mPickedDate = mPickedDate;
	}

	public void changePriority(){

		switch (mPriority.toString()) {
			case "LOW":
				setPriority(TaskPriority.MEDIUM);
				break;
			case "MEDIUM":
				setPriority(TaskPriority.HIGH);
				break;
			case "HIGH":
				setPriority(TaskPriority.LOW);
				break;
		}
	}

	public void setID(int id){
		mId = id;
	}
}
