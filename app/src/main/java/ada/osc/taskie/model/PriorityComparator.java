package ada.osc.taskie.model;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Task>{


    @Override
    public int compare(Task o1, Task o2) {
        int first,second;
        first=setIntPriority(o1.getPriority());
        second=setIntPriority(o2.getPriority());

        return first-second;

    }

    public int setIntPriority(TaskPriority taskPriority){
        int num=0;
        switch (taskPriority.toString()){
            case "LOW":
                num=1;
                break;
            case "MID":
                num=0;
                break;
            case "HIGH":
                num=-1;
        }
        return num;
    }
}
