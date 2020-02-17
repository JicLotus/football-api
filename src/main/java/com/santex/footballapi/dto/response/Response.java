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
        return buildResponse("League already imported");
    }

    public static <T> Response<T> notFound() {
        return buildResponse("Not found");
    }

    public static <T> Response<T> badRequest() {
        return buildResponse("Bad request");
    }

    public static <T> Response<T> successfullyImported() {
        return buildResponse("Successfully imported");
    }

    public static <T> Response<T> serverError() {
        return buildResponse("Server error");
    }

    public static <T> Response<T> buildResponse(String message){
        Response<T> response = new Response<>();
        response.setMessage(message);
        return response;
    }

}