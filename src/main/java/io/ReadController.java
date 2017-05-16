package io;


import entity.Author;
import entity.Book;
import entity.City;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ismailcam on 12/05/2017.
 */
public class ReadController
{
    /**
     * Get the directory of books.
     *
     * @return
     */
    public String getDir()
    {
        return new File( "src/main/files/books/" ).toURI().getPath();
    }

    /**
     * Get book file from books/ directory.
     *
     * @param bookId
     * @return
     */
    public File getBookFile(int bookId)
    {
        return new File( getDir() + bookId + ".txt" );
    }

    public String getBookAsString(int bookId) throws IOException
    {
        BufferedReader reader = new BufferedReader( new FileReader( getBookFile( bookId ) ) );
        StringBuilder builder = new StringBuilder();
        String line;

        while( ( line = reader.readLine() ) != null )
        {
            if( !line.isEmpty() )
                builder.append( line.trim() );
        }

        return builder.toString();
    }

    /**
     * Get all files as a File array from given directory
     *
     * @return
     */
    public File[] getBookFiles()
    {
        return new File( getDir() ).listFiles();
    }

    /**
     * Get Title from given book file.
     * Returns an array with bookId and Title.
     *
     * @param bookId
     * @return
     * @throws IOException
     */
    public String[] getTitle(int bookId)
    {
        return getFieldFromBook( getBookFile( bookId ), "Title: " );
    }

    /**
     * Get Title from all books.
     *
     * @return
     * @throws IOException
     */
    public List<String[]> getTitles()
    {
        return getFieldFromBooks( "Title: " );
    }

    /**
     * Get Author from given book file.
     * Returns an array with bookId and Author.
     *
     * @param bookId
     * @return
     * @throws IOException
     */
    public String[] getAuthor(int bookId)
    {
        return getFieldFromBook( getBookFile( bookId ), "Author: " );
    }

    /**
     * Get Authors from all books.
     *
     * @return
     * @throws IOException
     */
    public List<String[]> getAuthors()
    {
        return getFieldFromBooks( "Author: " );
    }

    public void getAuthorBookPivot()
    {

    }

    /**
     * Get all citites from cities15000.csv
     *
     * @return
     */
    public List<City> getCitiesFromCSV()
    {
        List<City> cities = new ArrayList<>();

        try
        {
            String citiesCSV = new File( "src/main/files/csvfiles/cities15000.csv" ).toURI().getPath();
            BufferedReader reader = new BufferedReader( new FileReader( citiesCSV ) );
            String line;

            // Skip the first/header line in file.
            reader.readLine();

            while( ( line = reader.readLine() ) != null )
            {
                cities.add( new City( line.trim().split( ";" ) ) );
            }
        }
        catch( IOException e )
        {
            System.err.println( "Error! An unexpected error occurred. Cant not read the file, please try again." );
        }

        return cities;
    }

    /**
     * Returns a String array whit bookId and value of given field.
     *
     * @param file
     * @param field
     * @return
     * @throws IOException
     */
    private String[] getFieldFromBook(File file, String field)
    {
        String bookId = file.getName().replace( ".txt", "" );
        StringBuilder builder = new StringBuilder();

        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( file ) );
            String line;

            while( ( line = reader.readLine() ) != null )
            {
                if( line.startsWith( field ) )
                {
                    builder.append( line.substring( 7 ).trim() ).append( " " );

                    // check value have multiple lines.
                    while( ( line = reader.readLine() ) != null && !line.isEmpty() )
                    {
                        // if line contains another field then break inner while.
                        if( !isValueMultipleLines( line ) )
                            break;

                        // else take the line and append to builder.
                        builder.append( line.trim() ).append( " " );
                    }

                    reader.close();

                    // the value is picked, break outer while.
                    break;
                }
            }
        }
        catch( IOException e )
        {
            System.err.println( "Error! An unexpected error occurred. Cant not read the file, please try again." );
        }

        // returns bookid and value of given field.
        return new String[]{ bookId, builder.toString() };
    }

    /**
     * Returns a List with String array, that include bookId and value of given field
     *
     * @param field
     * @return
     * @throws IOException
     */
    private List<String[]> getFieldFromBooks(String field)
    {
        List<String[]> values = new ArrayList<>();

        File[] books = getBookFiles();

        for( File book : books )
        {
            values.add( getFieldFromBook( book, field ) );
        }

        return values;
    }

    /**
     * Check for multiplelines
     *
     * @param line
     * @return
     */
    private boolean isValueMultipleLines(String line)
    {
        String[] fields = { "Title:", "Author:", "Release Date:", "Language:", "haracter set encoding:", "Reposted:" };

        for( String field : fields )
        {
            if( line.contains( field ) )
                return false;
        }

        return true;
    }
}

