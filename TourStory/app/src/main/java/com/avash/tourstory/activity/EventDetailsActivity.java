package com.avash.tourstory.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avash.tourstory.R;
import com.avash.tourstory.database.EventManager;


public class EventDetailsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    int eid,budget;
    String destination,title,startDate,endDate;

    TextView destinationTV,budgetTV,startDateTV,endDateTV;
    Button editButton,deleteButton,backButton;

    EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.evenBotottomNavigation);

        destinationTV = (TextView) findViewById(R.id.destEventTextView);
        budgetTV = (TextView) findViewById(R.id.budgetEventTextView);
        startDateTV = (TextView) findViewById(R.id.startDateEventTextView);
        endDateTV = (TextView) findViewById(R.id.endDateEventTextView);

        editButton = (Button) findViewById(R.id.editEventButton);
        deleteButton = (Button) findViewById(R.id.deleteEventButton);
        backButton = (Button) findViewById(R.id.eventDetailsBackButton);

        eventManager = new EventManager(this);

        selectBottomNavigationMethod();

        eid = getIntent().getIntExtra("eid",0);
        budget = getIntent().getIntExtra("budget",0);
        destination = getIntent().getStringExtra("destination");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        destinationTV.setText(destination);
        budgetTV.setText(""+budget);
        startDateTV.setText(startDate);
        endDateTV.setText(endDate);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EventDetailsActivity.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure to delete ?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                long queryResult = eventManager.deleteEvent(eid);
                                if(queryResult>0){
                                    Toast.makeText(getApplicationContext(), "Event successfully deleted!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EventDetailsActivity.this,ViewAllEventActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Sorry! Try Again.", Toast.LENGTH_SHORT).show();
                                }

                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailsActivity.this,AddEventActivity.class);
                intent.putExtra("eid",eid);
                intent.putExtra("destination",destination);
                intent.putExtra("budget",budget);
                intent.putExtra("startDate",startDate);
                intent.putExtra("endDate",endDate);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailsActivity.this,ViewAllEventActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }




    private void selectBottomNavigationMethod() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.eventsMenuItem:
                        break;
                    case R.id.expensesMenuItem:
                        Intent intent=new Intent(EventDetailsActivity.this,ExpenseActivity.class);
                        intent.putExtra("eid",eid);
                        startActivity(intent);
                        break;
                    case R.id.momentsMenuItem:
                        Intent intent1=new Intent(EventDetailsActivity.this,MomentsActivity.class);
                        intent1.putExtra("eid",eid);
                        startActivity(intent1);
                        break;
                }
                return true;
            }

        });

    }
}
