package pt.ua.cm.hw2.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.ua.cm.hw2.R;
import pt.ua.cm.hw2.datamodel.Forecast;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class CityAdapter extends
        RecyclerView.Adapter<CityAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageCity;
        public TextView nameCity;
        public FloatingActionButton enterCity;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageCity = (ImageView) itemView.findViewById(R.id.imageCity);
            nameCity = (TextView) itemView.findViewById(R.id.nameCity);
            enterCity = (FloatingActionButton) itemView.findViewById(R.id.enterCity);
        }
    }

    // Store a member variable for the contacts
    private List<Forecast> mCities;

    private Context context;

    // Pass in the contact array into the constructor
    public CityAdapter(List<Forecast> cities) {
        mCities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cityView = inflater.inflate(R.layout.item_fragment, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(cityView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Forecast city = mCities.get(position);

        // Set item views based on your views and data model
        ImageView iCity = holder.imageCity;
        iCity.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(city.getImage(), "drawable", context.getPackageName())));

        TextView nCity = holder.nameCity;
        nCity.setText(city.getName());

        FloatingActionButton eCity = holder.enterCity;
        eCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).callWeatherForecast(city.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}