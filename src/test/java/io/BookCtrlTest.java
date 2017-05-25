package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

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
        assertThat(book.getCities().isEmpty(), is(false)  );
    }

    @Test
    public void testGetTitles(){
        assertThat(book.getTitles());
    }
}