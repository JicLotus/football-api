package com.santex.footballapi.controller.v1;

import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.service.CompetitionsService;
import com.santex.footballapi.service.ImporterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/competitions")
public class CompetitionsController {

    @Autowired
    private CompetitionsService competitionsService;

    @Autowired
    private ImporterService importerService;

    @GetMapping("import-league/{leagueCode}")
    public Response<Object> importLeague(@PathVariable String leagueCode) {
        importerService.importFromLeagueCode(leagueCode);
        return Response
                .ok();
    }

    @GetMapping("total-players/{leagueCode}")
    public Response<Object> getTotalPlayers(@PathVariable String leagueCode) {
        return Response
                .ok()
                .setPayload(competitionsService.getTotalPlayers(leagueCode));
    }
    
}