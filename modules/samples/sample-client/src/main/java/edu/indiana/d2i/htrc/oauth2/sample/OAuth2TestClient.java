package edu.indiana.d2i.htrc.oauth2.sample;

import htrc.security.oauth2.client.OAuth2Client;
import htrc.security.oauth2.client.OAuthUserInfoRequest;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.commons.cli.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OAuth2TestClient {

    public static void main(String[] args) throws IOException {
        Options options = new Options();
        options.addOption("c", true, "Configuration file.");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("c")) {
                OAuth2TestClient testClient = new OAuth2TestClient();
                testClient.runTest(cmd.getOptionValue("c"));
            } else {
                HelpFormatter hf = new HelpFormatter();
                hf.printHelp("OAuth2 Sample Client", options);
            }
        } catch (ParseException e) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("OAuth2 Sample Client", options);
        }

    }

    public void runTest(String configFilePath) throws IOException {
        Configuration config = buildConfigurationFromPropertiesFile(configFilePath);
        // TODO: Fill the test logic.
    }

    private String invokeResourceWebApp(String accessToken) throws IOException {
        URL url = new URL("http://localhost:8080/resource-webapp/hello");
        URLConnection c = url.openConnection();
        c.addRequestProperty("Authorization", "Bearer " + accessToken);

        HttpURLConnection httpURLConnection = (HttpURLConnection) c;
        httpURLConnection.setRequestMethod("GET");

        InputStream inputStream;
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == 400) {
            inputStream = httpURLConnection.getErrorStream();
        } else if (responseCode == 401) {
            inputStream = httpURLConnection.getErrorStream();

        } else {

            inputStream = httpURLConnection.getInputStream();
        }
        String responseBody = OAuthUtils.saveStreamAsString(inputStream);

        return responseBody;
    }

    private boolean isValidResponse(String response) {
        if (response.contains("expired_token") || response.contains("invalid_token")) {
            return false;
        }

        return true;
    }

    private List<String> getToken(GrantType grantType, Configuration config) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest accessTokenRequest = null;
        List<String> ret = new ArrayList<String>();

        if (grantType == GrantType.CLIENT_CREDENTIALS) {
            accessTokenRequest = OAuthClientRequest
                    .tokenLocation(config.getAccessTokenURL())
                    .setGrantType(grantType)
                    .setClientId(config.getClientId())
                    .setClientSecret(config.getClientSecret())
                    .buildBodyMessage();
        } else if (grantType == GrantType.PASSWORD) {
            accessTokenRequest = OAuthClientRequest
                    .tokenLocation(config.getAccessTokenURL())
                    .setGrantType(grantType)
                    .setClientId(config.getClientId())
                    .setClientSecret(config.getClientSecret())
                    .setUsername(config.getUserName())
                    .setPassword(config.getUserPassword())
                    .buildBodyMessage();
        } else {
            throw new RuntimeException("Unsupported grant type.");
        }


        OAuthClient accessTokenClient = new OAuthClient(new URLConnectionClient());
        OAuthClientResponse accessTokenResponse = accessTokenClient.accessToken(accessTokenRequest);

        ret.add(0, accessTokenResponse.getParam("access_token"));
        ret.add(1, accessTokenResponse.getParam("refresh_token"));

        return ret;
    }

    private List<String> getTokenFromRefreshToken(String refreshToken, Configuration config) throws OAuthSystemException, OAuthProblemException {
        List<String> ret = new ArrayList<String>();
        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(config.getAccessTokenURL())
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(config.getClientId())
                .setClientSecret(config.getClientSecret())
                .setRefreshToken(refreshToken)
                .buildBodyMessage();

        OAuthClient accessTokenClient = new OAuthClient(new URLConnectionClient());
        OAuthClientResponse accessTokenResponse = accessTokenClient.accessToken(accessTokenRequest);

        ret.add(0, accessTokenResponse.getParam("access_token"));
        ret.add(1, accessTokenResponse.getParam("refresh_token"));

        return ret;
    }

    public static OAuthClientResponse getUserInformation(String accessToken, boolean isTokenValid, GrantType grantType, Configuration config) throws OAuthSystemException, OAuthProblemException {
        if (isTokenValid && grantType != GrantType.CLIENT_CREDENTIALS) {

            OAuthClientRequest userInfoRequest = OAuthUserInfoRequest
                    .userInfoLocation(config.getUserInfoURL())
                    .setClientId(config.getClientId())
                    .setClientSecret(config.getClientSecret())
                    .setAccessToken(accessToken)
                    .buildBodyMessage();

            OAuth2Client userInfoClient = new OAuth2Client(new URLConnectionClient());
            OAuthClientResponse userInfoResponse = userInfoClient.userInfo(userInfoRequest);

            System.out.println("Autherized User : " + userInfoResponse.getParam("authorized_user"));
            System.out.println("User Full Name : " + userInfoResponse.getParam("user_fullname"));
            System.out.println("User Email : " + userInfoResponse.getParam("user_email"));

            return userInfoResponse;
        }

        return null;
    }


    private void trustAllCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc;
        // Get SSL context
        sc = SSLContext.getInstance("SSL");
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                           String authType) {
            }
        }};

        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        SSLSocketFactory sslSocketFactory = sc.getSocketFactory();

        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private Configuration buildConfigurationFromPropertiesFile(String configFilePath) throws IOException {
        File configFile = new File(configFilePath);
        Configuration configuration = new Configuration();
        if (configFile.exists()) {
            Properties props = new Properties();
            props.load(new FileInputStream(configFile));

            configuration.setAccessTokenURL(props.getProperty("oauth2.accesstoken.url"));
            configuration.setUserInfoURL(props.getProperty("oauth2.userinfo.url"));
            configuration.setClientId(props.getProperty("oauth2.clientid"));
            configuration.setClientSecret(props.getProperty("oauth2.clientsecret"));
            configuration.setProduction(isProduction(props.getProperty("oauth2.isproduction")));
            configuration.setUserName(props.getProperty("oauth2.username"));
            configuration.setUserPassword(props.getProperty("oauth2.userpassword"));

            return configuration;
        }

        throw new RuntimeException("Cannot find client configuration file.");
    }

    private boolean isProduction(String bool) {
        if (bool.trim().toLowerCase().equals("true")) {
            return true;
        }

        return false;
    }
}
