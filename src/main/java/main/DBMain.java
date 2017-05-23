package main;


import database.MongoDB;
import database.MySQL;
import entity.Book;
import entity.City;
import interfaces.DatabaseInterface;
import interfaces.QueryInterface;

import java.io.IOException;
import java.util.List;

/**
 * Created by ismailcam on 08/05/2017.
 */
public class DBMain
{
    public static void main(String[] args) throws IOException
    {
        //----------------------------------------------------
        // MySQL
        //----------------------------------------------------
        MySQL mySQL = new MySQL();

        //System.out.println( mySQL.booksByLocation( 41.01384, 28.94966, 100 ).size() );
        System.out.println( mySQL.booksByTitle( "The King James Bible" ).size() );
        //System.out.println( mySQL.getBookById2( 10 ).getCities().size() );

        // Print Title and Author name by given a city name
//        for( Book book : mySQL.booksByCity( "Pen" ) )
//        {
//            System.out.println( "Title: " + book.getTitle() + ", Author: " + book.getAuthor().getName() );
//        }

        // Print Title and Author name by given a city name
//        for( Book book : mySQL.booksByTitle( "Cappy Ricks" ) )
//        {
//            System.out.println( "All cities by given this title: " + book.getTitle() + " / #" + book.getId() );
//            System.out.println( "----------------------------------------------------" );
//            for( City city : book.getCities() )
//            {
//                System.out.println( city );
//            }
//            System.out.println( "\n\n" );
//        }

        // Get all books by given author name
//        for( Book book : mySQL.booksByAuthor( "Voltaire" ) )
//        {
//            System.out.println( "Title: " + book.getTitle() );
//            System.out.println( "Author: " + book.getAuthor().getName() );
//            System.out.println( "Cities" );
//
//            for( City city : book.getCities() )
//            {
//                System.out.println(city);
//            }
//
//            System.out.println("\n\n");
//        }


        //----------------------------------------------------
        // MongoDB
        //----------------------------------------------------
        // Import from MySQL to MongoDB
        MongoDB mongoDb = new MongoDB();
//        try
//        {
//            mongoDb.importFromMysqlToMongoDB();
//        }
//        catch( IOException e )
//        {
//            e.printStackTrace();
//        }

        // Get books by title
//        List<Book> books = mongoDb.booksByAuthor( "Henry James" );
//
//        for( Book book : books )
//        {
//            System.out.println( book );
//        }

        //mongoDb.getBooksContainsCity("Ankara");
    }
}

