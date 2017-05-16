package io;


import com.opencsv.CSVReader;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by ismailcam on 08/05/2017.
 */
public class ImportData
{
    private static String[] FIELDS = { "Title:", "Author:", "Release Date:", "Language:", "haracter set encoding:", "Reposted:" };
    private static String DELIMETER = ";";

    public static void main(String[] args) throws IOException
    {
        ImportData ia = new ImportData();


        //  ia.fff();

//        for( String s : ia.printCityName() )
//        {
//            System.out.println( s );
//        }


//        ia.importToCSV( "authors", ia.getAuthors( true ), "id", "author" );
//        ia.importToCSV( "titles", ia.getTitles(), "id", "title" );
//        ia.importToCSV( "author_books", ia.getAuthorBooks(), "book_id", "author_id" );


    }

    /**
     * Get Titles from books.
     *
     * @return
     */
    private ArrayList<String> getTitles()
    {
        return scanFiles( "Title: ", true );
    }

    /**
     * Get Authors from books.
     *
     * @return
     */
    private ArrayList<String> getAuthorBooks()
    {
        ArrayList<String> authors = getAuthors( false );
        ArrayList<String> authorsDistinct = new ArrayList<String>( new LinkedHashSet<String>( authors ) );

        for( int i = 0; i < authors.size(); i++ )
        {
            for( int j = 0; j < authorsDistinct.size(); j++ )
            {
                if( authors.get( i ).equals( authorsDistinct.get( j ) ) )
                {
                    authors.set( i, Integer.toString( j ) );
                }
            }
        }

        File[] files = getBookFiles();
        String bookId;
        for( int i = 0; i < authors.size(); i++ )
        {
            bookId = files[ i ].getName().replace( ".txt", "" );
            authors.set( i, bookId + DELIMETER + authors.get( i ) );
        }


        return authors;
    }

    private ArrayList<String> getAuthors(boolean distinctWithId)
    {
        if( distinctWithId )
        {
            ArrayList<String> distinctAuthors = new ArrayList<String>( new LinkedHashSet<String>( scanFiles( "Author: ",
                                                                                                             false ) ) );
            for( int i = 0; i < distinctAuthors.size(); i++ )
            {
                distinctAuthors.set( i, i + DELIMETER + distinctAuthors.get( i ) );
            }

            return distinctAuthors;
        }

        return scanFiles( "Author: ", false );
    }

    private File[] getBookFiles()
    {
        String path = new File( "src/main/files/books/" ).toURI().getPath();
        return new File( path ).listFiles();
    }

    /**
     * Get specific field value from single file.
     *
     * @param file
     * @param field
     * @return
     */
    private String scanFile(File file, String field, boolean withBookId)
    {
        try
        {
            String bookId = file.getName().replace( ".txt", "" );
            BufferedReader reader = new BufferedReader( new FileReader( file ) );
            String line;

            while( ( line = reader.readLine() ) != null )
            {
                if( line.contains( field ) )
                {
                    if( withBookId )
                        return bookId + DELIMETER + getField( reader, line ).toString().trim();

                    return getField( reader, line ).toString().trim();
                }
            }

            reader.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get specific field value from multiple files.
     *
     * @param field
     * @return
     */
    private ArrayList<String> scanFiles(String field, boolean withBookId)
    {
        ArrayList<String> lines = new ArrayList<String>();

        File[] files = getBookFiles();

        if( files != null )
        {
            for( File file : files )
            {
                if( file.isFile() )
                {
                    lines.add( scanFile( file, field, withBookId ) );
                }
            }
        }

        return lines;
    }

    /**
     * Check the value of field has multiple lines.
     * Used in getField method.
     *
     * @param line
     * @return
     */
    private boolean isMultipleLines(String line)
    {
        for( String field : FIELDS )
        {
            if( line.contains( field ) )
                return false;
        }

        return true;
    }

    /**
     * Build field value.
     *
     * @param reader
     * @param line
     * @return
     * @throws IOException
     */
    private StringBuilder getField(BufferedReader reader, String line) throws IOException
    {
        // Start building
        StringBuilder field = new StringBuilder();

        // Remove the field name
        field.append( line.substring( 7 ) ).append( " " );

        // Check if value has multiple lines
        while( ( line = reader.readLine() ) != null && !line.isEmpty() )
        {
            if( isMultipleLines( line ) )
                field.append( line.trim() ).append( " " );
        }

        return field;
    }

    /**
     * Write data to CSV file.
     *
     * @param filename
     * @param list
     * @param header
     */
    private void importToCSV(String filename, ArrayList<String> list, String... header)
    {
        try
        {
            String path = new File( "src/main/files/csvfiles/" + filename + ".csv" ).toURI().getPath();

            FileWriter writer = new FileWriter( path );

            writer.append( String.join( DELIMETER, header ) ).append( "\n" );

            for( String line : list )
            {
                writer.append( line ).append( "\n" );
            }

            writer.flush();
            writer.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    private ArrayList<String> printCityName() throws IOException
    {
        File file = new File( "src/main/files/csvfiles/cities15000.csv" );

        CSVReader reader = new CSVReader( new FileReader( file ), ';' );
        String[] nextLine;
        ArrayList<String> citynames = new ArrayList<String>();

        while( ( nextLine = reader.readNext() ) != null )
        {
            // nextLine[] is an array of values from the line
            //System.out.println(nextLine[1]);
            citynames.add( nextLine[ 1 ] );
        }

        citynames.remove( 0 );

        return citynames;
    }

    private void fff() throws IOException
    {

        File toFile = new File( "src/main/files/csvfiles/bookcities.csv" );
        FileWriter writer = new FileWriter( toFile, true );
        ArrayList<String> cities = printCityName();


        File[] files = getBookFiles();

        if( files != null )
        {
            for( File file : files )
            {
                if( file.isFile() )
                {
                    String bookId = file.getName().replace( ".txt", "" );


                    ArrayList<String> citiesInBook = new ArrayList<>();
                    // File file = new File( "src/main/resources/books/" + bookId + ".txt" );
                    BufferedReader bookReader = new BufferedReader( new FileReader( file ) );

                    String line;
                    StringBuilder stringBuilder = new StringBuilder();

                    while( ( line = bookReader.readLine() ) != null )
                    {
                        stringBuilder.append( line.trim() );
                    }

                    String book = stringBuilder.toString();

                    for( String s : cities )
                    {
                        if( book.contains( s ) )
                        {
                            citiesInBook.add( s );
                        }
                    }

                    ArrayList<String> citiesDistinct = new ArrayList<String>( new LinkedHashSet<String>( citiesInBook ) );

                    //File toFile = new File( "src/main/resources/csvfiles/bookcities.csv" );

                    //FileWriter writer = new FileWriter( toFile, true );

                    // writer.append( String.join( DELIMETER, "book_id", "city" ) ).append( "\n" );

                    for( String inline : citiesDistinct )
                    {
                        writer.append( bookId ).append( DELIMETER ).append( inline ).append( "\n" );
                    }

                    //writer.flush();
                    //writer.close();
                }
            }
        }

    }

    private void assignCitiesToBook(File file, File file2) throws IOException
    {
        BufferedReader reader = new BufferedReader( new FileReader( file ) );
        BufferedReader reader2 = new BufferedReader( new FileReader( file2 ) );

        String line;

        while( ( line = reader.readLine() ) != null )
        {
            String cityline;
            while( ( cityline = reader2.readLine() ) != null )
            {

            }
        }
    }

}
