package com.loadium.jenkins.loadium;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.loadium.jenkins.loadium.model.LoadiumTestBasicDetailsDTO;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.remoting.VirtualChannel;
import hudson.security.ACL;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jenkinsci.Symbol;
import com.loadium.jenkins.loadium.model.CredentialModel;
import com.loadium.jenkins.loadium.services.AuthService;
import com.loadium.jenkins.loadium.services.LoadiumService;
import com.loadium.jenkins.loadium.util.CredentialsUtil;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by furkanbrgl on 19/11/2017.
 */
public class LoadiumPerformBuilder extends Builder {

    private final String testId;
    private final String credentialsId;

    public final static AuthService authService = AuthService.getInstance();
    public final static LoadiumService loadiumService = LoadiumService.getInstance();

    private final static Logger LOGGER = Logger.getLogger(LoadiumPerformBuilder.class);

    @DataBoundConstructor
    public LoadiumPerformBuilder(String testId, String credentialsId) {
        this.testId = testId;
        this.credentialsId = credentialsId;
    }

    public String getTestId() {
        return testId;
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) {

        boolean isTokenValid;
        Result result = null;


        if (this.getTestId() == null || this.getTestId().equals("")) {

            listener.fatalError("Test key might be empty");
            LOGGER.error("Test Id is: " + this.getTestId());
            result = Result.FAILURE;
            build.setResult(result);
            return false;

        } else {

            try {

                CredentialModel credentialModel = CredentialsUtil.getCredentialByCredentialId(this.getCredentialsId());
                isTokenValid = authService.authTokenValidation(credentialModel.getUsername(), String.valueOf(credentialModel.getPassword()));

                if (isTokenValid) {

                    VirtualChannel c = launcher.getChannel();
                    LoadiumBuild loadiumBuild = new LoadiumBuild();
                    loadiumBuild.setCredentialModel(credentialModel);
                    loadiumBuild.setTestId(this.getTestId());
                    loadiumBuild.setListener(listener);

                    result = c.call(loadiumBuild);

                    build.setResult(result);
                    return true;

                } else {
                    listener.fatalError("Credentials are not valid ");
                    result = Result.NOT_BUILT;
                    build.setResult(result);
                    LOGGER.error("Credentials are not valid ");
                    return false;
                }

            } catch (InterruptedException e) {

                LOGGER.error(e.getLocalizedMessage());
                LOGGER.error(e.getMessage());

                result = Result.ABORTED;
                build.setResult(result);
                return true;

            } catch (Exception e) {

                LOGGER.error(e.getLocalizedMessage());
                LOGGER.error(e.getMessage());

                result = Result.FAILURE;
                build.setResult(result);
                return false;

            } finally {

            }
        }
    }

    @Override
    public Action getProjectAction(AbstractProject<?, ?> project) {
        return super.getProjectAction(project);
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return super.getRequiredMonitorService();
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Loadium Performance Test Runner Plugin";
        }

        public ListBoxModel doFillCredentialsIdItems(@QueryParameter("credentialsId") String credentialsId) {
            ListBoxModel items = new ListBoxModel();
            try {

                Item item = Stapler.getCurrentRequest().findAncestorObject(Item.class);
                for (LoadiumCredentials c : CredentialsProvider
                        .lookupCredentials(LoadiumCredentials.class, item, ACL.SYSTEM)) {
                    items.add(new ListBoxModel.Option(c.getDescription(),
                            c.getId(),
                            false));
                }
                Iterator<ListBoxModel.Option> iterator = items.iterator();


                while (iterator.hasNext()) {
                    ListBoxModel.Option option = iterator.next();
                    try {
                        option.selected = StringUtils.isBlank(credentialsId) || credentialsId.equals(option.value);
                    } catch (Exception e) {
                        option.selected = false;
                    }
                }


            } catch (NullPointerException npe) {

            } finally {
                return items;
            }
        }

        public ListBoxModel doFillTestIdItems(@QueryParameter("credentialsId") String crid,
                                              @QueryParameter("testId") String savedTestId) throws FormValidation {

            boolean isTokenValid;

            ListBoxModel items = new ListBoxModel();
            List<LoadiumCredentials> creds = CredentialsUtil.getCredentials(CredentialsScope.GLOBAL);
            LoadiumCredentials credential = null;

            if (StringUtils.isBlank(crid)) {
                if (creds.size() > 0) {
                    crid = creds.get(0).getId();
                } else {
                    items.add("No Credentials Added to Global Configuration", "-1");
                    return items;
                }
            }

            for (LoadiumCredentials c : creds) {
                if (c.getId().equals(crid)) {
                    credential = c;
                }
            }

            try {

                isTokenValid = authService.authTokenValidation(credential.getUsername(), Secret.toString(credential.getPassword()));
                if (!isTokenValid) {
                    items.add("User Validation Eror", "-1");
                    return items;
                }

                List<LoadiumTestBasicDetailsDTO> detailsDTOS = loadiumService.getTests();

                if (detailsDTOS == null) {
                    items.add("Credential is not valid", "-1");
                } else if (detailsDTOS.isEmpty()) {
                    items.add("No Test present for this credential", "-1");
                } else {
                    for (LoadiumTestBasicDetailsDTO testDto : detailsDTOS) {
                        items.add(new ListBoxModel.Option(testDto.getTestName(), testDto.getTestKey()));
                    }

                }
            } catch (Exception e) {
                throw FormValidation.error(e.getMessage(), e);
            }
            Comparator c = new Comparator<ListBoxModel.Option>() {
                @Override
                public int compare(ListBoxModel.Option o1, ListBoxModel.Option o2) {
                    return o1.name.compareToIgnoreCase(o2.name);
                }
            };

            Collections.sort(items, c);


            return items;
        }

    }
}
