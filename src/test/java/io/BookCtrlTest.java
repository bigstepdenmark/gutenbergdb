package io;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
public class BookCtrlTest {

    BookCtrl book;

    @Before
    public void setUp() throws Exception {
        book = new BookCtrl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIfCityListIsEmpty() throws IOException {
        assertThat(book.getCities().isEmpty(), is(false));
    }


    @Test
    public void testGetTitle(){
      List<String> mockTitleList = new ArrayList<>();
        mockTitleList.add("he Rime of the Ancient Mariner");
        mockTitleList.add("Lincolns Second Inaugural Address, March 4, 1865");
        mockTitleList.add("Peter Pan Peter Pan and Wendy");

        BookCtrl book1 = mock(BookCtrl.class);
        when(book1.getTitles(false)).thenReturn(mockTitleList);
    }

    @Test
    public void testGetAuthor(){
      List<String> mockAuthorList = mock(List.class);
      when(mockAuthorList.get(0)).thenReturn("Samuel Taylor Coleridge 1");
      when(mockAuthorList.get(1)).thenReturn("Abraham Lincoln");
      when(mockAuthorList.get(2)).thenReturn("James M. Barrie");

      BookCtrl book2 = mock(BookCtrl.class);
      when(book2.getAuthors(false)).thenReturn(mockAuthorList);
    }




}