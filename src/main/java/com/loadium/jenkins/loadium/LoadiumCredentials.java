package com.loadium.jenkins.loadium;

import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.impl.BaseStandardCredentials;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.util.Secret;
import net.sf.json.JSONException;
import com.loadium.jenkins.loadium.services.AuthService;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.annotation.CheckForNull;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by sahabt on 04/11/2017.
 */
public class LoadiumCredentials  extends BaseStandardCredentials implements StandardUsernamePasswordCredentials {

    public static LoadiumCredentials EMPTY = new LoadiumCredentials(CredentialsScope.GLOBAL, "","","", "");

    private String username =null;
    private Secret password=null;

    @DataBoundConstructor
    public LoadiumCredentials(@CheckForNull CredentialsScope scope,
                              @CheckForNull String id, @CheckForNull String description,
                              @CheckForNull String username, @CheckForNull String password) {
        super(scope,id,description);
        this.username=username;
        this.password=Secret.fromString(password);;
    }

    @NonNull
    @Override
    public Secret getPassword() {
        return password;
    }


    @NonNull
    @Override
    public String getUsername() {
        return username;
    }


    @Extension(ordinal = 1)
    public static class DescriptorImpl extends BaseStandardCredentialsDescriptor {

        private AuthService authService = AuthService.getInstance();

        @Override
        public String getDisplayName() {
            return "Loadium Credentials";
        }

        public FormValidation doTestConnection(@QueryParameter("username") final String username, @QueryParameter("password") final String password)
                throws MessagingException, IOException, JSONException, ServletException {

            authService.setCredentials(username, password);

            try {
                authService.getAuthToken();
                return FormValidation.ok("Connection to Loadium Server is succesfull");
            } catch (Exception e) {
                return FormValidation.error("Client error : Username or Password is invalid.");
            }
        }

    }
}
