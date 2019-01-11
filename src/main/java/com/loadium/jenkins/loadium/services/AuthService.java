package com.loadium.jenkins.loadium.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.log4j.Logger;
import com.loadium.jenkins.loadium.util.RestUtil;

/**
 * Created by furkanbrgl on 13/11/2017.
 */
@SuppressFBWarnings("MS_PKGPROTECT")
public class AuthService {

    public String username;
    public String password;
    public static String accessToken;

    private final static Logger LOGGER = Logger.getLogger(AuthService.class);

    private static AuthService instance;
    private RestUtil rest;


    protected AuthService() {
    }

    @SuppressFBWarnings("LI_LAZY_INIT_STATIC")
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public String getAuthToken() throws Exception {

        rest = new RestUtil();
        setToken(rest.getAuthToken(this.username, this.password));

        return accessToken;
    }

    private synchronized static void setToken(String token) {
        accessToken = token;
    }

    public boolean authTokenValidation(String username, String password) {

        try {

            this.setCredentials(username, password);
            String accessToken = this.getAuthToken();

            if (accessToken == null || accessToken.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            LOGGER.error("Token Valiradion ERROR" + e.getMessage() + " --- " + e.toString());
            return false;
        }


    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
