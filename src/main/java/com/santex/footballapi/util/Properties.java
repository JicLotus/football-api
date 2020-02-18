package com.santex.footballapi.util;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Properties  {

    @Autowired
    private Environment env;

    public Integer IMPORTER_SLEEPTIME_MILISECONDS(){
        return Integer.parseInt(env.getProperty("importer.sleeptime.miliseconds"));
    }

    public String IMPORTER_TEAMS_URL(){
        return env.getProperty("importer.teams.url");
    }

    public String IMPORTER_COMPETITIONS_TEAMS_URL(){
        return env.getProperty("importer.competitions.teams.url");
    }

    public String IMPORTER_TOKEN(){
        return env.getProperty("importer.token");
    }

}