package com.santex.footballapi.model.competition;

import java.util.Set;

import com.santex.footballapi.model.team.Team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionImporter {
    private Competition competition;
    private Set<Team> teams;

    public void setTeams(Set<Team> teams){
        this.teams = teams;
        competition.setTeams(teams);
    }
}