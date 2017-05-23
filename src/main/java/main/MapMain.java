package main;

import database.MySQL;
import io.MapGenerater;

/**
 * Created by ismailcam on 23/05/2017.
 */
public class MapMain
{
    public static void main(String[] args)
    {
        MapGenerater mapGenerater = new MapGenerater();
        MySQL mySQL = new MySQL();
        mapGenerater.generate( mySQL.booksByTitle( "The United States Constitution" ), true );
    }
}
