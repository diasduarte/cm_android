package pt.ua.cm.hw2.network;

import java.util.HashMap;

import pt.ua.cm.hw2.datamodel.WeatherType;

public interface WeatherTypesResultsObserver {
    public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection);
    public void onFailure(Throwable cause);
}
