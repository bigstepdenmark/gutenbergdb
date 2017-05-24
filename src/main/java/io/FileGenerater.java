package io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ismailcam on 20/05/2017.
 */
public class FileGenerater
{
    /**
     * Write content to file by given list of String lines.
     * @param prefix
     * @param extension
     * @param lines
     * @throws IOException
     */
    public void writeToFile(String prefix, String extension, List<String> lines) throws IOException
    {
        File file = createEmptyFile( prefix, extension );
        BufferedWriter buffer = Files.newWriter( file, Charsets.UTF_8 );

        for( String line : lines )
        {
            buffer.append( line ).append( "\n" );
        }

        buffer.flush();
        buffer.close();
    }

    /**
     * Generate file name by given prefix and current datetime.
     * @param prefix
     * @return
     */
    private String generateFileName(String prefix)
    {
        return prefix + "_" + new SimpleDateFormat( "dd-MM-yyyy_HH-m-ss" ).format( new Date() );
    }

    /**
     * Create empty file in output directory
     * @param prefix
     * @param extension
     * @return
     * @throws IOException
     */
    private File createEmptyFile(String prefix, String extension) throws IOException
    {
        String path = getOutputDir() + generateFileName( prefix ) + "." + extension;
        Files.touch( new File( path ) );

        return new File( path );
    }

    /**
     * Get path of output folder.
     * @return
     */
    private String getOutputDir()
    {
        return new File( "src/main/files/output/" ).toURI().getPath();
    }
}
