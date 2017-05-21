package interfaces;

import entity.Author;
import entity.Book;
import entity.City;

import java.util.List;

/**
 * Created by ismailcam on 21/05/2017.
 */
public interface BookInterface
{
    List<Book> all();
    List<Book> where(String column, String value);
    Author getAuthor();
    List<Author> getAuthors();
    List<City> getCities();
}
