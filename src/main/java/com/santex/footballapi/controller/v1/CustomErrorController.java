package com.santex.footballapi.controller.v1;

import com.santex.footballapi.dto.response.Response;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController  {
 
    @RequestMapping("/error")
    public ResponseEntity handleError() {
        Response<Object> response = Response.notFound();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}