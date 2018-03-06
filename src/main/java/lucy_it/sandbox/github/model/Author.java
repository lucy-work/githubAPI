package lucy_it.sandbox.github.model;

/**
 * Created by liudmylaiterman on 3/4/18.
 */
public class Author {
    private int id;
    private String login;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
