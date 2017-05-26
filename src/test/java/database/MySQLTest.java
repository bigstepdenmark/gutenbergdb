package database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by ismailcam on 26/05/2017.
 */
@RunWith( MockitoJUnitRunner.class )
public class MySQLTest
{
    @Mock
    MySQL mySQL;

    @Before
    public void setUp() throws Exception
    {
        //mySQL = new MySQL();
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