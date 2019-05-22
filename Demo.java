package demo.app;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import com.adyen.service.exception.ApiException;

/**
 * @author ZiOS07
 */

public class Demo {

	private static final String API_KEY = "myApiKey";

	public static void main(String[] args) throws ApiException, IOException {
		URL url = new URL("https://checkout-test.adyen.com/v41/paymentMethods");
		URLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestProperty("x-API-key", API_KEY);
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);

		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = getJSON().getBytes("utf-8");
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

	private static String getJSON() {
		return "{ \"merchantAccount\": \"YOUR_MERCHANT_ACCOUNT\", \"countryCode\": \"NL\", \"amount\": {   \"currency\": \"EUR\",   \"value\": 1000 }, \"channel\": \"Web\"}";
	}

}
