package com.santex.footballapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.competition.CompetitionImporter;
import com.santex.footballapi.model.player.Player;
import com.santex.footballapi.model.team.Team;
import com.santex.footballapi.repository.CompetitionRepository;
import com.santex.footballapi.repository.TeamRepository;
import com.santex.footballapi.util.Properties;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.santex.footballapi.model.team.TeamImporter;
import com.santex.footballapi.exception.FootballApiExceptions.LeagueCodeAlreadyImportedException;

@Component
public class ImporterServiceImp implements ImporterService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Properties properties;

    @Override
    public Response<Object> importFromLeagueCode(String leagueCode) {
        try{
            this.checkIfCompetitionExist(leagueCode);
            Competition competition = this.fetchCompetition(leagueCode);
            competitionRepository.flush();
            competitionRepository.save(competition);
            this.savePlayers(competition);
            return Response.successfullyImported(); 
        }
        catch(HttpClientErrorException ex){
            HttpStatus status = ex.getStatusCode();
            if (status == HttpStatus.BAD_REQUEST  || status == HttpStatus.NOT_FOUND){
                throw new EntityNotFoundException();
            }
            throw ex;
        }
        catch (Exception ex){
            throw ex;
        }
    }

    private void savePlayers(Competition competition){
        Set<Team> teams = competition.getTeams();
        for(Team team : teams){
            Set<Player> players = fetchPlayers(team);
            team.setPlayers(players);
            teamRepository.flush();
            teamRepository.save(team);
            try {
                Thread.sleep(properties.IMPORTER_SLEEPTIME_MILISECONDS());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<Player> fetchPlayers(Team team){
        Long teamId = team.getId();
        String newUrl = properties
                            .IMPORTER_TEAMS_URL()
                            .concat(String.valueOf(teamId));
        ResponseEntity<TeamImporter> playersResponse = restTemplate.exchange(newUrl, HttpMethod.GET, this.buildHttpEntity(),
                TeamImporter.class);
        TeamImporter teamImp = playersResponse.getBody();
        return teamImp.getPlayers();
    }

    private Competition fetchCompetition(String leagueCode){     
        ResponseEntity<CompetitionImporter> response = restTemplate
                                                .exchange(
                                                    String.format(properties.IMPORTER_COMPETITIONS_TEAMS_URL(),leagueCode),
                                                    HttpMethod.GET,
                                                    this.buildHttpEntity(),
                                                    CompetitionImporter.class);
        CompetitionImporter compImporter = response.getBody();
        Competition competition = compImporter.getCompetition();
        competition.setAreaName(competition.getArea().getName());
        return competition;
    }

    private void checkIfCompetitionExist(String legueCode){
        Competition competition = competitionRepository.getByCode(legueCode);
        if (competition != null){
            throw new LeagueCodeAlreadyImportedException();
        }
    }

    private HttpEntity<String> buildHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", properties.IMPORTER_TOKEN());
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return entity;
    }

    @Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

}