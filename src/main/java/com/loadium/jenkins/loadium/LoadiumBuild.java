package com.loadium.jenkins.loadium;

import com.loadium.jenkins.loadium.enums.LoadiumSessionStatus;
import com.loadium.jenkins.loadium.model.CredentialModel;
import com.loadium.jenkins.loadium.model.wrapper.JMeterRunningSessionResponse;
import com.loadium.jenkins.loadium.model.wrapper.StartSessionResponse;
import com.loadium.jenkins.loadium.services.LoadiumService;
import com.loadium.jenkins.loadium.util.EnviromentUtil;
import com.loadium.jenkins.loadium.util.ProcessUtil;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.remoting.Callable;
//import org.eclipse.jetty.util.log.StdErrLog;
import org.jenkinsci.remoting.RoleChecker;

import java.io.PrintStream;
import java.util.Calendar;

/**
 * Created by furkanbrgl on 16/11/2017.
 */
//TODO: we have to generate information-log for more detail executing too (log file is located internal path).
public class LoadiumBuild implements Callable<Result, Exception> {


    private CredentialModel credentialModel;
    private String testId = "";
    private BuildListener listener = null;
    public final static LoadiumService loadiumService = LoadiumService.getInstance();

    private final static int DELAY = 30000;
    private final static int LastPrint = 0;


    @Override
    public Result call() throws Exception, InterruptedException {

        String sessionKey = null;
        String publicReportURL = null;
        JMeterRunningSessionResponse jMeterRunningSessionResponse;

        Result result = Result.SUCCESS;

        StringBuilder lentry = new StringBuilder();
        PrintStream consoleLogger = this.listener.getLogger();


        lentry.append("Token acquired for user : " + credentialModel.getUsername());
        this.listener.getLogger().println(lentry.toString());
        lentry.setLength(LastPrint);
        lentry.append("Timestamp: " + Calendar.getInstance().getTime());
        lentry.setLength(LastPrint);

        try {

            StartSessionResponse startSessionResponse = loadiumService.startSession(this.getTestId());

            sessionKey = startSessionResponse.getSession().getSessionKey();

            lentry.append("Running Session Key : " + sessionKey.substring(0, 10));
            this.listener.getLogger().println(lentry.toString());
            lentry.setLength(LastPrint);
            lentry.append("Loadium test report will be available ...");
            this.listener.getLogger().println(lentry.toString());
            lentry.setLength(LastPrint);

            while (true) {

                Thread.sleep(DELAY);
                jMeterRunningSessionResponse = loadiumService.getSessionStatus(sessionKey);

                if (jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus().equals(LoadiumSessionStatus.STARTED)) {
                    publicReportURL = EnviromentUtil.getInstance().getPublicReportURL(this.getTestId(), sessionKey);
                    lentry.append("Loadium Test Report :  " + publicReportURL);
                    this.listener.getLogger().println(lentry.toString());
                    lentry.setLength(LastPrint);
                    break;
                }

                if (jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus().equals(LoadiumSessionStatus.FAILED)) {
                    result = Result.FAILURE;
                    break;
                }

                if (jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus().equals(LoadiumSessionStatus.FINISHED)) {
                    publicReportURL = EnviromentUtil.getInstance().getPublicReportURL(this.getTestId(), sessionKey);
                    lentry.append("Loadium Test Report :  " + publicReportURL);
                    this.listener.getLogger().println(lentry.toString());
                    lentry.setLength(LastPrint);
                    result = Result.SUCCESS;
                    break;
                }


                if (Thread.interrupted()) {
                    throw new InterruptedException("Job was stopped by user on Jenkins");
                }

            }

//            EnvVars.masterEnvVars.put(this.getTestId() + "-" + sessionKey, publicReportURL);

            ProcessUtil.sessionStartedProgress(sessionKey, this.listener);

            jMeterRunningSessionResponse = loadiumService.getSessionStatus(sessionKey);
            if (jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus().equals(LoadiumSessionStatus.FAILED)) {
                result = Result.FAILURE;
            }

            if (jMeterRunningSessionResponse.getLoadiumSessionBasicDetailsDTO().getSessionStatus().equals(LoadiumSessionStatus.FINISHED)) {
                result = Result.SUCCESS;
            }

            Thread.sleep(10000);
            return result;

        } catch (InterruptedException e) {

            result = Result.ABORTED;
            lentry.append("Job was stopped by user on Jenkins. Please waiting...");
            this.listener.getLogger().println(lentry.toString());
            lentry.setLength(LastPrint);
            throw new InterruptedException("Job was stopped by user on Jenkins");

        } catch (Exception e) {

            result = Result.NOT_BUILT;
            lentry.append("An unknown error has occurred. Session is being stopped. Please waiting...");
            this.listener.getLogger().println(lentry.toString());
            lentry.setLength(LastPrint);
            throw new Exception("Job was stopped due to unknown reason");

        } finally {

            //Finally Durumunda Session'ı her turlu kapatmalıyız. Zaten Bitmis de olabilir. Oyuzden sessionEndedProggress Method'u kapatma istegi gonderirken 500 alabilir.
            ProcessUtil.sessionEndedProgress(sessionKey, this.getTestId(), this.listener);
            //Session'ın Loadium arayüzünden kapanma ihtimali ne karsılık tekrar bir status kontrolu yapmalıyız.
            if (loadiumService.getSessionStatus(sessionKey).getLoadiumSessionBasicDetailsDTO().getSessionStatus().equals(LoadiumSessionStatus.STARTED)) {
                ProcessUtil.sessionEndedProgress(sessionKey, this.getTestId(), this.listener);
            }

            lentry.append("Loadium Session Result = " + result.toString());
            this.listener.getLogger().println(lentry.toString());
            lentry.setLength(LastPrint);

//            EnvVars.masterEnvVars.remove(this.getTestId() + "-" + sessionKey, publicReportURL);
            consoleLogger.close();

        }

    }

    @Override
    public void checkRoles(RoleChecker roleChecker) throws SecurityException {

    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public void setListener(BuildListener listener) {
        this.listener = listener;
    }

    public void setCredentialModel(CredentialModel credentialModel) {
        this.credentialModel = credentialModel;
    }
}
