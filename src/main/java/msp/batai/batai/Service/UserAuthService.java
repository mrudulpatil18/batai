package msp.batai.batai.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import msp.batai.batai.Model.User;
import msp.batai.batai.Repository.UserRepository;
import msp.batai.batai.security.SecurityUser;

@Service
public class UserAuthService  implements UserDetailsService{

    private final UserRepository userRepository;

    public UserAuthService(UserRepository ur){
        this.userRepository = ur;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User u = userRepository.findUserByUsername(username);
            return new SecurityUser(u);
    }
    
}
