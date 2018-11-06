package hu.adamtorok.beermeter.activites;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import hu.adamtorok.beermeter.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
