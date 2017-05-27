package io;

import entity.Author;
import entity.City;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by ismailcam on 27/05/2017.
 */
@RunWith( MockitoJUnitRunner.class ) // Enable Mockito Annotations
public class BookCtrlTest
{
    @Mock
    BookCtrl bookCtrl;

    @Before
    public void setUp() throws Exception
    {
        // bookCtrl = mock( BookCtrl.class );
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void titlelist_is_not_empty() throws Exception
    {
        List<String> mockedList = mock( List.class );

        when( mockedList.isEmpty() ).thenReturn( false );
        when( bookCtrl.getTitles( false ) ).thenReturn( mockedList );

        assertThat( bookCtrl.getTitles( false ).isEmpty(), is( false ) );
    }

    @Test
    public void authorlist_is_not_empty() throws Exception
    {
        List<String> mockAuthorList = mock( List.class );

        when( mockAuthorList.isEmpty() ).thenReturn( false );
        when( bookCtrl.getAuthors( false ) ).thenReturn( mockAuthorList );

        assertThat( bookCtrl.getAuthors( false ).isEmpty(), is( false ) );
    }

    @Test
    public void exist_given_city_in_citylist() throws Exception
    {
        List<City> mockedList = mock( List.class );

        when( mockedList.get( 0 ) ).thenReturn( new City( 1,
                                                          "Copenhagen",
                                                          55.674732,
                                                          12.573197,
                                                          "DK",
                                                          250000,
                                                          "Europe/Copenhagen" ) );

        when( mockedList.get( 1 ) ).thenReturn( new City( 2,
                                                          "Odense",
                                                          55.403531,
                                                          10.402281,
                                                          "DK",
                                                          50000,
                                                          "Europe/Copenhagen" ) );

        when( mockedList.get( 2 ) ).thenReturn( new City( 3,
                                                          "Aarhus",
                                                          56.163940,
                                                          10.202740,
                                                          "DK",
                                                          65000,
                                                          "Europe/Copenhagen" ) );

        when( bookCtrl.getCities() ).thenReturn( mockedList );

        assertThat( bookCtrl.getCities().get( 0 ).getName(), is( "Copenhagen" ) );
        assertThat( bookCtrl.getCities().get( 1 ).getName(), is( "Odense" ) );
        assertThat( bookCtrl.getCities().get( 2 ).getName(), is( "Aarhus" ) );
    }

    @Test
    public void check_if_HashMap_return_right_authorId_by_given_key() throws Exception
    {
        HashMap<String, Author> mockedMapList = mock( HashMap.class );
        when( mockedMapList.get( "Samuel Taylor Coleridge" ) ).thenReturn( new Author( 1, "Samuel Taylor Coleridge" ) );
        when( mockedMapList.get( "Abraham Lincoln" ) ).thenReturn( new Author( 2, "Abraham Lincoln" ) );
        when( mockedMapList.get( "James M. Barrie" ) ).thenReturn( new Author( 3, "James M. Barrie" ) );

        when( bookCtrl.getAuthorsWithBooks() ).thenReturn( mockedMapList );

        assertThat( bookCtrl.getAuthorsWithBooks()
                            .get( "Samuel Taylor Coleridge" )
                            .getId(), is( 1 ) );
        assertThat( bookCtrl.getAuthorsWithBooks()
                            .get( "Abraham Lincoln" )
                            .getId(), is( 2 ) );
        assertThat( bookCtrl.getAuthorsWithBooks()
                            .get( "James M. Barrie" )
                            .getId(), is( 3 ) );
    }

    @Test( expected = IOException.class )
    public void when_import_to_csv_throw_exception() throws Exception
    {
        doThrow( IOException.class ).when( bookCtrl ).importToCSV( "data", null );
        bookCtrl.importToCSV( "data", null );
    }

}