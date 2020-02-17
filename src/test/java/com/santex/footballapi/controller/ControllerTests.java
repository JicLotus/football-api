package com.santex.footballapi.controller;

import com.santex.footballapi.FootballApiApplication;
import com.santex.footballapi.controller.v1.CompetitionsController;
import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.repository.CompetitionRepository;
import com.santex.footballapi.service.CompetitionsService;
import com.santex.footballapi.service.ImporterService;
import com.santex.footballapi.service.ImporterServiceImp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.santex.footballapi.exception.FootballApiExceptions.LeagueCodeAlreadyImportedException;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FootballApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ControllerTests {
 
    @Autowired
    private MockMvc mvc;
 
    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private ImporterService importerService;

    @Autowired
    private CompetitionRepository competitionRepository;

    
    @Before
    public void setUp() {
        this.createCompetition("CL");
        given(importerService.importFromLeagueCode("CL"))
            .willThrow(new LeagueCodeAlreadyImportedException());
        given(importerService.importFromLeagueCode("PL"))
            .willReturn(Response.successfullyImported());
    }

    @Test
    public void givenBadRequestRoute_thenReturn400()
        throws Exception {

        mvc.perform(get("/v1/competitions/bad-request-route/")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    public void givenFakeCode_whenTotalPlayerIsRequested_thenReturn404() throws Exception {
        mvc.perform(get("/v1/competitions/total-players/FAKECODE")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
    }
    
    @Test
    public void givenCLCode_whenTotalPlayerIsRequested_thenReturn200() throws Exception {
        
        mvc.perform(get("/v1/competitions/total-players/CL")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    public void givenCLAlreadyImportedCode_whenImportPlayerIsRequested_thenReturn409() throws Exception {
        mvc.perform(get("/v1/competitions/import-league/CL")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isConflict());
    }

    @Test
    public void givenPLCode_whenImportPlayerIsRequested_thenReturn201() throws Exception {
        mvc.perform(get("/v1/competitions/import-league/PL")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
    }

    private void createCompetition(String code) {
        Competition competition = new Competition()
                                    .setId(1L)
                                    .setCode("CL");
        competitionRepository.saveAndFlush(competition);
    }

}