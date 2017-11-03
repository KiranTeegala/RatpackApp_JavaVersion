package com.smartapp.api.test;

import com.smartapp.api.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.MainClassApplicationUnderTest;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SmartAppAPITest {

    MainClassApplicationUnderTest aut = new MainClassApplicationUnderTest(Main.class);

    @Test
    public void testGetLevel() {
        int statusCode = aut.getHttpClient().get("getLevel").getStatusCode();
        assertEquals(200,statusCode);
    }

    @Test
    public void testSetLevel() {
        ReceivedResponse response = aut.getHttpClient().request("setLevel", requestSpec -> {
            requestSpec.method("POST");
            requestSpec.getBody().text("{\"level\":\"39\"}");
        });
        System.out.println(response.getBody().getText());
        assertEquals(200,response.getStatusCode());
    }

}
