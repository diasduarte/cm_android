package pt.ua.cm.hw2.ui.CityList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ua.cm.hw2.R;
import pt.ua.cm.hw2.datamodel.Forecast;
import pt.ua.cm.hw2.ui.CityAdapter;

public class CityFragment extends Fragment {

    private static final String TAG = "Log";
    List<Forecast> cities;

    public CityFragment(List<Forecast> cities) {
        this.cities = cities;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.city_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lookup the recyclerview in activity layout
        RecyclerView rvCities = (RecyclerView) view.findViewById(R.id.rvCities);

        // Create adapter passing in the sample user data
        CityAdapter adapter = new CityAdapter(cities);
        // Attach the adapter to the recyclerview to populate items
        rvCities.setAdapter(adapter);
        // Set layout manager to position the items
        rvCities.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCities.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // That's all!
    }
}