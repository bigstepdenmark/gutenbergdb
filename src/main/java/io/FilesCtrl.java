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

    /**
     * Get all files by given path when instantiation of the class
     * @return
     */
    public File[] getFiles()
    {
        return files;
    }

    /**
     * Get list with Strings that contains full file content.
     * @return
     * @throws IOException
     */
    public List<String> asStringList() throws IOException
    {
        List<String> list = new ArrayList<>();

        for( File file : files )
        {
            list.add( Files.toString( file, Charsets.UTF_8 ) );
        }

        return list;
    }

    /**
     * Get list with list String that contains full file content.
     * @return
     * @throws IOException
     */
    public List<List<String>> asLineList() throws IOException
    {
        List<List<String>> lists = new ArrayList<>();

        for( File file : files )
        {
            lists.add( Files.readLines( file, Charsets.UTF_8 ) );
        }

        return lists;
    }

    /**
     * Get Iterator with Strings that contains full file content.
     * @return
     * @throws IOException
     */
    public Iterator<String> asStringIterator() throws IOException
    {
        return asStringList().iterator();
    }

    /**
     * Get Iterator with list of lines that contains full file content.
     * @return
     * @throws IOException
     */
    public Iterator<List<String>> asLineIterator() throws IOException
    {
        return asLineList().iterator();
    }

    /**
     * Get all file names without extension.
     * @return
     */
    public List<String> getNames()
    {
        List<String> fileNames = new ArrayList<>();

        for( File file : files )
        {
            fileNames.add( Files.getNameWithoutExtension( file.toURI().getPath() ) );
        }

        return fileNames;
    }

    /**
     * Get all file names with extension.
     * @return
     */
    public List<String> getNamesWithExtension()
    {
        List<String> fileNames = new ArrayList<>();

        for( File file : files )
        {
            fileNames.add( file.getName() );
        }

        return fileNames;
    }

    /**
     * Get path of files directory
     * @return
     */
    private String getFilesDirectory()
    {
        return new File( "src/main/files/" ).toURI().getPath();
    }

    /**
     * Get path of directory where the files is placed.
     * @return
     */
    private String getFullPath()
    {
        return new File( getFilesDirectory() + path ).toURI().getPath();
    }
}
