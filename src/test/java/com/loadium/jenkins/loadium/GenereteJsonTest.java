package com.loadium.jenkins.loadium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loadium.jenkins.loadium.enums.LoadiumSessionStatus;
import com.loadium.jenkins.loadium.model.LoadiumSessionBasicDetailsDTO;
import com.loadium.jenkins.loadium.model.LoadiumTestBasicDetailsDTO;
import com.loadium.jenkins.loadium.model.wrapper.DefaultResponse;
import com.loadium.jenkins.loadium.model.wrapper.GetBasicTestResponse;
import com.loadium.jenkins.loadium.model.wrapper.JMeterRunningSessionResponse;
import com.loadium.jenkins.loadium.model.wrapper.StartSessionResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by furkanbrgl on 23/11/2017.
 */
public class GenereteJsonTest {

    @Test
    public void genereteSessionJsonFromObjecttest() throws JsonProcessingException {

        StartSessionResponse startSessionResponse = new StartSessionResponse();

        LoadiumSessionBasicDetailsDTO loadiumSessionBasicDetailsDTO = new LoadiumSessionBasicDetailsDTO();
        loadiumSessionBasicDetailsDTO.setSessionKey("bklre7u87n3ph4ah660g12bpvs62qewq");
        loadiumSessionBasicDetailsDTO.setTestKey("bklre7u87n3ph4ah660g12bpvs62qewq");
        loadiumSessionBasicDetailsDTO.setSessionStatus(LoadiumSessionStatus.STARTED);

        startSessionResponse.setStatus("SUCCES");
        startSessionResponse.setSession(loadiumSessionBasicDetailsDTO);

        System.out.println(new ObjectMapper().writeValueAsString(startSessionResponse));

    }

    @Test
    public void genereteDefaultResponseJsonFromOj() throws JsonProcessingException {

        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setStatus("SUCCES");

        System.out.println(new ObjectMapper().writeValueAsString(defaultResponse));
    }

    @Test
    public void genereteGetBasicTestResponseJsonFromOj() throws JsonProcessingException {

        GetBasicTestResponse getBasicTestResponse = new GetBasicTestResponse();

        List<LoadiumTestBasicDetailsDTO> detailsDTOS = new ArrayList<>();

        LoadiumTestBasicDetailsDTO dto = new LoadiumTestBasicDetailsDTO();
        dto.setCreatedTime(new Date());
        dto.setFavorite(true);
        dto.setOwner("furkanbrgl");
        dto.setTestKey("bklre7u87n3ph4ah660g12bpvs62qewq");
        dto.setTestType("HTTP");
        dto.setTestName("Loadium");
        detailsDTOS.add(dto);

        getBasicTestResponse.setTestBasicDetailsDTOs(detailsDTOS);
        getBasicTestResponse.setStatus("SUCCES");
        System.out.println(new ObjectMapper().writeValueAsString(getBasicTestResponse));

    }

    @Test
    public void genereteJMeterRunningSessionResponseJsonFromOj() throws JsonProcessingException {

        JMeterRunningSessionResponse jMeterRunningSessionResponse = new JMeterRunningSessionResponse();
        jMeterRunningSessionResponse.setStatus("SUCCES");

        LoadiumSessionBasicDetailsDTO loadiumSessionBasicDetailsDTO = new LoadiumSessionBasicDetailsDTO();
        loadiumSessionBasicDetailsDTO.setSessionStatus(LoadiumSessionStatus.STARTED);
        loadiumSessionBasicDetailsDTO.setTestKey("vvdsa7u87n3ph4ah660g12bpvs62qewq");
        loadiumSessionBasicDetailsDTO.setSessionKey("bklre7u87n3ph4ah660g12bpvs62qewq");

        jMeterRunningSessionResponse.setLoadiumSessionBasicDetailsDTO(loadiumSessionBasicDetailsDTO);
        System.out.println(new ObjectMapper().writeValueAsString(jMeterRunningSessionResponse));

    }

}
