package io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import entity.Book;
import entity.City;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by ismailcam on 23/05/2017.
 */
public class MapGenerater
{
    private String path;
    private File file;
    private File indexHtml;
    private BufferedWriter writer;

    public MapGenerater()
    {
        try
        {
            this.path = new File( "src/main/files/html/" ).toURI().getPath();
            this.file = new File( this.path + "locations.js" );
            this.writer = Files.newWriter( file, Charsets.UTF_8 );
            this.indexHtml = new File( this.path + "index.html" );
        }
        catch( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Generate map by given Book list.
     * @param books
     * @param openMap
     */
    public void generate(List<Book> books, boolean openMap)
    {
        StringBuilder builder = new StringBuilder();

        builder.append( "var locs = [" );

        for( Book book : books )
        {
            for( City city : book.getCities() )
            {
                builder.append( "['" )
                       .append( city.getName() )
                       .append( "'," )
                       .append( city.getLatitude() )
                       .append( "," )
                       .append( city.getLongitude() )
                       .append( "], " );
            }
        }

        builder.append( "];" )
               .append( "\n" )
               .append( "var cLoc = [" )
               .append( books.get( 0 ).getCities().get( 0 ).getLatitude() )
               .append( "," )
               .append( books.get( 0 ).getCities().get( 0 ).getLongitude() )
               .append( "];" );

        writeToFile( builder.toString() );

        if( openMap )
            openMapInBrowser();
    }

    /**
     * Write to file by given content
     * @param content
     */
    private void writeToFile(String content)
    {
        try
        {
            writer.write( content );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            closeWriter();
        }
    }

    /**
     * Open the generated map in the default browser.
     */
    private void openMapInBrowser()
    {
        try
        {
            Desktop.getDesktop().browse( indexHtml.toURI() );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Close Bufferwriter
     */
    private void closeWriter()
    {
        try
        {
            writer.flush();
            writer.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
