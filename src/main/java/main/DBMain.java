package main;


import database.MySQL;
import interfaces.DatabaseInterface;

/**
 * Created by ismailcam on 08/05/2017.
 */
public class DBMain
{
    private final DatabaseInterface database;

    public DBMain(DatabaseInterface database)
    {
        this.database = database;
    }

    // test 3 med tag
    public static void main(String[] args)
    {
        DBMain m = new DBMain( new MySQL() );
        System.out.println( "dsds" );
        //  m.database.connect();
    }
}

