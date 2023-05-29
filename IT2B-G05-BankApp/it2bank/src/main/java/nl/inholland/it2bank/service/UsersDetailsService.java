package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserModel user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));

        return User
                .withUsername(email)
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
    }
}