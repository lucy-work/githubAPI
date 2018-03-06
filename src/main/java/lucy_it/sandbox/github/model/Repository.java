package lucy_it.sandbox.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liudmylaiterman on 3/3/18.
 */
public class Repository {
    private int id;
    private String name;
    @JsonProperty(value = "forks_count")
    private int forksCount;
    @JsonProperty(value = "stargazers_count")
    private int stargazersCount;
    @JsonProperty(value = "watchers_count")
    private int watchersCount;
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", forksCount=" + forksCount +
                ", stargazersCount=" + stargazersCount +
                ", watchersCount=" + watchersCount +
                ", owner=" + owner +
                '}';
    }
}
