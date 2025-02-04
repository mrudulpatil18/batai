package msp.batai.batai.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import msp.batai.batai.Model.User;
import msp.batai.batai.Repository.UserRepository;
import msp.batai.batai.security.SecurityUser;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        if(findUserByUsername(user.getUsername()) != null){
            throw new IllegalArgumentException("Username " + user.getUsername() + " already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getPhoneNumber() > 9999999999L && user.getPhoneNumber() <= 999999999L){
            throw new IllegalArgumentException("Invalid Mobile Number");
        }
        return userRepository.save(user);
    }

    public boolean loginUser(User user){
        User dbUser = userRepository.findByUsername(user.getUsername());
        if(dbUser != null  &&passwordEncoder.encode(user.getPassword()).equals(dbUser.getPassword())){
            return true;
        }
        return false;
    }

    public UserDetails loadUserByUsername(String userName) {
        return new SecurityUser(userRepository.findByUsername(userName));
    }

    public User findUserByUsername(String userName){
        return userRepository.findByUsername(userName);
    }

    public boolean doesUserExist(String username){
        if(findUserByUsername(username) != null){
            return true;
        }
        return false;
    }
}
