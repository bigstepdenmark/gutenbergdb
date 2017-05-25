package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by ismailcam on 25/05/2017.
 */
public class FileGeneraterTest
{
    FileGenerater fileGenerater;

    @Before
    public void setUp() throws Exception
    {
        fileGenerater = new FileGenerater();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void writeToFile() throws Exception
    {
//        // Mock list
//        List<String> mockedList = mock( List.class );
//
//        // Stubbing
//        when( mockedList.get( 0 ) ).thenReturn( "index 1" );
//        when( mockedList.get( 1 ) ).thenReturn( "index 2" );
//        when( mockedList.get( 2 ) ).thenReturn( "index 3" );
//
//        FileGenerater mockedFileGenerater = mock( FileGenerater.class );
//
//        //Mockito.doCallRealMethod().when( fileGenerater ).writeToFile( "test", "txt", mockedList );
//
//        doThrow( new Exception() ).when( mockedFileGenerater ).writeToFile( "test", "txt", null );
//
//        assertThat( mockedFileGenerater.writeToFile( "test", "txt", mockedList ), is(  ) );
    }

}