package com.santex.footballapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;

@Component
public class CompetitionsServiceImp implements CompetitionsService {

    @Override
    public CompetitionsDto getTotalPlayers(String leagueCode) {
        return new CompetitionsDto()
                .setTotalPlayers(1);
    }

}