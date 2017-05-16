package interfaces;

import entity.Book;

import java.util.ArrayList;

/**
 * Created by ismailcam on 07/05/2017.
 */
public interface QueryInterface
{
    ArrayList<Book> booksByCity(String city);

    ArrayList<Book> booksByTitle(String title);

    ArrayList<Book> booksByAuthor(String author);

    ArrayList<Book> booksByLocation(String location);
}
