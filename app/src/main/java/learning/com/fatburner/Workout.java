package learning.com.fatburner;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Workout extends Activity {
    private int seconds = 0;
    private boolean status = false;
    int count = 0, temp = 0;
    boolean section_10 = false, section_20 = true, music = true;
    int[] image = {R.drawable.pushup,R.drawable.squats,R.drawable.burpees,R.drawable.plank};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_layout);
        if(savedInstanceState != null){
            this.seconds = savedInstanceState.getInt("seconds");
            this.temp = savedInstanceState.getInt("temp");
            this.count = savedInstanceState.getInt("count");
            this.status = savedInstanceState.getBoolean("status");
            this.section_10 = savedInstanceState.getBoolean("section_10");
            this.section_20 = savedInstanceState.getBoolean("section_20");
            this.music = savedInstanceState.getBoolean("music");
        }
        startTimer();
    }

    public void start(View view){
        status = true;
    }

    public void pause(View view){
        status = false;
    }

    public void stop(View view){
        seconds = 0;
        status = false;
    }

    // start timer method to show the clock
    public void startTimer(){
        final Handler handler = new Handler();
        final TextView clock = (TextView) findViewById(R.id.clock);
        final ImageView workout = (ImageView) findViewById(R.id.workout);
        workout.setImageResource(image[count]);
        handler.post(new Runnable() {
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d:%02d",hours,minutes,secs);
                clock.setText(time);
                // setting the image
                //section 20 logic
                if(Math.abs(secs-temp) > 16 && section_10 == false && music == true){
                    music = false;
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.countdown);
                    mp.start();
                }
                if(Math.abs(secs-temp) == 20 && section_10 == false){
                    temp = secs;
                    music = true;
                    section_10 = true;
                    section_20 = false;
                    workout.setImageResource(R.drawable.jumping);
                }
                // section 10 logic
                if(Math.abs(secs-temp) > 6 && section_20 == false && music == true){
                    music = false;
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.countdown);
                    mp.start();
                }
                if((Math.abs(secs-temp) == 10 || secs == 0) && section_20 == false){
                    temp = secs;        // comparing secs == 0 coz after 59 secs become 0
                    music = true;
                    section_10 = false;
                    section_20 = true;
                    // for cycling the images
                    if(count == 3){
                        count = 0;
                    }else{
                        count++;
                    }
                    workout.setImageResource(image[count]);
                }
                if(status){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("seconds",seconds);
        outState.putInt("count",count);
        outState.putInt("temp",temp);
        outState.putBoolean("status",status);
        outState.putBoolean("section_10",section_10);
        outState.putBoolean("section_20",section_20);
        outState.putBoolean("music",music);
    }
}
