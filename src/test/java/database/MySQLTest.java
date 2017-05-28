package database;

import entity.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DriverManager;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;

/**
 * Created by ismailcam on 28/05/2017.
 */
public class MySQLTest
{
    MySQL mySQL;

    @Before
    public void setUp() throws Exception
    {
        mySQL = new MySQL( DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/gutenberg", "root", "" ) );
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void connect() throws Exception
    {
        assertThat( mySQL.connect().getCatalog(), is( "gutenberg" ) );
    }

    @Test
    public void close() throws Exception
    {
        mySQL.connect().close();
        assertThat( mySQL.connect().isClosed(), is( true ) );
    }

    @Test
    public void booksByCity() throws Exception
    {
        List<Book> result = mySQL.booksByCity( "Ankara" );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void booksByTitle() throws Exception
    {
        List<Book> result = mySQL.booksByTitle( "The King James Bible" );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void booksByAuthor() throws Exception
    {
        List<Book> result = mySQL.booksByAuthor( "Abraham Lincoln" );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void booksByLocation() throws Exception
    {
        List<Book> result = mySQL.booksByLocation( 25.78953, 55.9432, 100 );
        assertThat( result.isEmpty(), is( false ) );
    }

    @Test
    public void getBookById() throws Exception
    {
        assertThat( mySQL.getBookById( 10 ).getTitle(), is( "The King James Bible" ) );
    }

}