package lambda;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Example {

    public static class ExampleRequest {
        String hello;

        public String getHello() {
            return hello;
        }

        public void setHello(String hello) {
            this.hello = hello;
        }

        public ExampleRequest(String hello) {
            this.hello = hello;
        }

        public ExampleRequest() {
        }
    }

    public static class ExampleResponse {
        String hello;
        String token;

        public String getHello() {
            return hello;
        }

        public void setHello(String hello) {
            this.hello = hello;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public ExampleResponse(String hello, String token) {
            this.hello = hello;
            this.token = token;
        }

        public ExampleResponse() {
        }
    }

    public ExampleResponse handler(ExampleRequest event, Context context) {
        String token = "";
        try {
            token = (String) getProperty("LOGGLY_TOKEN");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new ExampleResponse(event.getHello(), token);
    }

    private Object getProperty(String key) throws IOException {
        Object result = null;
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "apex.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            result = prop.get(key);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return result;
    }
}
