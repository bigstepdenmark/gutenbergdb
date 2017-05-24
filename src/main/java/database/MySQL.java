package database;


import entity.Author;
import entity.Book;
import entity.City;
import interfaces.DatabaseInterface;
import interfaces.QueryInterface;
import io.FileCtrl;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQL implements DatabaseInterface, QueryInterface
{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * Make the connection to MySQL
     * @return
     */
    public Connection connect()
    {
        String url = "jdbc:mysql://127.0.0.1:3306/gutenberg";
        String username = "root";
        String password = "root";

        try
        {
            if( connection == null )
                connection = DriverManager.getConnection( url, username, password );
            //System.out.println( "Success!" );
        }
        catch( SQLException e )
        {
            System.out.println( "Connection error!" );
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Close all, resultset, statement and connection.
     */
    public void close()
    {
        try
        {
            if( resultSet != null )
                resultSet.close();

            if( statement != null )
                statement.close();

            if( connection != null )
                connection.close();
        }
        catch( SQLException e )
        {
            System.out.println( "Closing error!" );
            e.printStackTrace();
        }
    }

    /**
     * Get books that mentioned the given city name.
     * @param city
     * @return
     */
    public List<Book> booksByCity(String city)
    {
        List<Book> books = new ArrayList<>();

        try
        {
            addBookToBooks( getSQL( "booksByCity", city ), books );
        }
        catch( SQLException e )
        {
            System.err.println( e.getMessage() );
        }

        return books;
    }

    /**
     * Get books with cities by given book title.
     * @param title
     * @return
     */
    public List<Book> booksByTitle(String title)
    {
        List<Book> books = new ArrayList<>();

        try
        {
            addBookToBooks( getSQL( "booksByTitle", title ), books );
            addCitiesToBooks( books );
        }
        catch( SQLException e )
        {
            System.err.println( e.getMessage() );
        }

        return books;
    }

    /**
     * Get books with cities by given Author name.
     * @param author
     * @return
     */
    public List<Book> booksByAuthor(String author)
    {
        List<Book> books = new ArrayList<>();

        try
        {
            addBookToBooks( getSQL( "booksByAuthor", author ), books );
            addCitiesToBooks( books );
        }
        catch( SQLException e )
        {
            System.err.println( e.getMessage() );
        }

        return books;
    }

    /**
     * Get books near the given latitude, longitude and radius.
     * @param latitude
     * @param longitude
     * @param radiusInKm
     * @return
     */
    public List<Book> booksByLocation(double latitude, double longitude, int radiusInKm)
    {
        List<Book> books = new ArrayList<>();

        try
        {
            addBookToBooks( getSQL( "booksByLocation",
                                    Double.toString( latitude ),
                                    Double.toString( longitude ),
                                    Integer.toString( radiusInKm ) ), books );
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }

        return books;
    }

    // Used in MongoDb to import objects to MongoDB
    public Book getBookById(int bookId)
    {
        Book book = new Book( bookId );

        try
        {
            setResultSet( getSQL( "getBookAndAuthor", Integer.toString( bookId ) ) );

            while( resultSet.next() )
            {
                book.setTitle( resultSet.getString( "title" ) );
                book.setAuthor( new Author( resultSet.getInt( "author_id" ), resultSet.getString( "author_name" ) ) );
            }

            setResultSet( getSQL( "getCitiesByBookId", Integer.toString( bookId ) ) );

            while( resultSet.next() )
            {
                City city = new City( resultSet.getInt( "city_id" ),
                                      resultSet.getString( "city_name" ),
                                      resultSet.getDouble( "city_latitude" ),
                                      resultSet.getDouble( "city_longitude" ),
                                      resultSet.getString( "city_country_code" ),
                                      resultSet.getInt( "city_population" ),
                                      resultSet.getString( "city_timezone" ),
                                      null );

                book.getCities().add( city );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }

        return book;
    }

    // Used in MongoDb to import objects to MongoDB
    public List<Book> getBooks() throws IOException
    {
        FileCtrl fileCtrl = new FileCtrl( "output/titles_21-05-2017_20-39-52.csv" );
        List<String> lines = fileCtrl.asLines();
        List<Book> books = new ArrayList<>();

        for( String line : lines )
        {
            int bookId = Integer.parseInt( line.split( ";" )[ 0 ] );
            books.add( getBookById( bookId ) );
        }

        return books;
    }

    /**
     * Get mentioned in cities in books, and add them to Book object.
     * @param books
     * @throws SQLException
     */
    public void addCitiesToBooks(List<Book> books) throws SQLException
    {
        for( Book book : books )
        {
            setResultSet( getSQL( "getCitiesByBookId", Integer.toString( book.getId() ) ) );

            while( resultSet.next() )
            {
                City city = new City( resultSet.getInt( "city_id" ),
                                      resultSet.getString( "city_name" ),
                                      resultSet.getDouble( "city_latitude" ),
                                      resultSet.getDouble( "city_longitude" ),
                                      resultSet.getString( "city_country_code" ),
                                      resultSet.getInt( "city_population" ),
                                      resultSet.getString( "city_timezone" ),
                                      null );

                book.getCities().add( city );
            }
        }
    }

    /**
     * Get Book to books list
     * @param query
     * @param books
     * @throws SQLException
     */
    private void addBookToBooks(String query, List<Book> books) throws SQLException
    {
        setResultSet( query );

        while( resultSet.next() )
        {
            Book book = new Book( resultSet.getInt( "book_id" ), resultSet.getString( "title" ) );
            book.setAuthor( new Author( resultSet.getInt( "author_id" ), resultSet.getString( "author_name" ) ) );
            books.add( book );
        }
    }

    /**
     * Create statement and set resultset.
     * @param query
     * @throws SQLException
     */
    private void setResultSet(String query) throws SQLException
    {
        statement = connect().createStatement();
        resultSet = statement.executeQuery( query );
    }

    /**
     * Get SQL file as String from src/main/files/sql/*
     * @param filename
     * @param params
     * @return
     */
    private String getSQL(String filename, String... params)
    {
        try
        {
            String path = new File( "src/main/files/sql/" + filename + ".sql" ).toURI().getPath();

            BufferedReader reader = new BufferedReader( new FileReader( path ) );

            String line;
            StringBuilder query = new StringBuilder();

            while( ( line = reader.readLine() ) != null )
            {
                query.append( line ).append( " " );
            }

            reader.close();

            if( params.length > 0 )
                return String.format( query.toString(), params );

            return query.toString();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        return null;
    }
}