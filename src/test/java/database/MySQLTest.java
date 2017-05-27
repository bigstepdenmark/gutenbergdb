package database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by ismailcam on 27/05/2017.
 */
public class MySQLTest
{
    MySQL mySQL;

    @Before
    public void setUp() throws Exception
    {
        mySQL = new MySQL();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void connect() throws Exception
    {
    }

    @Test
    public void close() throws Exception
    {
    }

    @Test
    public void booksByCity() throws Exception
    {
    }

    @Test
    public void booksByTitle() throws Exception
    {
    }

    @Test
    public void booksByAuthor() throws Exception
    {
    }

    @Test
    public void booksByLocation() throws Exception
    {
    }

    @Test
    public void getBookById() throws Exception
    {
        String title = mySQL.getBookById( 10 ).getTitle();

        assertThat( title, is( "The King James Bible" ) );
    }

    @Test
    public void getBooks() throws Exception
    {
    }

    @Test
    public void addCitiesToBooks() throws Exception
    {
    }

}