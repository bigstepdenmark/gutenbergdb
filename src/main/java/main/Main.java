package main;

import database.MongoDB;
import database.MySQL;
import entity.Book;
import io.MapGenerater;

import java.util.List;

/**
 * Created by ismailcam on 24/05/2017.
 */
public class Main
{


    public static void main(String[] args)
    {
        Main main = new Main();
        MySQL mySQL = new MySQL();
        MongoDB mongoDB = new MongoDB();
        MapGenerater mapGenerater = new MapGenerater();

        /*
        |--------------------------------------------------------------------
        | Import data to your databases
        |--------------------------------------------------------------------
        |
        | MySQL: db=gutenberg, port=3306, username=root, password=root
        | Import data: execute SQL file /src/main/files/sql/factory.sql
        |
        | MongoDB: db=soft-semester-1, collection=gutenberg, port=27017
        | Import data: run method mongoDB.importFromMysqlToMongoDB();
        | both database engine must run when you import to mondoDB, because it use MySQL to get data.
        */

        // 1. Given a city name your application returns all book titles with corresponding authors that mention this city.
        // -------------------------------------------------------------------------------------------------------------------
        main.printResult( mySQL.booksByCity( "Ankara" ) ); // --> uncomment to run
        // main.printResult( mongoDB.booksByCity( "Ankara" ) ); // --> uncomment to run


        // 2. Given a book title, your application plots all cities mentioned in this book onto a map.
        // -------------------------------------------------------------------------------------------------------------------
        // mapGenerater.generate( mySQL.booksByTitle( "A Voyage to the Moon" ), true ); // --> uncomment to run
        // mapGenerater.generate( mongoDB.booksByTitle( "A Voyage to the Moon" ), true ); // --> uncomment to run


        // 3. Given an author name your application lists all books written by that author and plots all cities mentioned in any of the books onto a map.
        // -------------------------------------------------------------------------------------------------------------------
        // main.printAndShowMap( mySQL.booksByAuthor( "Lewis Carroll" ), mapGenerater ); // --> uncomment to run
        // main.printAndShowMap( mongoDB.booksByAuthor( "Lewis Carroll" ), mapGenerater ); // --> uncomment to run


        // 4. Given a geolocation, your application lists all books mentioning a city in vicinity of the given geolocation.
        // -------------------------------------------------------------------------------------------------------------------
        // main.printResult( mySQL.booksByLocation( 25.78953, 55.9432, 100 ) ); // --> uncomment to run
        // main.printResult( mongoDB.booksByLocation( 25.78953, 55.9432, 100 ) ); // --> uncomment to run
    }


    private void printResult(List<Book> books)
    {
        for( Book book : books )
        {
            System.out.println( "Title: " + book.getTitle() + ", Author: " + book.getAuthor().getName() );
        }
    }

    private void printAndShowMap(List<Book> books, MapGenerater mapGenerater)
    {
        for( Book book : books )
        {
            System.out.println( "Title: " + book.getTitle() + ", Author: " + book.getAuthor().getName() );
        }

        mapGenerater.generate( books, true );
    }

}
