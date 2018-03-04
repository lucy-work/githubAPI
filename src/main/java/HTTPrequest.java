import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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


    public static List<Repository> convertJson(String json){
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<Repository> repos = mapper.readValue(json, new TypeReference<List<Repository>> () {});
            return repos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Repository> sort(List<Repository> repos, Comparator<Repository> comparator) {
        Collections.sort(repos, comparator);
        return repos;
    }

    public static void main(String[] args) throws IOException {
        String json = queryHttp("api.github.com", "https", 443, "/orgs/meetup/repos");
        List<Repository> repos = convertJson(json);

        List<Repository> sorted = sort(repos, new StarDescComparator());
        System.out.println(sorted);
    }


}
