package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

/**
 * Created by ismailcam on 25/05/2017.
 */
public class FileCtrlTest
{
    FileCtrl fileCtrl;

    @Before
    public void setUp() throws Exception
    {
        fileCtrl = new FileCtrl( "test/test1.txt" );
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void fileExists() throws Exception
    {
        assertThat( fileCtrl.getFile().exists(), is( true ) );
    }

    @Test
    public void textStartWith() throws Exception
    {
        String startText = "The Project Gutenberg EBook of The Rime of the Ancient Mariner, by";
        assertThat( fileCtrl.asString().startsWith( startText ), is( true ) );
    }

    @Test
    public void listSize() throws Exception
    {
        assertThat( fileCtrl.asLines().size(), is( 1217 ) );
    }

    @Test
    public void iteratorHasNext() throws Exception
    {
        assertThat( fileCtrl.asIterator().hasNext(), is( true ) );
    }

    @Test
    public void fileNameWithoutExtension() throws Exception
    {
        assertThat( fileCtrl.getName(), is( "test1" ) );
    }

    @Test
    public void fileNameWithExtension() throws Exception
    {
        assertThat( fileCtrl.getNameWithExtension(), is( "test1.txt" ) );
    }

}