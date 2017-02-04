package com.avash.tourstory.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.avash.tourstory.R;
import com.avash.tourstory.database.EventManager;
import com.avash.tourstory.model.EventModel;

public class AddEventActivity extends AppCompatActivity {
    private EditText destinationET,fromDateET,toDateET,budgetET;
    EventManager eventManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        destinationET= (EditText) findViewById(R.id.travelDestinationEditText);
        fromDateET= (EditText) findViewById(R.id.fromDateEditText);
        toDateET= (EditText) findViewById(R.id.toDateEditText);
        budgetET= (EditText) findViewById(R.id.estimatedBudgetEditText);
    }

    public void saveEvent(View view) {
        String destination=destinationET.getText().toString();
        String fromDate=fromDateET.getText().toString();
        String toDate=toDateET.getText().toString();
        int budget=Integer.parseInt(budgetET.getText().toString());

        EventModel eventModel=new EventModel();

    }
}
