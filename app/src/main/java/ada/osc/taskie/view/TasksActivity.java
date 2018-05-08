package ada.osc.taskie.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;

import ada.osc.taskie.R;
import ada.osc.taskie.TaskRepository;
import ada.osc.taskie.model.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity {

	private static final String TAG = TasksActivity.class.getSimpleName();
	private static final int REQUEST_NEW_TASK = 10;
	public static final String EXTRA_TASK = "task";
	TaskRepository mRepository = TaskRepository.getInstance();
	TaskAdapter mTaskAdapter;
	boolean sorted=false,uncompleted=false;
	@BindView(R.id.fab_tasks_addNew) FloatingActionButton mNewTask;
	@BindView(R.id.recycler_tasks) RecyclerView mTasksRecycler;
	@BindView(R.id.my_toolbar) Toolbar myToolbar;

	TaskClickListener mListener = new TaskClickListener() {
		@Override
		public void onClick(Task task) {
			toastTask(task);
		}

		@Override
		public void onLongClick(final Task task) {

			AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this);
			builder.setTitle(R.string.options)
					.setItems(R.array.optons_array, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if(which==0){
								mRepository.removeTask(task);
							}else if(which==1){
								updateTaskActivity(task.getId());
							}
							updateTasksDisplay();
						}
					}).show();
		}

		@Override
		public void onPriorityClick(Task task) {
			task.changePriority();
			updateTasksDisplay();
		}

		@Override
		public void onIsCompletedClick(Task task){
			task.setCompleted(!task.isCompleted());
			updateTasksDisplay();

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		updateTasksDisplay();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
		ButterKnife.bind(this);
		setUpRecyclerView();
		updateTasksDisplay();
		setSupportActionBar(myToolbar);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	private void setUpRecyclerView() {

		int orientation = LinearLayoutManager.VERTICAL;

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
				this,
				orientation,
				false
		);

		RecyclerView.ItemDecoration decoration =
				new DividerItemDecoration(this, orientation);

		RecyclerView.ItemAnimator animator = new DefaultItemAnimator();

		mTaskAdapter = new TaskAdapter(mListener);

		mTasksRecycler.setLayoutManager(layoutManager);
		mTasksRecycler.addItemDecoration(decoration);
		mTasksRecycler.setItemAnimator(animator);
		mTasksRecycler.setAdapter(mTaskAdapter);
	}

	private void updateTasksDisplay() {
		List<Task> tasks;
		if (sorted) {
			mRepository.getSorted();
			mRepository.getUncompletedSorted();
		}
		if (uncompleted) {
			tasks = mRepository.getUncompleted();
		}else{
			tasks = mRepository.getTasks();
		}

			mTaskAdapter.updateTasks(tasks);
			for (Task t : tasks) {
				Log.d(TAG, t.getTitle());
			}
			mRepository.setIDs();
		}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menuSortItem:
				sorted = !sorted;
				updateTasksDisplay();
				if(sorted)
					myToolbar.getMenu().findItem(R.id.menuSortItem)
							.setTitle(R.string.unsorted);
				else
					myToolbar.getMenu().findItem(R.id.menuSortItem)
							.setTitle(R.string.sort);
				return true;

			case R.id.menuHideCompletedItem:
				uncompleted =!uncompleted;
				updateTasksDisplay();
				if(uncompleted)
					myToolbar.getMenu().findItem(R.id.menuHideCompletedItem)
							.setTitle(R.string.showAll);
				else myToolbar.getMenu().findItem(R.id.menuHideCompletedItem)
						.setTitle(R.string.showUncompleted);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	private void toastTask(Task task) {
		Toast.makeText(
				this,
				task.getTitle() + "\n" + task.getDescription()+"\n"+task.getPickedDateString()
						+ "\n" + task.isCompleted() + "\n"+task.getPriority(),
				Toast.LENGTH_SHORT
		).show();
	}

	@OnClick(R.id.fab_tasks_addNew)
	public void startNewTaskActivity(){

		Intent newTask = new Intent();
		newTask.setClass(this, NewTaskActivity.class);
		startActivityForResult(newTask, REQUEST_NEW_TASK);

	}

	public void updateTaskActivity(int taskIndex){

		Intent newTask = new Intent();
		newTask.setClass(TasksActivity.this, NewTaskActivity.class);
		newTask.putExtra(EXTRA_TASK,taskIndex);
		startActivity(newTask);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == REQUEST_NEW_TASK && resultCode == RESULT_OK) {
			if (data != null && data.hasExtra(EXTRA_TASK)) {
				Task task = (Task) data.getSerializableExtra(EXTRA_TASK);
				mRepository.saveTask(task);
				updateTasksDisplay();
			}
		}
	}
}
