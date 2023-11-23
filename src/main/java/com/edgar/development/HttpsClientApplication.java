package com.edgar.development;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.InputStream;
//import java.net.URL;

import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

//import javax.net.ssl.TrustManagerFactory;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;

@SpringBootApplication
public class HttpsClientApplication {

	public static void main(String[] args) throws Exception {
		
//		HttpsClientApplication app = new HttpsClientApplication();
//		app.createJKS();
		
		SpringApplication.run(HttpsClientApplication.class, args);
		
//		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        try (FileInputStream fis = new FileInputStream("newKeyStore.jks")) {
//            keyStore.load(fis, "password".toCharArray());
//        }
//        
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(keyStore);
//		
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);
//        
//		try {
//            URL url = new URL("https://hsbc.local:8443");
//            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//            con.setSSLSocketFactory(sslContext.getSocketFactory());
//            
//            con.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		
	}
	
	public void createJKS() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("server.crt");

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("alias", cert);

        try (FileOutputStream fos = new FileOutputStream("newKeyStore.jks")) {
            keyStore.store(fos, "password".toCharArray());
        }
	}

}
