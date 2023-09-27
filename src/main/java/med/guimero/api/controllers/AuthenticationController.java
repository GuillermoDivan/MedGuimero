package med.guimero.api.controllers;

import jakarta.validation.Valid;
import med.guimero.api.domain.user.User;
import med.guimero.api.domain.user.UserAuthenticationData;
import med.guimero.api.infra.security.SecurityConfigurations;
import med.guimero.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData){
        Authentication authToken = new UsernamePasswordAuthenticationToken
                (userAuthenticationData.login(), userAuthenticationData.password());
                var authenticatedUser = authenticationManager.authenticate(authToken);
                var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new SecurityConfigurations.JWTTokenData(JWTToken));
    }
}
