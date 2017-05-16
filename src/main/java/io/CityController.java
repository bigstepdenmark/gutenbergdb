package io;


import entity.Book;
import entity.City;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ismailcam on 15/05/2017.
 */
public class CityController implements Runnable
{
    private int bookId;
    private ReadController rc;
    List<City> cities;


    public CityController(int bookId, ReadController rc, List<City> cities)
    {
        this.bookId = bookId;
        this.rc = rc;
        this.cities = cities;
    }

    public void run()
    {
        try
        {
            getCitiesFromBook();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void getCitiesFromBook() throws IOException
    {
        String book = rc.getBookAsString( bookId );

        HashMap<Integer, City> citiesInBook = new HashMap<>();

        for( City city : cities )
        {
            if( book.contains( city.getName() ) )
                citiesInBook.put( city.getId(), city );
        }

        String path = new File( "src/main/files/csvfiles/bookcitypivot.csv" ).toURI().getPath();

        FileWriter writer = new FileWriter( path, true );

        for( City city : citiesInBook.values() )
        {
            String id = Integer.toString( bookId );
            String cityId = Integer.toString( city.getId() );
            String cityname = city.getName();
            writer.append( id ).append( ";" ).append( cityId ).append( ";" ).append( cityname ).append( "\n" );
        }

        writer.flush();
        writer.close();
    }
}

