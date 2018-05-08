package ada.osc.taskie.persistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ada.osc.taskie.model.PriorityComparator;
import ada.osc.taskie.model.Task;

public class FakeDatabase {

	private List<Task> mTasks;
	private List<Task> mTasksUncompleted=null;

	public FakeDatabase(){
		mTasks = new ArrayList<>();
	}

	public List<Task> getTasks(){
		List<Task> allTasks = new ArrayList<>(mTasks);
		if (mTasksUncompleted!=null){
			allTasks.addAll(mTasksUncompleted);
			setIDs(allTasks);
		}
		return allTasks;
	}

	public Task getTask(int id){
		return mTasks.get(id);
	}

	public void save(Task task){
		mTasks.add(task);
	}

	public void save(List<Task> tasks){
		mTasks.addAll(tasks);
	}

	public void delete(Task task) {
		mTasks.remove(task);
	}

	protected void setIDs(List<Task> tasks) {
		int i=0;
		for(Task t : tasks){
			t.setID(i);
			i++;
		}
	}

	public void setIDsAll() {
		setIDs(mTasks);
	}

	public void setIDsUncompleted(){
		setIDs(mTasksUncompleted);
	}

	protected void sort(List<Task> tasks){
		Collections.sort(tasks,new PriorityComparator());
		setIDsAll();
	}

	public void sortAll(){
		sort(mTasks);
	}

	public void sortUncompleted(){
		sort(mTasksUncompleted);
	}

	public List<Task> getUncompleted(){
		mTasksUncompleted = new ArrayList<>();
		for (Task taskie:mTasks) {
			if(!taskie.isCompleted()){
				mTasksUncompleted.add(taskie);
			}
		}
		sortUncompleted();
		setIDsUncompleted();

		return mTasksUncompleted;
	}

}
