/**
 * Created by liudmylaiterman on 3/3/18.
 */
public class Repository {
    private int id;
    private String name;
    private int forks_count;
    private int stargazers_count;
    private int watchers_count;


    public int getId() {
        return id;
    }
    public int getForks_count() {return forks_count;}
    public int getStargazers_count() {return stargazers_count;}
    public int getWatchers_count() {return watchers_count;}
    public String getName() {return name;}

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {this.name = name;}
    public void setForks_count(int forks_count) {this.forks_count = forks_count;}
    public void setStargazers_count(int stargazers_count) {this.stargazers_count = stargazers_count;}
    public void setWatchers_count(int watchers_count) {this.watchers_count = watchers_count;}

}
