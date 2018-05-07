package ada.osc.taskie.view;

import ada.osc.taskie.model.Task;

public interface TaskClickListener {
	void onClick(Task task);
	void onLongClick(Task task);
	void onPriorityClick(Task task);
	void onIsCompletedClick(Task task);
}
