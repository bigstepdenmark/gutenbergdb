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
import org.bson.conversions.Bson;

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

    /**
     * Connect to MongoDB.
     *
     * @return
     */
    public MongoClient connect()
    {
        if( client == null )
            client = new MongoClient( "127.0.0.1", 27017 );

        return client;
    }

    /**
     * Close connedtion
     */
    public void close()
    {
        client.close();
    }

    /**
     * Get books that mentioned the given city name.
     *
     * @param city
     * @return
     */
    public List<Book> booksByCity(String city)
    {
        return getBooks( getCursor( elemMatch( "cities", new Document( "name", city ) ) ) );
    }

    /**
     * Get books with cities by given book title.
     *
     * @param title
     * @return
     */
    public List<Book> booksByTitle(String title)
    {
        return getBooks( getCursor( eq( "title", title ) ) );
    }

    /**
     * Get books with cities by given Author name.
     *
     * @param author
     * @return
     */
    public List<Book> booksByAuthor(String author)
    {
        return getBooks( getCursor( eq( "author", author ) ) );
    }

    /**
     * Get books near the given latitude, longitude and radius.
     *
     * @param latitude
     * @param longitude
     * @param radiusInKm
     * @return
     */
    public List<Book> booksByLocation(double latitude, double longitude, int radiusInKm)
    {
        Document document = new Document();
        document.append( "cities.loc",
                         new Document( "$near",
                                       new Document( "$geometry",
                                                     new Document( "type", "Point" ).append( "coordinates",
                                                                                             Arrays.asList( longitude,
                                                                                                            latitude ) ) )
                                               .append( "$minDistance", 0.001 )
                                               .append( "$maxDistance", radiusInKm * 1000 ) )
        );

        return getBooks( getCursor( document ) );
    }

    /**
     * Only used to import book objects from Mysql to MongoDB.
     *
     * @throws IOException
     */
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
                cityDoc.append( "loc",
                                new Document().append( "type", "Point" )
                                              .append( "coordinates",
                                                       Arrays.asList( city.getLongitude(), city.getLatitude() ) ) );
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

    /**
     * Get database
     *
     * @return
     */
    private MongoDatabase getDatabase()
    {
        if( database == null )
            database = connect().getDatabase( "soft-semester-1" );

        return database;
    }

    /**
     * Get gutenberg collection from database.
     *
     * @return
     */
    private MongoCollection<Document> getCollection()
    {
        if( collection == null )
            collection = getDatabase().getCollection( "gutenberg" );

        return collection;
    }

    /**
     * Get books by given cursor.
     *
     * @param cursor
     * @return
     */
    private List<Book> getBooks(MongoCursor<Document> cursor)
    {
        List<Book> books = new ArrayList<>();

        while( cursor.hasNext() )
        {
            Document bookDoc = cursor.next();
            Book book = new Book( bookDoc.getInteger( "book_id" ), bookDoc.getString( "title" ) );
            book.setAuthor( new Author( bookDoc.getString( "author" ) ) );

            List<Document> cities = (List<Document>) bookDoc.get( "cities" );

            for( Document city : cities )
            {
                Document loc = (Document) city.get( "loc" );
                Object[] longLat = ( (List<Document>) loc.get( "coordinates" ) ).toArray();
                double lat = Double.parseDouble( longLat[ 1 ].toString() );
                double lon = Double.parseDouble( longLat[ 0 ].toString() );

                book.getCities()
                    .add( new City( city.getInteger( "id" ),
                                    city.getString( "name" ),
                                    lat,
                                    lon,
                                    city.getString( "country_code" ),
                                    city.getInteger( "population" ),
                                    city.getString( "timezone" ) ) );
            }

            books.add( book );
        }

        cursor.close();

        return books;
    }

    /**
     * Get MongoCursor by given given Bson filter.
     *
     * @param filter
     * @return
     */
    private MongoCursor<Document> getCursor(Bson filter)
    {
        return getCollection().find( filter ).iterator();
    }
}

