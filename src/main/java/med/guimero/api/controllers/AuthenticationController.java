package med.guimero.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.user.User;
import med.guimero.api.domain.user.UserAuthenticationData;
import med.guimero.api.infra.security.JWTTokenData;
import med.guimero.api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData){
        Authentication authToken = new UsernamePasswordAuthenticationToken
                (userAuthenticationData.login(), userAuthenticationData.password());
                var authenticatedUser = authenticationManager.authenticate(authToken);
                var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(JWTToken));
    }
}
