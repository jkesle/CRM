package jkesle.crm.server.controller;

import java.util.Map;

import javax.swing.SpinnerDateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jkesle.crm.server.data.UserRepository;
import jkesle.crm.server.model.User;
import jkesle.crm.server.service.AuthService;
import jkesle.crm.server.service.Result;

@RestController
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        Result<User> result = service.authenticate(credentials.get("username"), credentials.get("password"));
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
