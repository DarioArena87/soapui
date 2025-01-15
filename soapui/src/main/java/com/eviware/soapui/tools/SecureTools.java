package com.eviware.soapui.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class SecureTools {
    private final static String SSL_CONTEXT_INSTANCE_NAME = "SSL";
    private final static Logger logger = LogManager.getLogger(SecureTools.class);

    public static void setTrustSSL() {
        TrustManager[] trustAllCerts;
        trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public void checkClientTrusted(
                    X509Certificate[] certs, String authType
                ) {
                }

                public void checkServerTrusted(
                    X509Certificate[] certs, String authType
                ) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance(SSL_CONTEXT_INSTANCE_NAME);
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (GeneralSecurityException generalSecurityException) {
            logger.error(generalSecurityException.getMessage());
        }
    }
}
