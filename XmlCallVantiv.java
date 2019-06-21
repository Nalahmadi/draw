package com.medicalcare;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlCallVantiv {

    public static final int ACCOUNT_ID = 1069860;

    public static void main(String[] args) {
        URL hostedPayurl;

        try {
            String url = "https://certtransaction.elementexpress.com";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml");
            String xml =
                    "<TransactionSetup xmlns=\"https://transaction.elementexpress.com\">\n" +
                            "    <Credentials>\n" +
                            "        <AccountID>"+ ACCOUNT_ID +"</AccountID>\n" +
                            "        <AccountToken>CDFA3C21D3546AB08AB5CBF43D3983DB2A44886D332B97E66E38F188B7EED32591E6F401</AccountToken>\n" +
                            "        <AcceptorID>3928907</AcceptorID>\n" +
                            "    </Credentials>\n" +
                            "    <Application>\n" +
                            "        <ApplicationID>10039</ApplicationID>\n" +
                            "        <ApplicationName>MyExpressTest</ApplicationName>\n" +
                            "        <ApplicationVersion>1.0.0</ApplicationVersion>\n" +
                            "    </Application>\n" +
                            "    <Terminal>\n" +
                            "        <TerminalID>01</TerminalID>\n" +
                            "        <CardholderPresentCode>2</CardholderPresentCode>\n" +
                            "        <CardInputCode>5</CardInputCode>\n" +
                            "        <TerminalCapabilityCode>3</TerminalCapabilityCode>\n" +
                            "        <TerminalEnvironmentCode>2</TerminalEnvironmentCode>\n" +
                            "        <CardPresentCode>2</CardPresentCode>\n" +
                            "        <MotoECICode>1</MotoECICode>\n" +
                            "        <CVVPresenceCode>1</CVVPresenceCode>\n" +
                            "    </Terminal>\n" +
                            "    <Transaction>\n" +
                            "        <TransactionAmount>7.55</TransactionAmount>\n" +
                            "    </Transaction>\n" +
                            "    <TransactionSetup>\n" +
                            "        <TransactionSetupMethod>1</TransactionSetupMethod>\n" +
                            "        <Embedded>1</Embedded>\n" +
                            "        <AutoReturn>1</AutoReturn>\n" +
                            "        <ReturnURL></ReturnURL>\n" +
                            "        <CustomCss>body{margin-left:50px;font-family:arial;font-size:large;border:none;}</CustomCss>\n" +
                            "    </TransactionSetup>\n" +
                            "</TransactionSetup>";
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xml);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            System.out.println(responseStatus);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer respons = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                respons.append(inputLine);
            }
            in.close();
            System.out.println("response: " + respons.toString());

            // parse(new InputSource(new StringReader(respons.toString())));

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(respons.toString()));
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            String TranSetupID = doc.getElementsByTagName("TransactionSetupID").item(0).getTextContent();
            String frameUrl = "https://certtransaction.hostedpayments.com/?TransactionSetupID=" + TranSetupID;
            hostedPayurl = new URL(frameUrl);
            URLConnection conn = hostedPayurl.openConnection();

            System.out.println(frameUrl);
        } catch (Exception e) {

        }

    }

}
