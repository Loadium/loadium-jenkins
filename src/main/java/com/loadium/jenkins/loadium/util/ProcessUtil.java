package com.loadium.jenkins.loadium.util;

import com.loadium.jenkins.loadium.enums.LoadiumSessionStatus;
import com.loadium.jenkins.loadium.model.wrapper.JMeterRunningSessionResponse;
import com.loadium.jenkins.loadium.services.LoadiumService;
import hudson.FilePath;
import hudson.model.BuildListener;

import java.util.logging.Logger;

/**
 * Created by furkanbrgl on 17/11/2017.
 */
public class ProcessUtil {

    private final static int DELAY = 30000;

    private final static Logger LOGGER = Logger.getLogger(ProcessUtil.class.getName());

    private ProcessUtil() {
    }

    public static void sessionStartedProgress(String sessionKey,BuildListener loadiumLog) throws Exception {

        LoadiumSessionStatus sessionStatus;
        JMeterRunningSessionResponse jMeterRunningSessionResponse;

        while (true) {

            Thread.sleep(DELAY);
            jMeterRunningSessionResponse = LoadiumService.getInstance().getSessionStatus(sessionKey);
            sessionStatus = jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus();

            if (!sessionStatus.equals(LoadiumSessionStatus.STARTED)) {
                loadiumLog.getLogger().print("Loadium for session key " + sessionKey.substring(0,10) + " is finishing build... ");
                break;
            }

            if (Thread.interrupted()) {
                loadiumLog.getLogger().print("Job was stopped by user on Jenkins");
                throw new InterruptedException("Job was stopped by user on Jenkins");
            }
        }
    }

    public static void sessionEndedProgress(String sessionKey, String testKey, BuildListener loadiumLog) throws Exception {

        LoadiumSessionStatus sessionStatus;
        JMeterRunningSessionResponse jMeterRunningSessionResponse;

        try {
            LoadiumService.getInstance().stopSession(sessionKey, testKey);
        } catch (Exception e) {
            LOGGER.warning("An error has occurred while stopping session. Maybe your session was stopped on Loadium. Because of that we had HTTP/500 error. ");
        }

        while (true) {

            Thread.sleep(DELAY);
            jMeterRunningSessionResponse = LoadiumService.getInstance().getSessionStatus(sessionKey);
            sessionStatus = jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus();

            if (sessionStatus.equals(LoadiumSessionStatus.FINISHED)) {
                loadiumLog.getLogger().print("Loadium Session is being stopped on Loadium APi");
                break;
            }
        }
    }

    public static boolean stopMastersSession(String masterId) throws Exception {
        boolean terminate = true;
        //TODO: Stop Sesssion
        return terminate;
    }

    public static String getUserEmail() {
        //TODO: gets mail
        return "dummy@mail.com";
    }

    public static void saveReport(String reportName, String report, FilePath filePath, Logger loadiumLog) {
        //TODO: exports report
    }

}
