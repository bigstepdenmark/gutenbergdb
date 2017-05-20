package io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ismailcam on 18/05/2017.
 */
public class FilesCtrl
{
    private String path;
    private File[] files;

    public FilesCtrl(String path)
    {
        this.path = path;
        this.files = new File( getFullPath() ).listFiles();
    }

    public File[] getFiles()
    {
        return files;
    }

    public List<String> asStringList() throws IOException
    {
        List<String> list = new ArrayList<>();

        for( File file : files )
        {
            list.add( Files.toString( file, Charsets.UTF_8 ) );
        }

        return list;
    }

    public List<List<String>> asLineList() throws IOException
    {
        List<List<String>> lists = new ArrayList<>();

        for( File file : files )
        {
            lists.add( Files.readLines( file, Charsets.UTF_8 ) );
        }

        return lists;
    }

    public Iterator<String> asStringIterator() throws IOException
    {
        return asStringList().iterator();
    }

    public Iterator<List<String>> asLineIterator() throws IOException
    {
        return asLineList().iterator();
    }

    public List<String> getNames()
    {
        List<String> fileNames = new ArrayList<>();

        for( File file : files )
        {
            fileNames.add( Files.getNameWithoutExtension( file.toURI().getPath() ) );
        }

        return fileNames;
    }

    public List<String> getNamesWithExtension()
    {
        List<String> fileNames = new ArrayList<>();

        for( File file : files )
        {
            fileNames.add( file.getName() );
        }

        return fileNames;
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
