package hu.adamtorok.beermeter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hu.adamtorok.beermeter.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button
            btnMeasuring,
            btnScoreboard,
            btnSettings,
            btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMeasuring = findViewById(R.id.btnMeasuring);
        btnScoreboard = findViewById(R.id.btnScoreboard);
        btnSettings = findViewById(R.id.btnSettings);
        btnExit = findViewById(R.id.btnExit);

        btnMeasuring.setOnClickListener(this);
        btnScoreboard.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if( v.equals(btnMeasuring) ) {
            startActivity(new Intent(this, MeasuringActivity.class));
        }
        else if( v.equals(btnScoreboard) ) {
            startActivity(new Intent(this, ScoreboardActivity.class));
        }
        else if( v.equals(btnSettings) ) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        else if( v.equals(btnExit) ) {
            finish();
        }
    }
}
