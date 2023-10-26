package com.elsonkhoza.ecommerce001.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

  @PostMapping("/register")
    public ResponseEntity<SignUpRequest> register(@RequestBody SignUpRequest signUpRequest)
  {
      return  ResponseEntity.ok(signUpRequest);
  }

}
