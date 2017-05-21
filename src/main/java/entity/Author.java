package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismailcam on 15/05/2017.
 */
public class Author
{
    private int id;
    private String name;
    private List<Book> books = new ArrayList<>();

    public Author(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Author()
    {
    }

    public Author(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    @Override
    public String toString()
    {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

