package com.loadium.jenkins.loadium.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by furkanbrgl on 14/11/2017.
 */
public class EnviromentUtil {

    private final static String propertyName = "enviroment.properties";
    private final static Logger LOGGER = Logger.getLogger(RestUtil.class);

    private static EnviromentUtil instance = new EnviromentUtil();
    public static EnviromentUtil getInstance() {
        return instance;
    }

    private EnviromentUtil() {
        this.loadToSystemFile();
    }

    private void loadToSystemFile() {

        String propertyFile = this.propertyName;
        Properties properties = System.getProperties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(propertyFile));
            LOGGER.info("Enviroment properties loaded to System");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResourceBaseURL() {
        return System.getProperty("resource.base.url");
    }

    public String getWatchmanBaseURL() {
        return System.getProperty("watchman.base.url");
    }

    public String getAuthorization() {
        return System.getProperty("authorization");
    }

    public String getGrantType() {
        return System.getProperty("grant.type");
    }

    public String getScope() {
        return System.getProperty("scope");
    }

    public String getAuthServerTokenURL() {
        return System.getProperty("auth.server.token.url");
    }

    public String getUiApiPublic() {
        return System.getProperty("ui.api.public");
    }

    public String getPublicReportURL(String testKey, String sessionKey) {
        return this.getUiApiPublic() + "/test/public-report/" + testKey + "/session/" + sessionKey;
    }
}