package pt.ua.cm.hw2.ui.WeatherList;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ua.cm.hw2.R;
import pt.ua.cm.hw2.datamodel.Forecast;
import pt.ua.cm.hw2.datamodel.Weather;
import pt.ua.cm.hw2.ui.CityAdapter;
import pt.ua.cm.hw2.ui.CityList.CityFragment;
import pt.ua.cm.hw2.ui.ForecastAdapter;
import pt.ua.cm.hw2.ui.MainActivity;

public class WeatherFragment extends Fragment {

    private Forecast forecast;

    public static WeatherFragment newInstance(Forecast m_forecast)
    {
        WeatherFragment myFragment = new WeatherFragment();
        myFragment.setForecast(m_forecast);
        return myFragment;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.weather_fragment, container, false);

        TextView nameCity = root.findViewById(R.id.nameC);
        ((MainActivity)getActivity()).callWeatherForecast("Porto");
        //((MainActivity)getActivity()).callWeatherForecastForACityStep1("Porto");
        //nameCity.append(days.toString());
        nameCity.append(forecast.getName());
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            fab.hide();
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).startCitiesFragment();
                }
            });
        }

        ImageView imageCity = view.findViewById(R.id.imageCity);
        imageCity.setImageDrawable(getContext().getResources().getDrawable(getContext().getResources().getIdentifier(forecast.getImage(), "drawable", getContext().getPackageName())));

        // Lookup the recyclerview in activity layout
        RecyclerView rvDays = (RecyclerView) view.findViewById(R.id.daysList);


        // Create adapter passing in the sample user data
        ForecastAdapter adapter = new ForecastAdapter(forecast.getDays());
        // Attach the adapter to the recyclerview to populate items
        rvDays.setAdapter(adapter);
        // Set layout manager to position the items
        rvDays.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDays.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

}