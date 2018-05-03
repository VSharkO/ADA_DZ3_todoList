package ada.osc.taskie.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import ada.osc.taskie.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickDateActivity extends Activity {

    @BindView(R.id.datepicker_pickdate)DatePicker datePicker;
    @BindView(R.id.button_setdate)Button setDateButton;
    Calendar cal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setdate);
        ButterKnife.bind(this);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
    }

    @OnClick(R.id.button_setdate)
    public void onDateSet() {
        cal = Calendar.getInstance();
        cal.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        Intent setDate = new Intent(this, TasksActivity.class);
        setDate.putExtra(NewTaskActivity.PICKED_DATE,cal);
        setResult(RESULT_OK, setDate);
        finish();
    }
}
