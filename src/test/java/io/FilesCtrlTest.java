package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

/**
 * Created by ismailcam on 25/05/2017.
 */
public class FilesCtrlTest
{
    FilesCtrl filesCtrl;

    @Before
    public void setUp() throws Exception
    {
        filesCtrl = new FilesCtrl( "test" );
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void countFiles() throws Exception
    {
        assertThat( filesCtrl.getFiles().length, is( 3 ) );
    }

    @Test
    public void textStartWith() throws Exception
    {
        String startWith = "The Project Gutenberg EBook of Peter Pan, by James M. Barrie";
        assertThat( filesCtrl.asStringList().get( 2 ).startsWith( startWith ), is( true ) );
    }

    @Test
    public void listSize() throws Exception
    {
        assertThat( filesCtrl.asLineList().size(), is( 3 ) );
    }

    @Test
    public void iteratorWithStringHasNext() throws Exception
    {
        assertThat( filesCtrl.asStringIterator().hasNext(), is( true ) );
    }

    @Test
    public void iteratorWithListHasNext() throws Exception
    {
        assertThat( filesCtrl.asLineIterator().hasNext(), is( true ) );
    }

    @Test
    public void fileNameWithoutextension() throws Exception
    {
        assertThat( filesCtrl.getNames().get( 1 ), is( "test2" ) );
    }

    @Test
    public void fileNameWithExtension() throws Exception
    {
        assertThat( filesCtrl.getNamesWithExtension().get( 1 ), is( "test2.txt" ) );
    }

}