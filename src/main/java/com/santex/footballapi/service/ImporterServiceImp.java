package com.santex.footballapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.competition.CompetitionImporter;
import com.santex.footballapi.model.team.Team;
import com.santex.footballapi.repository.CompetitionRepository;
import com.santex.footballapi.repository.TeamRepository;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import springfox.documentation.spring.web.json.Json;
import org.springframework.web.client.RestTemplate;

import com.santex.footballapi.model.team.TeamImporter;

@Component
public class ImporterServiceImp implements ImporterService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private RestTemplate restTemplate;

    //TOOD: Add as env configuration
    private String teamsUrl = "https://api.football-data.org/v2/competitions/CL/teams";
    private String playersUrl = "https://api.football-data.org/v2/teams/";

    //Avoid loading all entities when project begins
    @Override
    public void importFromLeagueCode(String leagueCode) {

        HttpEntity<String> entity = this.buildHttpEntity();
        ResponseEntity<CompetitionImporter> response = restTemplate.exchange(teamsUrl, HttpMethod.GET, entity,
                CompetitionImporter.class);

        CompetitionImporter compImporter = response.getBody();
        Competition competition = compImporter.getCompetition();
        competition.setAreaName(competition.getArea().getName());

        competitionRepository.save(competition);

        compImporter.getTeams().forEach((Team team) -> {
            Long teamId = team.getId();
            String newUrl = playersUrl.concat(String.valueOf(teamId));
            ResponseEntity<TeamImporter> playersResponse = restTemplate.exchange(newUrl, HttpMethod.GET, entity,
                    TeamImporter.class);
            TeamImporter teamImp = playersResponse.getBody();
            team.setPlayers(teamImp.getPlayers());
            teamRepository.save(team);
            try {
                //TODO: Add as env configuration
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private HttpEntity<String> buildHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "a18d01fde9094e2ca0844cb162aeac8e");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return entity;
    }

    @Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

}