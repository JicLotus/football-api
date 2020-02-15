package com.santex.footballapi.controller.v1;

import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.service.CompetitionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/competitions")
public class CompetitionsController {

    @Autowired
    private CompetitionsService competitionsService;

    @GetMapping("total-players/{leagueCode}")
    public Response getTotalPlayers(@PathVariable String leagueCode) {
        return Response
                .ok()
                .setPayload(competitionsService.getTotalPlayers(leagueCode));
    }
    
}