
package com.santex.footballapi.service;

public interface ImporterService {
    /**
     * Get total players
     *
     * @param userDto
     * @return
     */
    void importFromLeagueCode(String leagueCode);

}