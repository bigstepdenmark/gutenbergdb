package database;

import entity.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by ismailcam on 28/05/2017.
 */
public class MongoDBTest
{
    MongoDB mongoDB;

    @Before
    public void setUp() throws Exception
    {
        mongoDB = new MongoDB();
        mongoDB.connect().getDatabase( "soft-semester-1" ).getCollection( "gutenberg" );
       // mongoDB.importFromMysqlToMongoDB();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void connect() throws Exception
    {
        assertThat( mongoDB.connect().getAddress().getPort(), is( 27017 ) );
    }

    @Test( expected = Exception.class )
    public void close() throws Exception
    {
        mongoDB.close();
        mongoDB.connect().getAddress();
    }

    @Test
    public void booksByCity() throws Exception
    {
        List<Book> result = mongoDB.booksByCity( "Ankara" );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void booksByTitle() throws Exception
    {
        List<Book> result = mongoDB.booksByTitle( "The King James Bible" );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void booksByAuthor() throws Exception
    {
        List<Book> result = mongoDB.booksByAuthor( "Abraham Lincoln" );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void booksByLocation() throws Exception
    {
        List<Book> result = mongoDB.booksByLocation( 25.78953, 55.9432, 100 );
        assertThat( result.isEmpty(), is( false ) );
    }
}