package pt.ua.cm.hw2.network;

import java.util.HashMap;
import java.util.List;

import pt.ua.cm.hw2.datamodel.Weather;
import pt.ua.cm.hw2.datamodel.WeatherType;

public interface ForecastForACityResultsObserver {
    public void receiveForecastList(List<Weather> forecast);
    public void onFailure(Throwable cause);
}
