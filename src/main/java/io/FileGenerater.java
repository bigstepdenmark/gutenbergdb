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

    private String generateFileName(String prefix)
    {
        return prefix + "_" + new SimpleDateFormat( "dd-MM-yyyy_HH-m-ss" ).format( new Date() );
    }

    private File createEmptyFile(String prefix, String extension) throws IOException
    {
        String path = getOutputDir() + generateFileName( prefix ) + "." + extension;
        Files.touch( new File( path ) );

        return new File( path );
    }

    private String getOutputDir()
    {
        return new File( "src/main/files/output/" ).toURI().getPath();
    }
}
