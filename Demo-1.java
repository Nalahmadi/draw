import com.adyen.service.exception.ApiException;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author ZiOS07
 */

public class Demo {

    private static final String API_KEY = "myApiKey";

    public static void main(String[] args) throws ApiException, IOException {
        URL url = new URL("https://checkout-test.adyen.com/v41/paymentMethods");
        URLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestProperty("x-API-key", API_KEY);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = getJSON().toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        connection.connect();

        String result;
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result2 = bis.read();
        while (result2 != -1) {
            buf.write((byte) result2);
            result2 = bis.read();
        }
        result = buf.toString();
        System.out.println(result);
    }


    private static JSONObject getJSON() {
        JSONObject amount = new JSONObject();
        JSONObject parent = new JSONObject();
        amount.put("currency", "EUR");
        amount.put("value", 1000);
        parent.put("merchantAccount", "YOUR_MERCHANT_ACCOUNT");
        parent.put("countryCode", "NL");
        parent.put("channel", "web");
        parent.put("amount", amount);
        return parent;
    }

}
