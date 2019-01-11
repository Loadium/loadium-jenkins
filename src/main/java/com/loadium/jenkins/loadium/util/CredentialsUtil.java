package com.loadium.jenkins.loadium.util;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.CredentialsScope;
import hudson.model.Item;
import hudson.security.ACL;
import com.loadium.jenkins.loadium.LoadiumCredentials;
import com.loadium.jenkins.loadium.model.CredentialModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by furkanbrgl on 16/11/2017.
 */
public class CredentialsUtil {

    /**
     * Generally used CredentialsScope.GLOBAL scope
     *
     * @param scope
     * @return
     */
    public static List<LoadiumCredentials> getCredentials(Object scope) {

        List<LoadiumCredentials> result = new ArrayList<LoadiumCredentials>();
       // Set<String> addedCredentials = new HashSet<>();

        Item item = scope instanceof Item ? (Item) scope : null;
      //  StringBuilder id = new StringBuilder();

        for (LoadiumCredentials c : CredentialsProvider.lookupCredentials(LoadiumCredentials.class, item, ACL.SYSTEM)) {
        //    id.append(c.getId());
            result.add(c);
        //    addedCredentials.add(id.toString());
         //   id.setLength(0);
        }

        return result;
    }


    public static CredentialModel getCredentialByCredentialId(String credentialId) throws Exception {

        LoadiumCredentials credential = null;
        List<LoadiumCredentials> creds = getCredentials(CredentialsScope.GLOBAL);

        for (LoadiumCredentials c : creds) {

            if (c.getId().equals(credentialId)) {
                credential = c;
            }
        }
        if (credential != null) {
            return new CredentialModel(credential.getUsername(), credential.getPassword());
        } else {
            throw new Exception("CredentialsId is not found");
        }
    }

}
