package lucy_it.sandbox.github.comparator;

import lucy_it.sandbox.github.model.Repository;

import java.util.Comparator;

/**
 * Created by liudmylaiterman on 3/3/18.
 */
public class StarDescComparator implements Comparator<Repository> {

    @Override
    public int compare(Repository repo1, Repository repo2) {
        return repo2.getStargazersCount() - repo1.getStargazersCount();
    }
}
