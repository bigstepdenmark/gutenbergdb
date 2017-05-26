package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
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
        FileGenerater mockedFileGenerater = mock( FileGenerater.class );
        when( mockedFileGenerater );

        Method method = FileGenerater.class.getDeclaredMethod( "createEmptyFile", String.class, String.class );
        method.setAccessible( true );
        String s = (String) method.invoke( mockedFileGenerater,  "test", "txt");

        System.out.println(s);
    }

}