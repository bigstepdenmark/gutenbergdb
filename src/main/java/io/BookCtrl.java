package io;

import entity.Author;
import entity.Book;
import entity.City;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ismailcam on 18/05/2017.
 */
public class BookCtrl
{
    private static String[] fields = { "Title:", "Author:", "Release Date:", "Language:", "haracter set encoding:", "Reposted:", "Posting Date:" };
    public static String delimeter = ";";

    /**
     * Scan Titles from book files and return them as a list.
     * @param withBookId
     * @return
     */
    public List<String> getTitles(boolean withBookId)
    {
        List<String> titles = new ArrayList<>();

        try
        {
            titles = getFields( "Title: ", withBookId );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        return titles;
    }

    /**
     * Scan Authors from book files and return them as a list
     * @param withBookId
     * @return
     */
    public List<String> getAuthors(boolean withBookId)
    {
        List<String> authors = new ArrayList<>();

        try
        {
            authors = getFields( "Author: ", withBookId );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        return authors;
    }

    /**
     * Get all cities from src/files/csvfiles/cities15000.csv file
     * and return them as a list of City objects.
     * @return
     * @throws IOException
     */
    public List<City> getCities() throws IOException
    {
        // Header: id;name;latitude;longitude;country_code;population;timezone
        FileCtrl fileCtrl = new FileCtrl( "csvfiles/cities15000.csv" );
        List<City> cities = new ArrayList<>();

        for( String city : fileCtrl.asLines() )
        {
            cities.add( new City( city.split( delimeter ) ) );
        }

        return cities;
    }

    /**
     * Get a map with Author objects. The objects also include book that belongs to Author.
     * @return
     */
    public HashMap<String, Author> getAuthorsWithBooks()
    {
        List<String> authors = getAuthors( true );
        HashMap<String, Author> authorsMap = new HashMap<>();

        int i = 0;
        for( String authorWithBookId : authors )
        {
            String[] data = authorWithBookId.split( delimeter );
            int bookId = Integer.parseInt( data[ 0 ] );
            String authorname = data[ 1 ];

            if( authorsMap.containsKey( authorname ) )
            {
                authorsMap.get( authorname ).getBooks().add( new Book( bookId ) );
            }
            else
            {
                Author author = new Author( i, authorname );
                author.getBooks().add( new Book( bookId ) );
                authorsMap.put( authorname, author );
                i++;
            }
        }

        return authorsMap;
    }

    /**
     * Get a list of Author names, with Author name and *generated Author id.
     * *The Author id is generated in getAuthorsWithBooks() method.
     * @return
     */
    public List<String> getDistinctAuthorList()
    {
        HashMap<String, Author> authorsMap = getAuthorsWithBooks();
        List<String> authorList = new ArrayList<>();

        for( Author author : authorsMap.values() )
        {
            authorList.add( author.getId() + delimeter + author.getName() );
        }

        return authorList;
    }

    /**
     * Get a list with bookId and authorId. Values in list is
     * seperated whit delimeter variable
     * @return
     */
    public List<String> getAuthorBooksPivot()
    {
        List<String> pivot = new ArrayList<>();

        for( Author author : getAuthorsWithBooks().values() )
        {
            for( Book book : author.getBooks() )
            {
                pivot.add( book.getId() + delimeter + author.getId() );
            }
        }

        return pivot;
    }

    /**
     * Generates CSV files by given list
     * @param filename
     * @param lines
     */
    public void importToCSV(String filename, List<String> lines)
    {
        FileGenerater generater = new FileGenerater();
        try
        {
            generater.writeToFile( filename, "csv", lines );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Get field value from book file by given field.
     * @param file
     * @param field
     * @param withBookId
     * @return
     * @throws IOException
     */
    private String getField(File file, String field, boolean withBookId) throws IOException
    {
        FileCtrl fileCtrl = new FileCtrl( "books/" + file.getName() );
        Iterator<String> lines = fileCtrl.asIterator();
        StringBuilder builder = new StringBuilder();

        while( lines.hasNext() )
        {
            String line = lines.next();

            if( line.startsWith( field ) )
            {
                // Remove fieldname and get only value of field
                builder.append( line.substring( 7 ).replace( "\"", "" ).trim() ).append( " " );

                // Check if value have multiple lines
                multiple:
                while( lines.hasNext() && !line.isEmpty() )
                {
                    line = lines.next();

                    // If next contains an another field, then break while.
                    for( String s : fields )
                    {
                        if( line.startsWith( s ) )
                            break multiple;
                    }

                    builder.append( line.replace( "\"", "" ).trim() ).append( " " );
                }

                break;
            }
        }

        // If field not found, then return Unknown.
        if( builder.length() < 1 )
            builder.append( "Unknown" );

        String title = builder.toString().trim();
        return withBookId ? fileCtrl.getName() + delimeter + title : title;
    }

    /**
     * Get field values from book files by given field.
     * @param field
     * @param withBookId
     * @return
     * @throws IOException
     */
    private List<String> getFields(String field, boolean withBookId) throws IOException
    {
        FilesCtrl filesCtrl = new FilesCtrl( "books" );
        File[] files = filesCtrl.getFiles();
        List<String> fieldValues = new ArrayList<>();

        for( File file : files )
        {
            fieldValues.add( getField( file, field, withBookId ) );
        }

        return fieldValues;
    }

}
