package hu.adamtorok.beermeter;

import android.os.Looper;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static double vectorLength(float[] vector) {
        return Math.sqrt(
                vector[0] * vector[0] +
                vector[1] * vector[1] +
                vector[2] * vector[2]
        );
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static long nanoTimeToSeconds(long time) {
        return TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
    }

    public static float nanoTimeToMilliSeconds(long time) {
        return time / 1000000000.0f; // TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
    }
}
