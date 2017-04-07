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
    private ImageView workout;
    private boolean FIRST_TIME_LAUNCHED = false;
    private int seconds = 0;
    int count = 0, temp = 0;
    boolean section_10 = false, section_20 = true, music = true, status = false, jumping = false;
    int[] image = {R.drawable.pushup,R.drawable.squats,R.drawable.burpees,R.drawable.plank};
    //private static MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_layout);
        workout = (ImageView) findViewById(R.id.workout);
        //mp =  MediaPlayer.create(getApplicationContext(),R.raw.countdown);
        if(savedInstanceState != null){
            this.seconds = savedInstanceState.getInt("seconds");
            this.temp = savedInstanceState.getInt("temp");
            this.count = savedInstanceState.getInt("count");
            this.status = savedInstanceState.getBoolean("status");
            this.section_10 = savedInstanceState.getBoolean("section_10");
            this.section_20 = savedInstanceState.getBoolean("section_20");
            this.music = savedInstanceState.getBoolean("music");
            this.FIRST_TIME_LAUNCHED = savedInstanceState.getBoolean("firstTimeLaunched");
            this.jumping = savedInstanceState.getBoolean("jumping");
            /*
            *   since oncreate method has been called newly so the image
            *   is changing to the previous stage
            * */
            if(jumping == true){
                workout.setImageResource(R.drawable.jumping);
            }else{
                workout.setImageResource(image[count]);
            }
        }
        // this method shows the clock in the textview
        startTimer();
    }

    public void start(View view){
        status = true;
        if(FIRST_TIME_LAUNCHED == false){
            ImageView workout = (ImageView) findViewById(R.id.workout);
            workout.setImageResource(R.drawable.pushup);
            FIRST_TIME_LAUNCHED = true;
        }

        /*if(mp.isPlaying()){
            mp.seekTo(position);
        }*/
    }

    public void pause(View view){
        status = false;
        //position = mp.getCurrentPosition();
        //mp.pause();
    }

    public void stop(View view){
        seconds = 0;
        status = false;
        //mp.stop();
    }

    // start timer method to show the clock
    public void startTimer(){
        final Handler handler = new Handler();
        final TextView clock = (TextView) findViewById(R.id.clock);
        handler.post(new Runnable() {
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d:%02d",hours,minutes,secs);
                clock.setText(time);
                //section 20 logic
                if(Math.abs(secs-temp) > 16 && section_10 == false && music == true){
                    music = false;
                }
                if(Math.abs(secs-temp) == 20 && section_10 == false){
                    temp = secs;
                    music = true;
                    section_10 = true;
                    section_20 = false;
                    workout.setImageResource(R.drawable.jumping);
                    jumping = true;
                }
                // section 10 logic
                if(Math.abs(secs-temp) > 6 && section_20 == false && music == true){
                    music = false;
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
                    jumping = false;
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
        outState.putBoolean("firstTimeLaunched",FIRST_TIME_LAUNCHED);
        outState.putBoolean("jumping",jumping);
    }
}
