package pt.ua.cm.hw2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import pt.ua.cm.hw2.R;
import pt.ua.cm.hw2.datamodel.City;
import pt.ua.cm.hw2.datamodel.Forecast;
import pt.ua.cm.hw2.datamodel.Weather;
import pt.ua.cm.hw2.datamodel.WeatherType;
import pt.ua.cm.hw2.network.CityResultsObserver;
import pt.ua.cm.hw2.network.ForecastForACityResultsObserver;
import pt.ua.cm.hw2.network.IpmaWeatherClient;
import pt.ua.cm.hw2.network.WeatherTypesResultsObserver;
import pt.ua.cm.hw2.ui.CityList.CityFragment;
import pt.ua.cm.hw2.ui.WeatherList.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    private static StringBuilder feedback;
    public StringBuilder res = new StringBuilder();
    boolean alreadyExecuted = false;

    public ArrayList<Forecast> cForecast = new ArrayList<>();

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedback = new StringBuilder();

        cForecast.add(new Forecast("Braga", "braga"));
        cForecast.add(new Forecast("Porto", "porto"));
        cForecast.add(new Forecast("Aveiro", "aveiro"));
        cForecast.add(new Forecast("Coimbra", "coimbra"));
        cForecast.add(new Forecast("Lisboa", "lisboa"));
        cForecast.add(new Forecast("Setúbal", "setubal"));
        cForecast.add(new Forecast("Faro", "faro"));

        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag, new CityFragment(cForecast));
        ft.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void startWeatherFragment(String city, List<Weather> days) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Forecast fCast = cForecast.stream()
                .filter(forecast -> city.equals(forecast.getName()))
                .findAny()
                .orElse(null);
        fCast.setDays(days);
        ft.replace(R.id.frag, new WeatherFragment(fCast));
        ft.commit();
    }

    public void startCitiesFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frag, new CityFragment(cForecast));
        ft.commit();
        alreadyExecuted = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callWeatherForecast(String city) {
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                MainActivity.this.weatherDescriptions = descriptorsCollection;
                client.retrieveCitiesList(new CityResultsObserver() {

                    @Override
                    public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                        MainActivity.this.cities = citiesCollection;
                        City cityFound = cities.get(city);
                        if( null != cityFound) {
                            int locationId = cityFound.getGlobalIdLocal();
                            client.retrieveForecastForCity(locationId, new ForecastForACityResultsObserver() {
                                @Override
                                public void receiveForecastList(List<Weather> forecast) {
                                    if(!alreadyExecuted) {
                                        startWeatherFragment(city, forecast);
                                        Log.v("MAIN", "Start");
                                        alreadyExecuted = true;
                                    }
                                }
                                @Override
                                public void onFailure(Throwable cause) {
                                    res.append( "Failed to get forecast for 5 days");
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Throwable cause) {
                        res.append("Failed to get cities list!");
                    }
                });
            }
            @Override
            public void onFailure(Throwable cause) {
                res.append("Failed to get weather conditions!");
            }
        });
    }

    public void callWeatherForecastForACityStep1(String city) {

        feedback.append("\nGetting forecast for: " + city + "\n");

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                MainActivity.this.weatherDescriptions = descriptorsCollection;
                callWeatherForecastForACityStep2( city);
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback.append("Failed to get weather conditions!");
            }
        });
        //Log.v("MAIN", feedback.toString());
    }

    private void callWeatherForecastForACityStep2(String city) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    callWeatherForecastForACityStep3(locationId);
                } else {
                    feedback.append("unknown city: " + city);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                feedback.append("Failed to get cities list!");
            }
        });
    }

    private void callWeatherForecastForACityStep3(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    feedback.append(day.toString());
                    feedback.append("\t");
                }
                //Log.v("MAIN", feedback.toString());
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback.append( "Failed to get forecast for 5 days");
            }
        });

    }

}
