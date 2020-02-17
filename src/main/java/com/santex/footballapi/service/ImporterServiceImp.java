package com.santex.footballapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.competition.CompetitionImporter;
import com.santex.footballapi.model.team.Team;
import com.santex.footballapi.repository.CompetitionRepository;
import com.santex.footballapi.repository.TeamRepository;

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

    private static HttpEntity<String> entity = buildHttpEntity();

    //@Value("{importer.sleeptime.miliseconds}")
    //private Integer sleep;

    //TOOD: Add as env configuration
    private String teamsUrl = "https://api.football-data.org/v2/competitions/%s/teams";
    private String playersUrl = "https://api.football-data.org/v2/teams/";

    //Avoid loading all entities when project begins
    @Override
    public Response<Object> importFromLeagueCode(String leagueCode) {
        
        try{
            
            Competition competition = this.fetchCompetition(leagueCode);
            this.checkIfCompetitionExist(competition);

            competitionRepository.save(competition);

            competition.getTeams().forEach((Team team) -> {
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

    private Competition fetchCompetition(String leagueCode){     
        ResponseEntity<CompetitionImporter> response = restTemplate
                                                .exchange(
                                                    String.format(teamsUrl,leagueCode),
                                                    HttpMethod.GET,
                                                    entity,
                                                    CompetitionImporter.class);
        CompetitionImporter compImporter = response.getBody();
        Competition competition = compImporter.getCompetition();
        competition.setAreaName(competition.getArea().getName());
        return competition;
    }

    private void checkIfCompetitionExist(Competition competition){
        if (competitionRepository.findById(competition.getId()).get()!=null){
            throw new LeagueCodeAlreadyImportedException();
        }
    }

    private static HttpEntity<String> buildHttpEntity(){
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