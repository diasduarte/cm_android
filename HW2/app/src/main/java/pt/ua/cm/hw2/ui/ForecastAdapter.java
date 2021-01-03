package pt.ua.cm.hw2.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.ua.cm.hw2.R;
import pt.ua.cm.hw2.datamodel.Weather;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ForecastAdapter extends
        RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView date;
        public ImageView forecast;
        public TextView rain;
        public TextView max;
        public TextView min;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            forecast = (ImageView) itemView.findViewById(R.id.forecast);
            rain = (TextView) itemView.findViewById(R.id.rain);
            max = (TextView) itemView.findViewById(R.id.max);
            min = (TextView) itemView.findViewById(R.id.min);
        }
    }

    // Store a member variable for the contacts
    private List<Weather> mWeather;

    private Context context;

    // Pass in the contact array into the constructor
    public ForecastAdapter(List<Weather> weathers) {
        mWeather = weathers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cityView = inflater.inflate(R.layout.forecast_fragment, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(cityView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Weather city = mWeather.get(position);

        // Set item views based on your views and data model
        TextView dWeather = holder.date;
        ImageView fWeather = holder.forecast;
        TextView rWeather = holder.rain;
        TextView xWeather = holder.max;
        TextView nWeather = holder.min;

        dWeather.append(city.getForecastDate());
        rWeather.append(Math.round(city.getPrecipitaProb()) + "%");
        if(city.getPrecipitaProb() < 40.0) {
            fWeather.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier("ic_sun", "drawable", context.getPackageName())));
        } else {
            fWeather.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier("ic_raining", "drawable", context.getPackageName())));
        }
        xWeather.append(Long.toString(Math.round(city.getTMax())));
        nWeather.append(Long.toString(Math.round(city.getTMin())));
    }

    @Override
    public int getItemCount() {
        return mWeather.size();
    }
}