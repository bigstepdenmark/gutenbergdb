package main;


import entity.City;
import io.CityController;
import io.ReadController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ismailcam on 15/05/2017.
 */
public class IOMain
{
    public static void main(String[] args) throws IOException
    {
        ReadController r = new ReadController();
        List<City> cities = r.getCitiesFromCSV();
        String path = new File( "src/main/files/books/" ).toURI().getPath();
        File[] files = new File( path ).listFiles();

        // ORG
        // ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        // After memory error
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool( processors );

        for( File file : files )
        {
            int bookId = Integer.parseInt( file.getName().replace( ".txt", "" ) );

            service.execute( new CityController( bookId, r, cities ) );
        }

        service.shutdown();
    }
}

