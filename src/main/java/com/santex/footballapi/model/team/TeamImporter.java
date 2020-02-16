package com.santex.footballapi.model.team;

import java.util.Set;
import java.util.stream.Collectors;

import com.santex.footballapi.model.player.Player;
import com.santex.footballapi.model.team.Team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamImporter {
    
    private Set<Player> squad;
    private Set<Player> players;

    public void setSquad(Set<Player> squad){
        Set<Player> players = squad
                                .stream()
                                .filter((Player pl) -> pl.getRole().equals("PLAYER"))
                                .collect(Collectors.toSet());
        this.squad = squad;
        this.players = players;
    }
}