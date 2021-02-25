package com.example.employeeprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//CREATED BY ZEESHAN SHAIKH
public class MainActivity extends AppCompatActivity {
    EditText n,e,p,c;
    Button b;

    DatabaseHelper mDatabaseHelper;

    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;
    private Activity mActivity;
    private TextView mDate;
    private Button ab,vb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        mSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault());
        mDate = (EditText) findViewById(R.id.datetime);
        mDate.setOnClickListener(textListener);

        n = (EditText) findViewById(R.id.editTextTextPersonName);
        e = (EditText) findViewById(R.id.editTextTextEmailAddress);
        p = (EditText) findViewById(R.id.editTextPhone);
        c = (EditText) findViewById(R.id.editTextTextPersonName2);


        ab = (Button) findViewById(R.id.add_button);
        vb = (Button) findViewById(R.id.view_button);

        ab.setOnClickListener((v -> {
            String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if (!n.getText().toString().matches("(^[A-Za-z]+$)"))
            {
                n.setError("Enter only characters please");
            }
            else if (e.length() == 0 || !Pattern.compile(EMAIL_STRING).matcher(e.getText().toString()).matches())
            {
                e.setError("Please enter valid Email");
            }
            else if (!p.getText().toString().matches("[0-9]{10}"))
            {
                p.setError("Enter only 10 digit number");
            }
            else if (!c.getText().toString().matches("(^[A-Za-z]+$)"))
            {
                c.setError("Enter only characters please");
            }
            else if(mDate.length() == 0)
            {
                mDate.setError("Please choose correct date");
            }
            else {

                AddData(n.getText().toString(),e.getText().toString(),p.getText().toString(),c.getText().toString(),mDate.getText().toString());
                n.setText("");
                e.setText("");
                p.setText("");
                c.setText("");
                mDate.setText("");
            }

        }));

        vb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                startActivity(intent);
            }
        });

    }


        /* Define the onClickListener, and start the DatePickerDialog with users current time */
        private final View.OnClickListener textListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                new DatePickerDialog(mActivity, mDateDataSet, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        };

        /* After user decided on a date, store those in our calendar variable and then start the TimePickerDialog immediately */
        private final DatePickerDialog.OnDateSetListener mDateDataSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(mActivity, mTimeDataSet, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
            }
        };

        /* After user decided on a time, save them into our calendar instance, and now parse what our calendar has into the TextView */
        private final TimePickerDialog.OnTimeSetListener mTimeDataSet = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                mDate.setText(mSimpleDateFormat.format(mCalendar.getTime()));
            }
        };



    public void AddData(String newEntry1,String newEntry2,String newEntry3,String newEntry4,String newEntry5) {
        boolean insertData = mDatabaseHelper.addData(newEntry1,newEntry2,newEntry3,newEntry4,newEntry5);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

