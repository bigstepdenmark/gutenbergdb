package io;


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

@RunWith(MockitoJUnitRunner.class)
public class BookCtrlTest {

    BookCtrl book;

    @Mock
    List<String> mockAuthor;

    @Before
    public void setUp() throws Exception {
        book = new BookCtrl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = IOException.class)
    public void testIfCityListIsEmpty() throws IOException {
        assertThat(book.getCities().isEmpty(), is(false));
    }

    @Test(expected = IOException.class)
    public void testGetTitle(){
      List<String> mockTitleList = new ArrayList<>();
        mockTitleList.add("he Rime of the Ancient Mariner");
        mockTitleList.add("Lincolns Second Inaugural Address, March 4, 1865");
        mockTitleList.add("Peter Pan Peter Pan and Wendy");

        BookCtrl book1 = mock(BookCtrl.class);
        when(book1.getTitles(false)).thenReturn(mockTitleList);
    }

    @Test(expected = IOException.class)
    public void testGetAuthor(){
      List<String> mockAuthorList = mock(List.class);
      when(mockAuthorList.get(0)).thenReturn("Samuel Taylor Coleridge 1");
      when(mockAuthorList.get(1)).thenReturn("Abraham Lincoln");
      when(mockAuthorList.get(2)).thenReturn("James M. Barrie");

      BookCtrl book2 = mock(BookCtrl.class);
      when(book2.getAuthors(false)).thenReturn(mockAuthorList);
    }

    @Test
    public void testGetAuthorsWithBooks(){



    }

    // https://stackoverflow.com/questions/31544855/hamcrest-matcher-for-no-duplicates-in-a-list-of-strings
    public void testGetDistinctAuthorList(){
        mockAuthor = book.getDistinctAuthorList();
        mockAuthor.add("Samuel Taylor Coleridge 1");
        mockAuthor.add("Abraham Lincoln");
        mockAuthor.add("James M. Barrie");

    //    assertThat(mockAuthor, hasDistinctElements());
    }



}