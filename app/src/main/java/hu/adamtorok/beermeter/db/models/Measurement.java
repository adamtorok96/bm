package hu.adamtorok.beermeter.db.models;

import android.support.annotation.Nullable;

import com.orm.SugarRecord;

public class Measurement extends SugarRecord<Measurement> {

    double time;

    @Nullable
    String name;

    public Measurement() {

    }

    public Measurement(double time) {
        this.time = time;
        this.name = null;
    }

    public Measurement(double time, String name) {
        this.time = time;
        this.name = name;
    }
}
