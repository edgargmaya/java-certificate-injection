package com.edgar.development;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
//import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

//import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Configuration
public class RestTemplateConfig {
	
	@Value("${hsbc.certificado}")
    private String certificado;
	
	@Bean
    public RestTemplate restTemplate() throws Exception {
        
		System.out.println(certificado);
		byte[] certBytes = Base64.getDecoder().decode(certificado);
		
		InputStream certStream = new ByteArrayInputStream(certBytes);

//		InputStream is = getClass().getClassLoader().getResourceAsStream("server.crt");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate caCert = (X509Certificate) cf.generateCertificate(certStream);

		// Crear un KeyStore y cargar el certificado
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(null, null);
		keyStore.setCertificateEntry("mycert", caCert);
		
//		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        try (FileInputStream fis = new FileInputStream("newKeyStore.jks")) {
//            keyStore.load(fis, "password".toCharArray());
//        }
        
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
                }
            }
        };

        return new RestTemplate(requestFactory);
    }
}
