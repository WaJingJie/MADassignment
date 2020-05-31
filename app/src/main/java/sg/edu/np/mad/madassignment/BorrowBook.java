package sg.edu.np.mad.madassignment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BorrowBook extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText borrowdate;
    EditText duedate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowbook);
        borrowdate = findViewById(R.id.dateborrowed);
        borrowdate.setInputType(InputType.TYPE_NULL);

        duedate = findViewById(R.id.duedatefield);
        duedate.setInputType(InputType.TYPE_NULL);

        //shows date picker when the text box is click
        borrowdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //auto fills up the due date by 2 weeks
        borrowdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                String d = borrowdate.getText().toString();

                String[] dsplit = d.split("/");

                int day = Integer.parseInt(dsplit[0]);
                int month = Integer.parseInt(dsplit[1]);
                int year = Integer.parseInt(dsplit[2]);

                GregorianCalendar calendar = new GregorianCalendar();
                calendar.set(year,day-1,month+14);

                Date getDate = calendar.getTime();
                DateFormat sdf = new SimpleDateFormat("M/dd/yy");


                String date = sdf.format(getDate);
                duedate.setText(date);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());


        borrowdate = findViewById(R.id.dateborrowed);
        borrowdate.setText(currentDateString);
    }
}
