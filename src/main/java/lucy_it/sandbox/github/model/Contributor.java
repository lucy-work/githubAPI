package lucy_it.sandbox.github.model;

/**
 * Created by liudmylaiterman on 3/4/18.
 */
public class Contributor {
   private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contributor that = (Contributor) o;

        return author != null ? author.equals(that.author) : that.author == null;

    }

    @Override
    public int hashCode() {
        return author != null ? author.hashCode() : 0;
    }
}
