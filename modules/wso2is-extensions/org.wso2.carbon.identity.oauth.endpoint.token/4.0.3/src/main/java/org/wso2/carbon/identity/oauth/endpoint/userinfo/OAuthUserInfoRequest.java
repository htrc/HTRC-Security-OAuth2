package org.wso2.carbon.identity.oauth.endpoint.userinfo;


import org.apache.amber.oauth2.as.request.OAuthRequest;
import org.apache.amber.oauth2.as.validator.ClientCredentialValidator;
import org.apache.amber.oauth2.as.validator.PasswordValidator;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.common.validators.AbstractValidator;
import org.apache.amber.oauth2.common.validators.OAuthValidator;

import javax.servlet.http.HttpServletRequest;

public class OAuthUserInfoRequest extends OAuthRequest{

    public OAuthUserInfoRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }

    @Override
    protected OAuthValidator<HttpServletRequest> initValidator() throws OAuthProblemException, OAuthSystemException {
        return new UserInfoRequestValidator();
    }

    public String getAccessToken() {
         return getParam(OAuth.OAUTH_BEARER_TOKEN);
    }

    public class UserInfoRequestValidator extends AbstractValidator{
        public UserInfoRequestValidator(){
            requiredParams.add(OAuth.OAUTH_ACCESS_TOKEN);
            requiredParams.add(OAuth.OAUTH_CLIENT_ID);
            requiredParams.add(OAuth.OAUTH_CLIENT_SECRET);
        }
    }
}
