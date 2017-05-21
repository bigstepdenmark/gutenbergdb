package database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.Book;
import entity.City;
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

    public static void main(String[] args)
    {
        MongoDB m = new MongoDB();
        m.connect();
        //m.importFromMysqlToMongoDB();
    }

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

    public void importFromMysqlToMongoDB()
    {
        MySQL mySQL = new MySQL();

        List<Book> books = mySQL.getBooks();
        List<Document> documents = new ArrayList<>();

        for( Book book : books )
        {
            Document doc = new Document( "book_id", book.getId() );
            doc.append( "title", book.getTitle() );
            doc.append( "author", book.getAuthor().getName() );

            List<Document> citiesDoc = new ArrayList<>();

            for( City city : book.getCities() )
            {
                Document cityDoc = new Document();
                cityDoc.append( "id", city.getId() );
                cityDoc.append( "name", city.getName() );
                cityDoc.append( "latitude", city.getLatitude() );
                cityDoc.append( "longitude", city.getLongitude() );
                cityDoc.append( "country_code", city.getCountryCode() );
                cityDoc.append( "population", city.getPopulation() );
                cityDoc.append( "timezone", city.getTimezone() );

                citiesDoc.add( cityDoc );
            }

            doc.append( "cities", citiesDoc );

            documents.add( doc );
        }

        getCollection().insertMany( documents );
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

}

