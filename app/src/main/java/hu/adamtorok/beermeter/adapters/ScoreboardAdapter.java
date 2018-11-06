package hu.adamtorok.beermeter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

import hu.adamtorok.beermeter.R;
import hu.adamtorok.beermeter.db.models.Measurement;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ViewHolder> {

    private List<Measurement> mMeasurements;

    public ScoreboardAdapter() {
        mMeasurements = Measurement
                .listAll(Measurement.class)
        ;

        mMeasurements.sort(new Comparator<Measurement>() {
            @Override
            public int compare(Measurement o1, Measurement o2) {
                if( o1.getTime() == o2.getTime() )
                    return 0;

                return o1.getTime() > o2.getTime() ? 1 : -1;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout ll = (LinearLayout)LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.measurement, viewGroup, false)
        ;

        return new ViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Measurement measurement = mMeasurements.get(i);

        viewHolder.tvScore.setText(String.valueOf(measurement.getTime()));

        if( measurement.getName() == null ) {
            viewHolder.tvName.setText(R.string.unnamed);
        } else {
            viewHolder.tvName.setText(measurement.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mMeasurements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvScore;
        public TextView tvName;

        public ViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);

            tvScore = itemView.findViewById(R.id.tvScore);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
