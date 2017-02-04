package com.avash.tourstory.activity;

import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.avash.tourstory.Api.WeatherApi;
import com.avash.tourstory.R;
import com.avash.tourstory.model.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    WeatherApi weatherApi;

    TextView avgTextView,maxTextView,minTextView,textTextView;

    ImageView minImageView,maxImageView,textImageView;

    LinearLayout mainLayout;
    LinearLayout linearLayout;

    Spinner spinner;
    String[] cities={"Dhaka","Chittagong","Khulna","Barisal","Rajshahi","Rangpur"};
    String cityName;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        avgTextView = (TextView) findViewById(R.id.avgTextView);
        maxTextView = (TextView) findViewById(R.id.maxTextView);
        minTextView = (TextView) findViewById(R.id.minTextView);
        textTextView = (TextView) findViewById(R.id.textTextView);

        minImageView = (ImageView) findViewById(R.id.minImageView);
        maxImageView = (ImageView) findViewById(R.id.maxImageView);
        textImageView = (ImageView) findViewById(R.id.textImageView);

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        linearLayout = (LinearLayout) findViewById(R.id.firstLayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int width = size.x;
        linearLayout.getLayoutParams().height = height;

        laibraryInitial();
        getData();


        spinner= (Spinner) findViewById(R.id.citySpinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String city=spinner.getSelectedItem().toString();
                cityName=city;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getData() {
        Call<Weather> weatherCall = weatherApi.getWeatehrData();
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                if(weather.getQuery().getResults().getChannel().getItem()
                        .getCondition().getText().equals("Sunny")){
                    mainLayout.setBackgroundResource(R.drawable.sunny_image);
                }else if(weather.getQuery().getResults().getChannel().getItem()
                        .getCondition().getText().equals("Cloudy")){
                    mainLayout.setBackgroundResource(R.drawable.cloudy_image);
                }else if(weather.getQuery().getResults().getChannel().getItem()
                        .getCondition().getText().equals("Clear")){
                    mainLayout.setBackgroundResource(R.drawable.night_image);
                }else if(weather.getQuery().getResults().getChannel().getItem()
                        .getCondition().getText().equals("Partly Cloudy")){
                    mainLayout.setBackgroundResource(R.drawable.cloudy_image);
                }else if(weather.getQuery().getResults().getChannel().getItem()
                        .getCondition().getText().equals("Mostly Cloudy")){
                    mainLayout.setBackgroundResource(R.drawable.cloudy_image);
                }

                avgTextView.setText(weather.getQuery().getResults().getChannel().getItem()
                        .getCondition().getTemp()+ (char) 0x00B0+
                        ""+ weather.getQuery().getResults().getChannel().getUnits().getTemperature());
                maxTextView.setText(weather.getQuery().getResults().getChannel().getItem()
                        .getForecast().get(0).getHigh()+ (char) 0x00B0+
                        ""+ weather.getQuery().getResults().getChannel().getUnits().getTemperature());
                maxImageView.setBackgroundResource(R.drawable.arrow_up);
                minTextView.setText(weather.getQuery().getResults().getChannel().getItem()
                        .getForecast().get(0).getLow()+ (char) 0x00B0+
                        ""+ weather.getQuery().getResults().getChannel().getUnits().getTemperature());
                minImageView.setBackgroundResource(R.drawable.arrow_down);
                textTextView.setText(weather.getQuery().getResults().getChannel()
                        .getItem().getCondition().getText());
                textImageView.setBackgroundResource(R.drawable.clear_moon);

                /*Picasso.with(StartActivity.this)
                        .load(weather.getQuery().getResults().getChannel().getImage().getUrl())
                        .resize(250, 250)
                        .centerCrop()
                        .into(imageImageView);*/
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
    }

    public void laibraryInitial() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://query.yahooapis.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherApi = retrofit.create(WeatherApi.class);
    }

}
