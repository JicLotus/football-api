package com.santex.footballapi.player;

import com.santex.footballapi.model.player.Player;
import com.santex.footballapi.repository.PlayerRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerTests {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private PlayerRepository playerRepository;
 

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Player alex = new Player();
        alex.setId(1L);
        alex.setName("Alex");
        entityManager.persist(alex);
        entityManager.flush();
     
        // when
        Player found = playerRepository.findByName(alex.getName());
     
        assertThat(found.getName().toString()).isEqualTo("Alex");
    }
    
 
}