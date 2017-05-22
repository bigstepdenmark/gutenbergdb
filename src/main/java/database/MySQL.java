package database;


import entity.Author;
import entity.Book;
import entity.City;
import interfaces.DatabaseInterface;
import interfaces.QueryInterface;
import io.FileCtrl;
import io.FilesCtrl;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQL implements DatabaseInterface, QueryInterface
{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

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

    public List<Book> booksByCity(String city)
    {
        return getBooksWithWhere( "c.name", city );
    }

    public List<Book> booksByTitle(String title)
    {
        return getBooksWithWhere( "b.title", title );
    }

    public List<Book> booksByAuthor(String author)
    {
        return getBooksWithWhere( "a.name", author );
    }

    public List<Book> booksByLocation(String location)
    {
        return null;
    }

    private void setResultSet(String query) throws SQLException
    {
        statement = connect().createStatement();
        resultSet = statement.executeQuery( query );
    }

    private String getQuery(String filename, String... params)
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

    public Book getBookById(String bookId)
    {
        Book book = new Book( Integer.parseInt( bookId ) );
        try
        {
            setResultSet( getQuery( "getBook", bookId ) );

            int counter = 0;

            while( resultSet.next() )
            {
                if( counter == 0 )
                {
                    String title = resultSet.getString( "title" );
                    int authorId = resultSet.getInt( "author_id" );
                    String authorName = resultSet.getString( "author_name" );

                    book.setTitle( title );
                    book.setAuthor( new Author( authorId, authorName ) );
                }

                int cityId = resultSet.getInt( "city_id" );
                String cityName = resultSet.getString( "city_name" );
                double latitude = resultSet.getDouble( "city_latitude" );
                double longitude = resultSet.getDouble( "city_latitude" );
                String countyCode = resultSet.getString( "city_country_code" );
                int population = resultSet.getInt( "city_population" );
                String timezone = resultSet.getString( "city_timezone" );
                Object position = resultSet.getObject( "city_position" );

                book.getCities()
                    .add( new City( cityId,
                                    cityName,
                                    latitude,
                                    longitude,
                                    countyCode,
                                    population,
                                    timezone,
                                    position.toString() ) );

                counter++;
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> getBooksWithWhere(String column, String value)
    {
        List<Book> books = new ArrayList<>();

        try
        {
            setResultSet( getQuery( "bookWhere", column, value ) );

            while( resultSet.next() )
            {
                Book book = new Book();

                int bookId = resultSet.getInt( "book_id" );
                String title = resultSet.getString( "title" );
                int authorId = resultSet.getInt( "author_id" );
                String authorName = resultSet.getString( "author_name" );

                book.setId( bookId );
                book.setTitle( title );
                book.setAuthor( new Author( authorId, authorName ) );

                int cityId = resultSet.getInt( "city_id" );
                String cityName = resultSet.getString( "city_name" );
                double latitude = resultSet.getDouble( "city_latitude" );
                double longitude = resultSet.getDouble( "city_latitude" );
                String countyCode = resultSet.getString( "city_country_code" );
                int population = resultSet.getInt( "city_population" );
                String timezone = resultSet.getString( "city_timezone" );
                Object position = resultSet.getObject( "city_position" );

                book.getCities()
                    .add( new City( cityId,
                                    cityName,
                                    latitude,
                                    longitude,
                                    countyCode,
                                    population,
                                    timezone,
                                    position.toString() ) );

                books.add( book );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooks() throws IOException
    {
        FileCtrl fileCtrl = new FileCtrl( "output/titles_21-05-2017_20-39-52.csv" );
        List<String> lines = fileCtrl.asLines();
        List<Book> books = new ArrayList<>();

        for( String line : lines )
        {
            books.add( getBookById( line.split( ";" )[ 0 ] ) );
        }

        return books;
    }
}