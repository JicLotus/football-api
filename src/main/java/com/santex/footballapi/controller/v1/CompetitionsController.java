package com.santex.footballapi.controller.v1;

import com.santex.footballapi.dto.model.competitions.CompetitionsDto;
import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.service.CompetitionsService;
import com.santex.footballapi.service.ImporterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/v1/competitions")
public class CompetitionsController {

    @Autowired
    private CompetitionsService competitionsService;

    @Autowired
    private ImporterService importerService;

    @GetMapping("import-league/{leagueCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> importLeague(@PathVariable String leagueCode) {
        return importerService.importFromLeagueCode(leagueCode);
    }

    @GetMapping("total-players/{leagueCode}")
    public CompetitionsDto getTotalPlayers(@PathVariable String leagueCode) {
        return competitionsService.getTotalPlayers(leagueCode);
    }
    
}