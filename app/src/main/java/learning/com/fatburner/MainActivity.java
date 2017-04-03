package learning.com.fatburner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // on click start workout button
    public void startWorkout(View view){
        Intent intent = new Intent(getApplicationContext(),Workout.class);
        startActivity(intent);
    }
}
