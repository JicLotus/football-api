
package com.santex.footballapi.service;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;

public interface CompetitionsService {
    /**
     * Get total players
     *
     * @param userDto
     * @return
     */
    CompetitionsDto getTotalPlayers(String leagueCode);

}