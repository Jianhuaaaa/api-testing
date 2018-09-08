package cucumber.stepdefinition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import utils.PropertyReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jianhua Sun on 2018/9/8.
 */
public class StepDefinitions {
    private HttpHost host = new HttpHost(PropertyReader.read("hostname") + ":" + PropertyReader.read("port"));
    private RequestConfig requestConfig;
    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private Gson gson;
    private HttpPost request;
    private List<NameValuePair> params;

    @Given("^Customer bank details API$")
    public void customer_bank_details_API(){
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(15000)
                .setSocketTimeout(20000)
                .build();
        client = HttpClients.createDefault();
        gson = new GsonBuilder().create();
    }

    @When("^I prepared http post$")
    public void i_prepared_http_post(){
        request = new HttpPost(PropertyReader.read("post_url"));
        request.setConfig(requestConfig);
        request.setHeader("Context-type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        params = new ArrayList<NameValuePair>();
    }

    @Then("^I verify valid http response$")
    public void i_verify_valid_http_response(Map<String, String> data){
        try {
            data.keySet().stream().parallel().forEach(k -> {
                if (!k.equalsIgnoreCase("expect_response"))
                    params.add(new BasicNameValuePair(k, data.get(k)));
            });
            request.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(host, request);
            String responseString = EntityUtils.toString(response.getEntity());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            Assert.assertTrue(responseString.contains(data.get("expect_response")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("^I verify invalid http response$")
    public void i_verify_invalid_http_response(Map<String, String> data){
        try {
            data.keySet().stream().parallel().forEach(k -> {
                if (!k.equalsIgnoreCase("expect_response"))
                    params.add(new BasicNameValuePair(k, data.get(k)));
            });
            request.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(host, request);
            String responseString = EntityUtils.toString(response.getEntity());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
            Assert.assertTrue(responseString.contains(data.get("expect_response")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
