package com.loadium.jenkins.loadium;

import com.loadium.jenkins.loadium.services.AuthService;
import com.loadium.jenkins.loadium.util.RestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by furkanbrgl on 23/11/2017.
 */

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(AuthService.class)
public class AuthServiceOperationTest {
/*
    @Mock
    RestUtil restUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    public void authTokenValidationTest() throws Exception {

        String userName = "furkanbrgl";
        String password = "Admin";
        String tokenFileName = "token.txt";

        String tempJson = TestRequirement.readToResourceFile(tokenFileName);

        when(restUtil.getAuthToken(Mockito.anyString(), Mockito.anyString())).thenReturn(tempJson);

        PowerMockito.whenNew(RestUtil.class).withNoArguments().thenReturn(restUtil);

        boolean isValid = authService.authTokenValidation(userName, password);

        Assert.assertTrue(isValid);


    }
*/
}
