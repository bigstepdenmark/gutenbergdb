package database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import entity.Author;
import entity.Book;
import entity.City;
import interfaces.DatabaseInterface;
import interfaces.QueryInterface;
import org.bson.Document;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

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

    public List<Book> booksByCity(String city)
    {
        return null;
    }

    public List<Book> booksByTitle(String title)
    {
        return getBookWhere( "title", title );
    }

    public List<Book> booksByAuthor(String author)
    {
        return getBookWhere( "author", author );
    }

    public List<Book> booksByLocation(String location)
    {
        return null;
    }

    public void importFromMysqlToMongoDB() throws IOException
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

    public List<Book> getBookWhere(String field, String value)
    {
        MongoCursor<Document> cursor = getCollection().find( eq( field, value ) ).iterator();
        List<Book> books = new ArrayList<>();

        while( cursor.hasNext() )
        {
            Document bookDoc = cursor.next();
            Book book = new Book( bookDoc.getInteger( "book_id" ), bookDoc.getString( "title" ) );
            book.setAuthor( new Author( bookDoc.getString( "author" ) ) );

            List<Document> cities = (List<Document>) bookDoc.get( "cities" );

            for( Document city : cities )
            {
                book.getCities()
                    .add( new City( city.getInteger( "id" ),
                                    city.getString( "name" ),
                                    city.getDouble( "latitude" ),
                                    city.getDouble( "longitude" ),
                                    city.getString( "country_code" ),
                                    city.getInteger( "population" ),
                                    city.getString( "timezone" ) ) );
            }

            books.add( book );
        }

        cursor.close();

        return books;
    }

    public void getBooksContainsCity(String cityname)
    {
        // db.getCollection('gutenberg').find({cities : {$elemMatch : {name : 'Ankara'} } } ).count();
        MongoCursor<Document> cursor = getCollection().find( elemMatch( "cities", new Document( "name", cityname ) ) )
                                                      .iterator();

        while( cursor.hasNext() )
        {
            System.out.println( cursor.next().getString( "title" ) );
        }
    }
}

