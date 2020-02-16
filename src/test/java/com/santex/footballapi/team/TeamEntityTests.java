package com.santex.footballapi.team;

import com.santex.footballapi.model.player.Player;
import com.santex.footballapi.model.team.Team;
import com.santex.footballapi.repository.PlayerRepository;
import com.santex.footballapi.repository.TeamRepository;

import org.assertj.core.util.Arrays;
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
public class TeamEntityTests {
 
    @Autowired
    private TestEntityManager entityManager;
 

    @Autowired
    private TeamRepository teamRepository;
 
    private Team team;
    
    @Before
	public void setUp() {

        Player player = new Player()
            .setId(1L)
            .setName("Jose")
            .setCountryOfBirth("CountryOfBirth")
            .setDateOfBirth("DateOfBirth")
            .setNationality("TheNationality")
            .setPosition("ThePosition");

        Set<Player> players = new HashSet<Player>();
        players.add(player);

        this.team = new Team()
            .setId(1L)
            .setAreaName("areaName")
            .setEmail("email")
            .setName("TheTeam")
            .setShortName("ShorName")
            .setTla("Tla")
            .setPlayers(players);
    }

    @Test
    public void whenFindById_thenReturnTeam() {
        entityManager.persistAndFlush(team);
        Team found = teamRepository.findById(team.getId()).get();

        assertThat(found.getPlayers().size()).isEqualTo(1);
        assertThat(found).isEqualTo(team);
    }
    
}