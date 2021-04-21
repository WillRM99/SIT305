package com.example.workouttimerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private Chronometer timer;
    private  boolean running = false;
    private  long pauseOffSet;
    long time;
    private long baseTime;
    private long parseTime;
    long activityTime;
    long pauseTime;
    String activityType;

    String previousWorkoutText;
    private EditText inputText;
    private TextView trackerText;

    public static final  String SHAREDPREFS = "sharedPrefs";
    public static final  String WORKOUTTEXT = "workoutText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.workoutTypeEditText);
        trackerText = findViewById(R.id.previousWorkoutTextView);



        if (savedInstanceState == null) {
            timer = findViewById(R.id.TimeView);

            SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFS, MODE_PRIVATE);
            previousWorkoutText = sharedPreferences.getString(WORKOUTTEXT, "");
            if(!previousWorkoutText.equals(""))
            {
                trackerText.setText(previousWorkoutText);
            }

        }
        else if(savedInstanceState != null)
        {
            long localtime = savedInstanceState.getLong("time");
            running = savedInstanceState.getBoolean("isRunning");
            pauseOffSet = savedInstanceState.getLong("offSet");

            activityType = savedInstanceState.getString("workoutType");
            previousWorkoutText = savedInstanceState.getString("previousWorkoutText");

            inputText.setText(activityType);
            trackerText.setText(previousWorkoutText);





            timer = findViewById(R.id.TimeView);

            timer.setBase(SystemClock.elapsedRealtime() - localtime);

            Log.i(TAG, "mainClick: onCreate offsetime " + pauseOffSet);
            Log.i(TAG, "mainClick: onCreate basetime " + timer.getBase());
            Log.i(TAG, "mainClick: onCreate elapedtime " + SystemClock.elapsedRealtime());


            if(running)
            {
                timer.start();
            }
            else
            {
                timer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            }


        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        long currentTime = SystemClock.elapsedRealtime() - timer.getBase();
        
        // parseTime = SystemClock.elapsedRealtime() - timer.getBase();



        outState.putLong("time", currentTime);
        outState.putLong("offSet", pauseOffSet);
        outState.putBoolean("isRunning", running);
        outState.putString("previousWorkoutText", previousWorkoutText);
        outState.putString("workoutType", activityType);

        super.onSaveInstanceState(outState);

    }



    public void mainClick(View view) {
        switch(view.getId())
        {
            case R.id.startButton:
                //start timer
                if(!running)
                {
                    baseTime = SystemClock.elapsedRealtime() - pauseOffSet;
                    timer.setBase(baseTime);

                    timer.start();
                    running = true;
                }
                break;

            case R.id.pauseButton:
                //pause timer
                if(running)
                {
                    timer.stop();
                    pauseOffSet = SystemClock.elapsedRealtime() - timer.getBase();

                    pauseTime = SystemClock.elapsedRealtime();

                    running = false;
                }
                break;

            case R.id.stopButton:
                //pause timer and save result
                if(running)
                {
                    timer.stop();
                    running = false;
                    activityTime = SystemClock.elapsedRealtime() - timer.getBase();


                }
                else
                {
                    activityTime = (pauseTime - timer.getBase());

                    Log.i(TAG, "mainClick: offset tim " + pauseOffSet);
                    Log.i(TAG, "mainClick: elapsed time " + SystemClock.elapsedRealtime());
                    Log.i(TAG, "mainClick base time " + timer.getBase());


                }
                if(activityTime < 0)
                {
                    activityTime = 0;
                }
                int seconds = (int) ((activityTime / 1000)% 1000);
                int minutes = (int) ((activityTime / (1000*60)) % 60);



                activityType = inputText.getText().toString();

                if(activityType.equals(""))
                {
                    activityType = "Something";
                }
                previousWorkoutText = "You spent " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + " doing " + activityType + " last time";

                trackerText.setText(previousWorkoutText);

                SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(WORKOUTTEXT, previousWorkoutText);
                editor.apply();

                timer.setBase(SystemClock.elapsedRealtime());
                pauseOffSet = 0;

                //save the time spent and get the workout type to the app

                break;

            default:
                throw new IllegalStateException("Unexpected Value" + view.getId());


        }
    }
}