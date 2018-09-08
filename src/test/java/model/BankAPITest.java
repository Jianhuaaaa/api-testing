package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelReader;
import utils.PropertyReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jianhua Sun on 2018/9/4.
 */
public class BankAPITest {

    private HttpHost host = new HttpHost(PropertyReader.read("hostname") + ":" + PropertyReader.read("port"));
    private RequestConfig requestConfig;
    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private Gson gson;
    private HttpPost request;
    private List<NameValuePair> params;

    @BeforeClass
    public void beforeClass() {
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(15000)
                .setSocketTimeout(20000)
                .build();
        client = HttpClients.createDefault();
        gson = new GsonBuilder().create();
    }

    @BeforeMethod
    public void beforeMethod() {
        request = new HttpPost(PropertyReader.read("post_url"));
        request.setConfig(requestConfig);
        request.setHeader("Context-type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        params = new ArrayList<NameValuePair>();
    }

    @Test(dataProvider = "valid_data")
    public void validBankDetailTest(int rowNumber, Map<String, String> data) {
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

    @Test(dataProvider = "invalid_data")
    public void invalidBankDetailsTest(int rowNumber, Map<String, String> data) {
        try {
            data.keySet().stream().parallel().forEach(k -> {
                if (!k.equalsIgnoreCase("expect_response"))
                    params.add(new BasicNameValuePair(k, data.get(k)));
            });
            request.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(host, request);
            String responseString = EntityUtils.toString(response.getEntity());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);
            Assert.assertTrue(responseString.contains(data.get("expect_response")), "expected response: " + data.get("expect_response") + ", actual response: " + responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "valid_data")
    public Object[][] validData() {
        String filePath = BankAPITest.class.getClassLoader().getResource("bank_test_data.xls").getPath();
        return new ExcelReader(filePath, "valid").readTestData();
    }

    @DataProvider(name = "invalid_data")
    public Object[][] invalidData() {
        String filePath = BankAPITest.class.getClassLoader().getResource("bank_test_data.xls").getPath();
        return new ExcelReader(filePath, "invalid").readTestData();
    }

    @AfterMethod
    public void afterMethod() {
    }

    @AfterClass
    public void afterClass() {
    }
}
