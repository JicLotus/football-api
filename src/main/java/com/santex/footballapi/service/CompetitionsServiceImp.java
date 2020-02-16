package com.santex.footballapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.team.Team;
import com.santex.footballapi.repository.CompetitionRepository;

@Component
public class CompetitionsServiceImp implements CompetitionsService {

    @Autowired
    private CompetitionRepository competitionRepository;


    @Override
    public CompetitionsDto getTotalPlayers(String leagueCode) {
        int total = competitionRepository.getTotalPlayers(leagueCode);
        return new CompetitionsDto()
                .setTotalPlayers(total);
    }

}