package entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismailcam on 07/05/2017.
 */
public class Book
{
    private int id;
    private String title;
    private Author author;
    private List<City> cities;

    public Book(int id, String title, Author author)
    {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    public List<City> getCities()
    {
        return cities;
    }

    public void setCities(List<City> cities)
    {
        this.cities = cities;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", cities=" + cities +
                '}';
    }
}
