package msp.batai.batai.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import msp.batai.batai.Model.User;
import msp.batai.batai.Repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean loginUser(User user){
        User dbUser = userRepository.findByUsername(user.getUsername());
        if(dbUser != null  &&passwordEncoder.encode(user.getPassword()).equals(dbUser.getPassword())){
            return true;
        }
        return false;
    }
}
