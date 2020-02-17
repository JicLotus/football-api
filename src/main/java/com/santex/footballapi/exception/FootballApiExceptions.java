package com.santex.footballapi.exception;

import org.springframework.stereotype.Component;

@Component
public class FootballApiExceptions {

    public static class LeagueCodeAlreadyImportedException extends RuntimeException {
        public LeagueCodeAlreadyImportedException() {
            super();
        }
    }

    public static class ServerErrorException extends RuntimeException {
        public ServerErrorException(String message) {
            super(message);
        }
    }

}
