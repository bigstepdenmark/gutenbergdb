package io;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ismailcam on 15/05/2017.
 */
public class WriteController
{
    private String delimeter;
    private ReadController readController;

    public WriteController(String delimeter)
    {
        this.delimeter = delimeter;
        this.readController = new ReadController();
    }

    /**
     * Get the directory of output files.
     *
     * @return
     */
    public String getDir()
    {
        return new File( "src/main/files/csvfiles/" ).toURI().getPath();
    }

    /**
     * Create a empty file in output directory
     *
     * @param filename
     * @return
     */
    public boolean generate(String filename)
    {
        try
        {
            FileWriter writer = new FileWriter( getDir() + filename );

            writer.flush();
            writer.close();
            return true;
        }
        catch( IOException e )
        {
            return false;
        }
    }

    /**
     * Create new file in output directory, with given values.
     *
     * @param values
     * @param filename
     * @param header
     * @throws IOException
     */
    public void generate(List<String[]> values, String filename, String... header)
    {
        try
        {
            FileWriter writer = new FileWriter( getDir() + filename );

            // Headerline
            writer.append( String.join( delimeter, header ) ).append( "\n" );

            for( String[] title : values )
            {
                writer.append( title[ 0 ] ).append( delimeter ).append( title[ 1 ] ).append( "\n" );
            }

            writer.flush();
            writer.close();

            System.out.println( "File is successfully generated to path: " + getDir() + filename );
        }
        catch( IOException e )
        {
            System.err.println( "Error! The file could not be created!" );
        }
    }

    public void generateBooks()
    {
        generate( readController.getTitles(), filename( "books" ), "id", "title" );
    }

    public void generateAuthors()
    {
        generate( readController.getAuthors(), filename( "authors" ), "book_id", "author" );
    }

    private String filename(String prefix)
    {
        return prefix + "_" + new SimpleDateFormat( "dd-MM-yyyy_HH:m:ss" ).format( new Date() );
    }

    public static void main(String[] args)
    {
        WriteController w = new WriteController( ";" );

        w.generateAuthors();
    }
}
