package nl.inholland.it2bank.service;

import jakarta.persistence.EntityNotFoundException;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.repository.UserRepository;
import nl.inholland.it2bank.util.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;
import java.util.List;

@Lazy
@Service
public class UserService {

    private final UserRepository userRepository;
    private List<UserModel> users;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<UserModel> findUserByAttributes(Integer id,  String firstName, String lastName, Long bsn, String phoneNumber, String email, Integer roleId){ return (List<UserModel>) userRepository.findUserByAttributes(id, firstName, lastName, bsn, phoneNumber, email, roleId); }

    public UserModel addUser(UserDTO userDto){
        return userRepository.save(this.mapObjectToUser(userDto));
    }

    private UserModel mapObjectToUser(UserDTO userDto){
        UserModel user = new UserModel();

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBsn(userDto.bsn());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setPhoneNumber(userDto.phoneNumber());
        user.setRole(userDto.role());

        return user;
    }

    public UserModel getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }

    public String login(String email, String password) throws Exception {
        // See if a user with the provided username exists or throw exception
        UserModel user = this.userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found"));

        // Check if the password hash matches
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // Return a JWT to the client
            return jwtTokenProvider.createToken(user.getEmail(), UserRoles.valueOf(user.getRole().toString()).ordinal()); //convert to int
        } else {
            throw new AuthenticationException("Invalid username/password");
        }
    }

    public UserDetails getUserByEmail(String email){
        UserModel user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(email)
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .build();
    }
}
