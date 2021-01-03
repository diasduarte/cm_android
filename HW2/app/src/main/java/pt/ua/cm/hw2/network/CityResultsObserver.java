package pt.ua.cm.hw2.network;

import java.util.HashMap;

import pt.ua.cm.hw2.datamodel.City;

public interface  CityResultsObserver {
    public void receiveCitiesList(HashMap<String, City> citiesCollection);
    public void onFailure(Throwable cause);
}
