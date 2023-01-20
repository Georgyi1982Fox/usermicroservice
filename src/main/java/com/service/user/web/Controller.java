package com.service.user.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/v1/message")
public class Controller {
    @GetMapping("/unrestricted")
    public ResponseEntity<?> getMessage(){
        return new ResponseEntity<>("Message ...", HttpStatus.OK);
    }
    @GetMapping("/restricted")
    public ResponseEntity<?> getRestrictedMessage(){
        return new ResponseEntity<>("This is a restricted message", HttpStatus.OK);
    }
}

