package pt.ua.cm.hw2.datamodel;

import java.util.List;

public class Forecast {

    private String name;

    private String image;

    private List<Weather> days;

    public Forecast(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<Weather> getDays() {
        return days;
    }

    public void setDays(List<Weather> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", days=" + days +
                '}';
    }
}

