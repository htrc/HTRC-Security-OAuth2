package htrc.security.oauth2.client;

import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.client.validator.OAuthClientValidator;
import org.apache.amber.oauth2.client.validator.TokenValidator;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.utils.JSONUtils;
import org.codehaus.jettison.json.JSONException;

public class OAuthUserInfoResponse extends OAuthClientResponse{
    @Override
    protected void setBody(String s) throws OAuthProblemException {
        this.body = s;
        System.out.println(s);
        try {
            this.parameters = JSONUtils.parseJSON(s);
        } catch (JSONException e) {
            System.out.println(s);
            throw OAuthProblemException.error(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
                    "Invalid response! Response body is not " + OAuth.ContentType.JSON + " encoded");
        }
    }

    @Override
    protected void setContentType(String s) {
        this.contentType = s;
    }

    @Override
    protected void setResponseCode(int i) {
        this.responseCode = i;
    }

    public String getUser(){
        return (String)parameters.get("registered_user");
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        validator = new UserInfoValidator();
        super.init(body, contentType, responseCode);
    }

    public class UserInfoValidator extends OAuthClientValidator{
        public UserInfoValidator(){
        }
    }
}
