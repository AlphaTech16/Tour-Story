package com.avash.tourstory.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.avash.tourstory.adapter.EventRowAdapter;
import com.avash.tourstory.R;
import com.avash.tourstory.database.DatabaseHelper;
import com.avash.tourstory.database.EventManager;
import com.avash.tourstory.model.EventModel;

import java.util.ArrayList;

public class ViewAllEventActivity extends AppCompatActivity {
    private ListView eventListView;
    private EventRowAdapter eventRowAdapter;
    public EventManager eventManager;
    private ArrayList<EventModel> eventModels = null;
    private DatabaseHelper databaseHelper;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_event);

        //bottom navigation
        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottomNavigation);
        selectBottomNavigationMethod();

        SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        int uid = sharedPreferences.getInt("uid",0);

        //event list
        databaseHelper=new DatabaseHelper(this);
        eventListView= (ListView) findViewById(R.id.eventShowListView);
        eventModels=new ArrayList<>();
        eventManager=new EventManager(this);
        eventModels=eventManager.getUserAllEvent(uid);

        if(eventModels!=null){
            eventRowAdapter=new EventRowAdapter(this,eventModels);
            eventListView.setAdapter(eventRowAdapter);
            eventListView.setItemsCanFocus(true);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void selectBottomNavigationMethod() {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nearbyMenuItem:
                            break;
                        case R.id.weatherMenuItem:
                            Intent weatherIntent=new Intent(ViewAllEventActivity.this,WeatherActivity.class);
                            startActivity(weatherIntent);
                            break;
                        case R.id.editProfileMenuItem:
                            Intent editProfileIntent=new Intent(ViewAllEventActivity.this,UpdateProfileActivity.class);
                            startActivity(editProfileIntent);
                            break;
                    }
                    return true;
                }

            });

    }

    //using floating button
    public void AddEvent(View view) {
                Intent addEventIntent=new Intent(ViewAllEventActivity.this,AddEventActivity.class);
                startActivity(addEventIntent);

    }

}
