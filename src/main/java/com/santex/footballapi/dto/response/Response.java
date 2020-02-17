package com.santex.footballapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.santex.footballapi.util.DateUtils;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;





@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {

    private String message;

    public static <T> Response<T> leagueCodeAlreadyImportedException() {
        Response<T> response = new Response<>();
        response.setMessage("League already imported");
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setMessage("Not found");
        return response;
    }

    public static <T> Response<T> successfullyImported() {
        Response<T> response = new Response<>();
        response.setMessage("Successfully imported");
        return response;
    }

    public static <T> Response<T> serverErro() {
        Response<T> response = new Response<>();
        response.setMessage("Server error");
        return response;
    }

}