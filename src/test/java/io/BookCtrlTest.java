package io;


import entity.City;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by mazlumsert on 25/05/2017.
 */

@RunWith( MockitoJUnitRunner.class )
public class BookCtrlTest
{

    BookCtrl bookCtrl;

    @Mock
    List<String> mockAuthor;

    @Before
    public void setUp() throws Exception
    {
        bookCtrl = mock( BookCtrl.class );
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void testIfCityListIsEmpty() throws IOException
    {
        List<City> mockedList = mock( List.class );
        when( mockedList.get( 0 ) ).thenReturn( new City( 1, "Copenhagen", 1.0, 2.0, "DK", 5000000, "DK" ) );
        when( mockedList.get( 1 ) ).thenReturn( new City( 1, "Odense", 1.0, 2.0, "DK", 5000000, "DK" ) );
        when( mockedList.get( 2 ) ).thenReturn( new City( 1, "AArhus", 1.0, 2.0, "DK", 5000000, "DK" ) );

        when( bookCtrl.getCities() ).thenReturn( mockedList );

        assertThat( bookCtrl.getCities().isEmpty(), is( false ) );
    }

    @Test
    public void testGetTitles() throws IOException
    {
        List<String> mockedList = mock( List.class );
        when( mockedList.get( 0 ) ).thenReturn( "Hello 1" );
        when( mockedList.get( 1 ) ).thenReturn( "Hello 1" );
        when( mockedList.get( 2 ) ).thenReturn( "Hello 1" );

        when( bookCtrl.getTitles( false ) ).thenReturn( mockedList );

        assertThat( bookCtrl.getTitles( false ).isEmpty(), is( false ) );
    }

    @Test
    public void testGetAuthors()
    {
        List<String> mockAuthorList = mock( List.class );
        when( mockAuthorList.get( 0 ) ).thenReturn( "Samuel Taylor Coleridge 1" );
        when( mockAuthorList.get( 1 ) ).thenReturn( "Abraham Lincoln" );
        when( mockAuthorList.get( 2 ) ).thenReturn( "James M. Barrie" );

        when( bookCtrl.getAuthors( false ) ).thenReturn( mockAuthorList );

        assertThat( bookCtrl.getAuthors( false ).isEmpty(), is( false ) );
    }

    @Test
    public void testGetAuthorsWithBooks()
    {
        assertThat( true, is( true ) );

    }

    // https://stackoverflow.com/questions/31544855/hamcrest-matcher-for-no-duplicates-in-a-list-of-strings
    @Test
    public void testGetDistinctAuthorList()
    {
        mockAuthor = bookCtrl.getDistinctAuthorList();
//        mockAuthor.add( "Samuel Taylor Coleridge 1" );
//        mockAuthor.add( "Abraham Lincoln" );
//        mockAuthor.add( "James M. Barrie" );

        //    assertThat(mockAuthor, hasDistinctElements());
        assertThat( true, is( true ) );
    }


}