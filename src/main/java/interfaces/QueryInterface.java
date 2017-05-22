package interfaces;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismailcam on 07/05/2017.
 */
public interface QueryInterface
{
    List<Book> booksByCity(String city);

    List<Book> booksByTitle(String title);

    List<Book> booksByAuthor(String author);

    List<Book> booksByLocation(String location);
}
