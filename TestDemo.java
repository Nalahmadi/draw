import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.net.www.http.PosterOutputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.cert.Certificate;

public class TestDemo {

    private static final String JSON_PATH = "AdyenPaymentResponse.json";

    @BeforeClass
    public static void setup() {
        URL.setURLStreamHandlerFactory(protocol -> "https".equals(protocol) ? new URLStreamHandler() {
            protected URLConnection openConnection(URL url) throws IOException {
                return new HttpsURLConnection(url) {

                    @Override
                    public void disconnect() {

                    }

                    @Override
                    public boolean usingProxy() {
                        return false;
                    }

                    @Override
                    public String getCipherSuite() {
                        return null;
                    }

                    @Override
                    public Certificate[] getLocalCertificates() {
                        return new Certificate[0];
                    }

                    @Override
                    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
                        return new Certificate[0];
                    }

                    @Override
                    public OutputStream getOutputStream() throws IOException {
                        return new PosterOutputStream();
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return Demo.class.getResourceAsStream(JSON_PATH);
                    }


                    public void connect() throws IOException {
                    }
                };
            }
        } : null);
    }

    @Test
    public void getMockUrl() throws IOException {

        URL url = new URL("https://checkout-test.adyen.com/v41/paymentMethods");
        URLConnection connection = url.openConnection();

        String result = Demo.createPaymentTransaction();

        Assert.assertTrue(result.contains("SaleToPOIRequest"));
        Assert.assertEquals(1l, 1l);
    }


}
