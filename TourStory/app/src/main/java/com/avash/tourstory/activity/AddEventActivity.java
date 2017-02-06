package com.avash.tourstory.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.avash.tourstory.R;
import com.avash.tourstory.database.EventManager;
import com.avash.tourstory.model.EventModel;

import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {
    private EditText destinationET,fromDateET,toDateET,budgetET;
    EventManager eventManager;
    int year,month,dayOfMonth;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        destinationET= (EditText) findViewById(R.id.travelDestinationEditText);
        fromDateET= (EditText) findViewById(R.id.fromDateEditText);
        toDateET= (EditText) findViewById(R.id.toDateEditText);
        budgetET= (EditText) findViewById(R.id.estimatedBudgetEditText);
        eventManager = new EventManager(this);
        SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid",0);
    }

    public void saveEvent(View view) {
        String destination=destinationET.getText().toString();
        String fromDate=fromDateET.getText().toString();
        String toDate=toDateET.getText().toString();
        int budget=Integer.parseInt(budgetET.getText().toString());

        EventModel eventModel=new EventModel(uid,0,"",destination,budget,fromDate,toDate);
        long result = eventManager.addEvent(eventModel);
        if(result>0){
            Toast.makeText(this, "Event Successfully Added !!!", Toast.LENGTH_SHORT).show();
        }
        Intent viewIntent=new Intent(AddEventActivity.this,ViewAllEventActivity.class);
        startActivity(viewIntent);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    //date picker
    public void fromDatePicker(View view) {
        Calendar calendar=Calendar.getInstance(Locale.getDefault());
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fromDateET.setText(  new StringBuilder()
                                .append(dayOfMonth)
                                .append("/")
                                .append(month)
                                .append("/")
                                .append(year));
                    }
                },year,month,dayOfMonth);
        datePickerDialog.show();
    }

    public void toDatePicker(View view) {
        Calendar calendar=Calendar.getInstance(Locale.getDefault());
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        toDateET.setText(  new StringBuilder()
                                .append(dayOfMonth)
                                .append("/")
                                .append(month)
                                .append("/")
                                .append(year));
                    }
                },year,month,dayOfMonth);
        datePickerDialog.show();
    }
}
