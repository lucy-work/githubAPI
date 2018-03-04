/**
 * Created by liudmylaiterman on 3/4/18.
 */
public class Owner {
    private String login;

    @Override
    public String toString() {
        return "Owner{" +
                "login='" + login + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
