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

    public static void main(String[] args) throws IOException
    {
        BookCtrl bookCtrl = new BookCtrl();

        // Er overført 20-05-2017
        // bookCtrl.importToCSV( "titles", bookCtrl.getTitles( true ) );
        // bookCtrl.importToCSV( "bookAuthorPivot", bookCtrl.getAuthorBooksPivot() );
        // bookCtrl.importToCSV( "authors", bookCtrl.getDistinctAuthorList() );
        // Cities er overført og hedder output/bookcitypivot.csv
    }

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

    public List<City> getCities() throws IOException
    {
        // id;name;latitude;longitude;country_code;population;timezone
        FileCtrl fileCtrl = new FileCtrl( "csvfiles/cities15000.csv" );
        List<City> cities = new ArrayList<>();

        for( String city : fileCtrl.asLines() )
        {
            cities.add( new City( city.split( delimeter ) ) );
        }

        return cities;
    }

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

    // Use CityConroller instead of this method.
    public List<Book> getBooksWithCities() throws IOException
    {
        File[] files = new FilesCtrl( "books" ).getFiles();
        List<City> cities = getCities();
        List<Book> books = new ArrayList<>();

        for( File file : files )
        {
            FileCtrl fileCtrl = new FileCtrl( "books/" + file.getName() );
            // String book = new FileCtrl( "books/" + file.getName() ).asString();
            Book book = new Book( Integer.parseInt( fileCtrl.getName() ), getField( file, "Title: ", false ) );
            book.setContent( fileCtrl.asString() );

            for( City city : cities )
            {
                if( book.getContent().contains( city.getName() ) )
                {
                    book.getCities().add( city );
                }
            }

            books.add( book );
        }

        return books;
    }

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

    public void importToCSV(String filename, List<String> lines)
    {
        FileGenerater generater = new FileGenerater();
        try
        {
            generater.writeToFile( filename, lines );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

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
