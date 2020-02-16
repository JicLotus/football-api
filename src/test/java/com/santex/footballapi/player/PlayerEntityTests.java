package com.santex.footballapi.player;

import com.santex.footballapi.model.player.Player;
import com.santex.footballapi.repository.PlayerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerEntityTests {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private PlayerRepository playerRepository;
 
    private Player jose;

    @Before
	public void setUp() {
        jose = new Player()
            .setId(1L)
            .setName("Alex")
            .setCountryOfBirth("CountryOfBirth")
            .setDateOfBirth("DateOfBirth")
            .setNationality("TheNationality")
            .setPosition("ThePosition");
    }

    @Test
    public void whenFindById_thenReturnPlayer() {
        entityManager.persistAndFlush(jose);
        Player found = playerRepository.findById(jose.getId()).get();
        assertThat(found).isEqualTo(jose);
    }
    
}