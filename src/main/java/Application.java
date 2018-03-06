import lucy_it.sandbox.github.comparator.ForksDescComparator;
import lucy_it.sandbox.github.comparator.StarDescComparator;
import lucy_it.sandbox.github.comparator.WatchersDescComparator;
import lucy_it.sandbox.github.model.Contributor;
import lucy_it.sandbox.github.model.Repository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;


/**
 * Created by liudmylaiterman on 3/3/18.
 */
public class Application {

    public static void reposPrint(List<Repository> repos, String creterion, Comparator<Repository> comparator) {
        System.out.printf("Top 3 repositories sorted by %s count%n", creterion);
        System.out.println("--------------------------------------");
        for (Repository repo : QueryUtils.topN(QueryUtils.sort(repos, comparator),3)) {
            System.out.println("id: " + repo.getId() + ", name: " + repo.getName());
        }
        System.out.println("");

    }
    public static void main(String[] args) throws IOException {
        String jsonRepo = QueryUtils.queryHttp("api.github.com", "https", 443, "/orgs/meetup/repos");
        List<Repository> repos = QueryUtils.jsonToRepos(jsonRepo);

        reposPrint(repos, "stargazer", new StarDescComparator());
        reposPrint(repos, "watchers", new WatchersDescComparator());
        reposPrint(repos, "forks", new ForksDescComparator());

        System.out.println(QueryUtils.getUniqueContributors(repos));


    }


}
