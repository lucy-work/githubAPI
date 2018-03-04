import java.util.Comparator;

/**
 * Created by liudmylaiterman on 3/3/18.
 */
public class ForksDescComparator implements Comparator<Repository> {

    @Override
    public int compare(Repository repo1, Repository repo2) {
        return repo2.getForksCount() - repo1.getForksCount();
    }
}
