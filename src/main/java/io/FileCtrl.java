package io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ismailcam on 18/05/2017.
 */
public class FileCtrl
{
    private String path;
    private File file;

    public FileCtrl(String path)
    {
        this.path = path;
        this.file = new File( getFullPath() );
    }

    /**
     * Get file by given path when instantiation of the class
     * @return
     */
    public File getFile()
    {
        return file;
    }

    /**
     * Get file content as String
     * @return
     * @throws IOException
     */
    public String asString() throws IOException
    {
        return Files.toString( file, Charsets.UTF_8 );
    }

    /**
     * Get file as list with String lines
     * @return
     * @throws IOException
     */
    public List<String> asLines() throws IOException
    {
        return Files.readLines( file, Charsets.UTF_8 );
    }

    /**
     * Get file as Iterator with String lines
     * @return
     * @throws IOException
     */
    public Iterator<String> asIterator() throws IOException
    {
        return Files.readLines( file, Charsets.UTF_8 ).iterator();
    }

    /**
     * Get file name without extension
     * @return
     */
    public String getName()
    {
        return Files.getNameWithoutExtension( getFullPath() );
    }

    /**
     * Get file name with extension
     * @return
     */
    public String getNameWithExtension()
    {
        return file.getName();
    }

    /**
     * Get the files directory path
     * @return
     */
    private String getFilesDirectory()
    {
        return new File( "src/main/files/" ).toURI().getPath();
    }

    /**
     * Get the full path of file
     * @return
     */
    private String getFullPath()
    {
        return new File( getFilesDirectory() + path ).toURI().getPath();
    }
}
