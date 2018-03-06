import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lucy_it.sandbox.github.model.Contributor;
import lucy_it.sandbox.github.model.Repository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by liudmylaiterman on 3/4/18.
 */
public class QueryUtils {
    public static String queryHttp(final String host, final String protocol, final int port, final String path) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = null;
            HttpHost target = new HttpHost(host, port, protocol);
            HttpGet getRequest = new HttpGet(path);

            boolean sucess = false;
            int counter = 0;
            while (!sucess || counter > 10) {
                counter++;
                httpResponse = httpclient.execute(target, getRequest);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                switch (statusCode) {
                    case 200: {
                        sucess = true;
                        break;
                    }
                    case 202: {
                        System.out.println("Status Code = 202. Will retry in 5s");
                        Thread.sleep(10000);
                        EntityUtils.consumeQuietly(httpResponse.getEntity());
                        break;
                    }
                    default: {
                        throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                    }
                }
            }

            HttpEntity entity = httpResponse.getEntity();

            return EntityUtils.toString(entity);

        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            httpclient.close();
        }

    }


    public static List<Repository> jsonToRepos(String json) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, new TypeReference<List<Repository>>() {});
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

    public static Set<Contributor> getUniqueContributors(List<Repository> repos){
        Set<Contributor> uniqueContr = new HashSet<Contributor>();

        for (Repository repo : repos){
            String path = String.format("/repos/%s/%s/stats/contributors", repo.getOwner().getLogin(), repo.getName());
            String jsonContrib = QueryUtils.queryHttp("api.github.com", "https", 443, path);
            List<Contributor> contributors = QueryUtils.jsonToContributors(jsonContrib);
            uniqueContr.addAll(contributors);
        }

        return uniqueContr;
    }
}
