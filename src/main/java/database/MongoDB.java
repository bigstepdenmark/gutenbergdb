package database;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.Book;
import interfaces.DatabaseInterface;
import interfaces.QueryInterface;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismailcam on 08/05/2017.
 */
public class MongoDB implements DatabaseInterface, QueryInterface
{
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoClient connect()
    {
        if( client == null )
            client = new MongoClient( "localhost", 27017 );

        return client;
    }


    public void close()
    {
        client.close();
    }

    public ArrayList<Book> booksByCity(String city)
    {
        return null;
    }

    public ArrayList<Book> booksByTitle(String title)
    {
        return null;
    }

    public ArrayList<Book> booksByAuthor(String author)
    {
        return null;
    }

    public ArrayList<Book> booksByLocation(String location)
    {
        return null;
    }

    private MongoDatabase getDatabase()
    {
        if( database == null )
            database = connect().getDatabase( "soft-semester-1" );

        return database;
    }

    private MongoCollection<Document> getCollection()
    {
        if( collection == null )
            collection = getDatabase().getCollection( "gutenberg" );

        return collection;
    }

    public static void main(String[] args)
    {
        MongoDB m = new MongoDB();

        List<Document> finds = m.getCollection().find().into( new ArrayList<Document>() );

        for( Document d : finds )
        {
            System.out.println( d.toJson() );
        }
    }

}

