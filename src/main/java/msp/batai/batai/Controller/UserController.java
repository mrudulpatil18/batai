package msp.batai.batai.Controller;

import org.springframework.web.bind.annotation.RestController;

import msp.batai.batai.Model.User;
import msp.batai.batai.Service.JwtService;
import msp.batai.batai.Service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
public ResponseEntity<?> registerUser(@RequestBody User user) {
    try {
        User u = userService.registerUser(user);
        // Return a JSON object for successful registration
        return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", u));
    } catch (IllegalArgumentException e) {
        // Return a JSON object for an error
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(Map.of("message", "User already exists"));
    }
}


    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());

            authenticationManager.authenticate(authToken);

            String token = jwtUtil.generateToken(loginRequest.getUsername());
            User u = userService.findUserByUsername(loginRequest.getUsername());

            return ResponseEntity.ok(Map.of("token", token, "user", u));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error during authentication"));
        }
    }

}
