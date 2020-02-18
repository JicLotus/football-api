
package com.santex.footballapi.service;

import com.santex.footballapi.dto.response.Response;

public interface ImporterService {
    /**
     * Get total players
     *
     * @param userDto
     * @return
     */
    Response<Object> importFromLeagueCode(String leagueCode);

}