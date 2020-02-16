package com.santex.footballapi.competition;

import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.player.Player;
import com.santex.footballapi.model.team.Team;
import com.santex.footballapi.repository.CompetitionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompetitionEntityTests {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CompetitionRepository competitionRepository;
 
    private Competition competition;
    
    @Before
	public void setUp() {

        Player player = new Player()
            .setId(1L)
            .setName("Jose")
            .setCountryOfBirth("CountryOfBirth")
            .setDateOfBirth("DateOfBirth")
            .setNationality("TheNationality")
            .setPosition("ThePosition");

        Player playerTwo = new Player()
            .setId(2L)
            .setName("Santex")
            .setCountryOfBirth("CountryOfBirth")
            .setDateOfBirth("DateOfBirth")
            .setNationality("TheNationality")
            .setPosition("ThePosition");

        Set<Player> players = new HashSet<Player>();
        players.add(player);
        players.add(playerTwo);

        Team team = new Team()
            .setId(1L)
            .setAreaName("areaName")
            .setEmail("email")
            .setName("TheTeam")
            .setShortName("ShorName")
            .setTla("Tla")
            .setPlayers(players);

        Set<Team> teams = new HashSet<Team>();
        teams.add(team);

        this.competition = new Competition()
            .setId(1L)
            .setAreaName("areaName")
            .setCode("The champion")
            .setName("CL")
            .setTeams(teams);
    }

    @Test
    public void whenFindById_thenReturnCompetition() {
        entityManager.persistAndFlush(competition);
        Competition found = competitionRepository.findById(competition.getId()).get();

        assertThat(found.getTeams().size()).isEqualTo(1);
        assertThat(found).isEqualTo(competition);
    }
    
}