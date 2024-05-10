package codescreen.tipa.bank.controllers;

import codescreen.tipa.bank.model.AuthorizationRequest;
import codescreen.tipa.bank.model.AuthorizationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import codescreen.tipa.bank.service.AuthorizationRequestService;
import codescreen.tipa.bank.service.Result;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    private final AuthorizationRequestService service;

    public AuthorizationController(AuthorizationRequestService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Object> authorize(@RequestBody AuthorizationRequest request){


        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(result.getError(), HttpStatus.BAD_REQUEST);
    }
}
