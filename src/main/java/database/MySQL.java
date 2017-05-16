package database;


import entity.Author;
import entity.Book;
import interfaces.DatabaseInterface;
import interfaces.QueryInterface;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class MySQL implements DatabaseInterface, QueryInterface
{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Connection connect()
    {
        String url = "jdbc:mysql://localhost:3306/gutenberg";
        String username = "root";
        String password = "root";

        try
        {
            if( connection == null )
                connection = DriverManager.getConnection( url, username, password );
            System.out.println( "Success!" );
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

    public ArrayList<Book> booksByCity(String city)
    {
        return null;
    }

    public ArrayList<Book> booksByTitle(String title)
    {
        ArrayList<Book> bookList = new ArrayList<Book>();

        try
        {
            setResultSet( getQuery( "booksByTitle", title ) );

            while( resultSet.next() )
            {
                int id = resultSet.getInt( "id" );
                Author author = new Author( resultSet.getString( "author" ) );
                bookList.add( new Book( id, title, author ) );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            close();
        }

        return bookList;
    }

    public ArrayList<Book> booksByAuthor(String author)
    {
        ArrayList<Book> bookList = new ArrayList<Book>();

        try
        {
            setResultSet( getQuery( "booksByAuthor", author ) );

            while( resultSet.next() )
            {
                int id = resultSet.getInt( "id" );
                String title = resultSet.getString( "title" );
                bookList.add( new Book( id, title, new Author( author ) ) );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            close();
        }

        return bookList;
    }

    public ArrayList<Book> booksByLocation(String location)
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
            String path = new File( "src/main/resources/sql/" + filename + ".sql" ).toURI().getPath();

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

    public static void main(String[] args)
    {
        MySQL m = new MySQL();

        for( Book book : m.booksByTitle( "The Forest Schoolmaster" ) )
        {
            System.out.println( book );
        }
    }
}