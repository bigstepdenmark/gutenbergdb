package io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ismailcam on 18/05/2017
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

    public File getFile()
    {
        return file;
    }

    public String asString() throws IOException
    {
        return Files.toString( file, Charsets.UTF_8 );
    }

    public List<String> asLines() throws IOException
    {
        return Files.readLines( file, Charsets.UTF_8 );
    }

    public Iterator<String> asIterator() throws IOException
    {
        return Files.readLines( file, Charsets.UTF_8 ).iterator();
    }

    public String getName()
    {
        return Files.getNameWithoutExtension( getFullPath() );
    }

    public String getNameWithExtension()
    {
        return file.getName();
    }

    private String getFilesDirectory()
    {
        return new File( "src/main/files/" ).toURI().getPath();
    }

    private String getFullPath()
    {
        return new File( getFilesDirectory() + path ).toURI().getPath();
    }
}
