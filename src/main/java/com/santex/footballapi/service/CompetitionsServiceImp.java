package com.santex.footballapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;
import com.santex.footballapi.repository.CompetitionRepository;

@Component
public class CompetitionsServiceImp implements CompetitionsService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public CompetitionsDto getTotalPlayers(String leagueCode) {
        this.doesLeagueCodeExist(leagueCode);
        int total = competitionRepository.getTotalPlayers(leagueCode);
        return new CompetitionsDto()
                .setTotal(total);
    }

    private void doesLeagueCodeExist(String legueCode){
        if (competitionRepository.getByCode(legueCode)==null){
            throw new EntityNotFoundException();
        }
    }

}