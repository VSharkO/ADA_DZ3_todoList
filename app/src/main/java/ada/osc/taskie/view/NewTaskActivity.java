package ada.osc.taskie.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import ada.osc.taskie.R;
import ada.osc.taskie.model.Task;
import ada.osc.taskie.model.TaskPriority;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskActivity extends AppCompatActivity {

	@BindView(R.id.edittext_newtask_title)	EditText mTitleEntry;
	@BindView(R.id.edittext_newtask_description) EditText mDescriptionEntry;
	@BindView(R.id.spinner_newtask_priority) Spinner mPriorityEntry;
	@BindView(R.id.button_newtask_pickdate) Button pickEndDate;
	Calendar pickedDate = null;

	private static final int PICK_DATE = 20;
	public static final String PICKED_DATE = "date";
	private Task newTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);
		ButterKnife.bind(this);
		setUpSpinnerSource();

	}

	private void setUpSpinnerSource() {
		mPriorityEntry.setAdapter(
				new ArrayAdapter<TaskPriority>(
						this, android.R.layout.simple_list_item_1, TaskPriority.values()
				)
		);
		mPriorityEntry.setSelection(0);
	}

	@OnClick(R.id.imagebutton_newtask_savetask)
	public void saveTask(){
		String title = mTitleEntry.getText().toString();
		String description = mDescriptionEntry.getText().toString();
		TaskPriority priority = (TaskPriority) mPriorityEntry.getSelectedItem();

		if(!title.equals("")&&!description.equals("")) {
			if(pickedDate==null) {
				newTask = new Task(title, description, priority);
			}else{
				newTask = new Task(title, description, priority, pickedDate);
			}
			Intent saveTaskIntent = new Intent(this, TasksActivity.class);
			saveTaskIntent.putExtra(TasksActivity.EXTRA_TASK, newTask);
			setResult(RESULT_OK, saveTaskIntent);
			finish();
		}
	}

	@OnClick(R.id.button_newtask_pickdate)
	public void pickDate(){
		Intent pickDate = new Intent();
		pickDate.setClass(this, PickDateActivity.class);
		startActivityForResult(pickDate, PICK_DATE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == PICK_DATE && resultCode == RESULT_OK) {
			if (data != null && data.hasExtra(PICKED_DATE)) {
				pickedDate = (Calendar) data.getSerializableExtra(PICKED_DATE);
			}
		}
	}
}
