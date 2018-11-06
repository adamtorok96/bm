package hu.adamtorok.beermeter.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import hu.adamtorok.beermeter.R;
import hu.adamtorok.beermeter.adapters.ScoreboardAdapter;

public class ScoreboardActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        mRecyclerView = findViewById(R.id.rvScoreboard);
        mRecyclerView.setAdapter(new ScoreboardAdapter());
    }
}
