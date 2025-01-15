package msp.batai.batai.Controller;

import org.springframework.web.bind.annotation.RestController;

import msp.batai.batai.Model.User;
import msp.batai.batai.Service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    
    private final UserService userService;

    public UserController(UserService us){
        this.userService = us;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered Succesfully");
    }
}
