package htrc.security.oauth2.client;


import org.apache.amber.oauth2.client.HttpClient;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;

import java.util.HashMap;
import java.util.Map;

public class OAuth2Client extends OAuthClient {
    public OAuth2Client(HttpClient oauthClient) {
        super(oauthClient);
    }

    public OAuthUserInfoResponse userInfo(OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);

        return httpClient.execute(request, headers, OAuth.HttpMethod.POST, OAuthUserInfoResponse.class);
    }
}