package com.loadium.jenkins.loadium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import com.loadium.jenkins.loadium.util.EnviromentUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by furkanbrgl on 23/11/2017.
 */
public class EnviromentTest {

    private EnviromentUtil enviromentUtil = EnviromentUtil.getInstance();
    private final static Logger LOG = Logger.getLogger(EnviromentTest.class);

    @Before
    public void before() {
        BasicConfigurator.configure();
    }

    @Ignore
    @Test
    public void EnviromentTest() {

        LOG.info("########");

        Assert.assertEquals("Basic dGVzdGluaXVtU3VpdGVUcnVzdGVkQ2xpZW50OnRlc3Rpbml1bVN1aXRlU2VjcmV0S2V5", enviromentUtil.getAuthorization());
        Assert.assertEquals("http://localhost:4202", enviromentUtil.getUiApiPublic());
        Assert.assertEquals("http://localhost:9002/watchman/api/v1", enviromentUtil.getWatchmanBaseURL());

        LOG.info("########");
    }

}