package database;

import org.dbunit.*;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by ismailcam on 27/05/2017.
 */
//@RunWith( MockitoJUnitRunner.class ) // Enable Mockito Annotations
public class MySQLTest //extends DBTestCase
{

//    IDatabaseConnection connection;
//    IDataSet dataSet;

    //@Mock
    MySQL mySQL;

/*    public MySQLTest(String name)
    {
        super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                            "jdbc:mysql://127.0.0.1:3306/gutenberg" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "gutenberg" );
    }

    @Override
    protected IDataSet getDataSet() throws Exception
    {
        IDataSet[] datasets = new IDataSet[]{
                new FlatXmlDataSetBuilder().build( new File( new File(
                        "src/main/files/databaseExports/partial/books.xml" ).toURI().getPath() ) ),
                new FlatXmlDataSetBuilder().build( new File( new File(
                        "src/main/files/databaseExports/partial/authors.xml" ).toURI().getPath() ) ),
                new FlatXmlDataSetBuilder().build( new File( new File(
                        "src/main/files/databaseExports/partial/author_books.xml" ).toURI().getPath() ) ),
                new FlatXmlDataSetBuilder().build( new File( new File(
                        "src/main/files/databaseExports/partial/cities.xml" ).toURI().getPath() ) ),
                new FlatXmlDataSetBuilder().build( new File( new File(
                        "src/main/files/databaseExports/partial/book_cities.xml" ).toURI().getPath() ) )
        };

        return new CompositeDataSet( datasets );

    }*/

    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }

    @Before
    public void setUp() throws Exception
    {
        mySQL = new MySQL(DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/gutenberg", "root", "" ));
//        connection = this.getConnection();
//        dataSet = getDataSet();
        //super.setUp();
    }

    @After
    public void tearDown() throws Exception
    {
       // super.tearDown();
    }

    @Test
    public void testTableCount() throws Exception
    {
      //  String query = "SELECT * FROM books where id = 10";

        //System.out.println( connection.createDataSet().getTableNames().length );

        //assertThat( connection, is( "" ) );

        //assertThat( title, is( "The King James Bible" ) );

        assertThat( true, is( true ) );
    }

    /*    @Test
    public void testMe() throws Exception
    {
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable( "books" );

        ITable expectedTable = databaseTester.getDataSet().getTable( "books" );

        Assertion.assertEquals( expectedTable, actualTable );

    }*/


}