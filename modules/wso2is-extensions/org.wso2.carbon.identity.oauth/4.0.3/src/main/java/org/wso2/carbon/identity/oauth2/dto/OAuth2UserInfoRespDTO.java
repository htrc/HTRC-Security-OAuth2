package org.wso2.carbon.identity.oauth2.dto;

/**
 * Created with IntelliJ IDEA.
 * User: samitha
 * Date: 12/24/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class OAuth2UserInfoRespDTO {

    private String authorizedUser;

    private String errorMsg;


    public String getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(String authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
