package com.santex.footballapi.competition;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.repository.CompetitionRepository;
import com.santex.footballapi.service.CompetitionsService;
import com.santex.footballapi.service.CompetitionsServiceImp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
public class CompetitionIntegrationTests {
 
    @TestConfiguration
    static class CompetitionsServiceImplTestContextConfiguration {
  
        @Bean
        public CompetitionsService competitionService() {
            return new CompetitionsServiceImp();
        }
    }
 
    @Autowired
    private CompetitionsService competitionService;
 
    @MockBean
    private CompetitionRepository competitionRepository;
 
    @Before
    public void setUp() {
        Competition competition = new Competition()
            .setId(1L)
            .setAreaName("areaName")
            .setCode("The champion")
            .setName("CL");
        
        Mockito.when(competitionRepository.getByCode("CL"))
            .thenReturn(competition);
        Mockito.when(competitionRepository.getTotalPlayers("CL"))
            .thenReturn(10);
    }

    @Test
    public void whenValidName_thenGetTotalAmount() {
        CompetitionsDto compDTO = competitionService.getTotalPlayers("CL");
        assertThat(compDTO.getTotal())
            .isEqualTo(10);
    }
  
    @Test(expected = EntityNotFoundException.class)
    public void whenInvalidName_thenGetInvalidEntityException() {
        CompetitionsDto compDTO = competitionService.getTotalPlayers("PL");
    }

    
}