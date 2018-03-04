import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;


/**
 * Created by liudmylaiterman on 3/3/18.
 */
public class HTTPrequest {
    public static String queryHttp(final String host, final String protocol, final int port, final String path) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            // specify the host, protocol, and port
            HttpHost target = new HttpHost(host, port, protocol);

            // specify the get request
            HttpGet getRequest = new HttpGet(path);

            HttpResponse httpResponse = httpclient.execute(target, getRequest);

            //verify the valid error code
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            //pull back the response object
            HttpEntity entity = httpResponse.getEntity();

            return EntityUtils.toString(entity);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public static List<Repository> jsonToRepos(String json) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, new TypeReference<List<Repository>> () {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Contributor> jsonToContributors(String json) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, new TypeReference<List<Contributor>> () {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Repository> sort(List<Repository> repos, Comparator<Repository> comparator) {
        Collections.sort(repos, comparator);
        return repos;
    }

    public static List<Repository> topN(List<Repository> repos, int n) {
        return repos.subList(0, n);
    }

    public static HashSet<Contributor> getContribList(List<Contributor> contributerses){
        HashSet<Contributor> uniqueContr = new HashSet<Contributor>();
        for (Contributor contr : contributerses) {
            uniqueContr.add(contr);
        }
        return uniqueContr;
    }

    public static void main(String[] args) throws IOException {
        String jsonRepo = queryHttp("api.github.com", "https", 443, "/orgs/meetup/repos");
        List<Repository> repos = jsonToRepos(jsonRepo);

        List<Repository> top3StarRepos = topN(sort(repos, new StarDescComparator()),3);
//        System.out.println(topN(sort(repos, new WatchersDescComparator()),3));
//        System.out.println(topN(sort(repos, new ForksDescComparator()),3));


        for (Repository repo : top3StarRepos){
            String path = String.format("/repos/%s/%s/stats/contributors", repo.getOwner().getLogin(), repo.getName());
            String jsonContrib = queryHttp("api.github.com", "https", 443, path);
            List<Contributor> contributors = jsonToContributors(jsonContrib);
            System.out.println(contributors);
        }


    }


}
