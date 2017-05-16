package entity;


/**
 * Created by ismailcam on 15/05/2017.
 */
public class City
{
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String countryCode;
    private int population;
    private String timezone;
    private String position; // skal rettes til Point object.

    public City(int id, String name, double latitude, double longitude, String countryCode, int population, String timezone)
    {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryCode = countryCode;
        this.population = population;
        this.timezone = timezone;
    }

    public City(int id, String name, double latitude, double longitude, String countryCode, int population, String timezone, String position)
    {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryCode = countryCode;
        this.population = population;
        this.timezone = timezone;
        this.position = position;
    }

    public City(String[] citydata)
    {
        this.id = Integer.parseInt( citydata[ 0 ] );
        this.name = citydata[ 1 ];
        this.latitude = Double.parseDouble( citydata[ 2 ] );
        this.longitude = Double.parseDouble( citydata[ 3 ] );
        this.countryCode = citydata[ 4 ];
        this.population = Integer.parseInt( citydata[ 5 ] );
        this.timezone = citydata[ 6 ];
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public int getPopulation()
    {
        return population;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public String getPosition()
    {
        return position;
    }

    @Override
    public String toString()
    {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryCode='" + countryCode + '\'' +
                ", population=" + population +
                ", timezone='" + timezone + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
