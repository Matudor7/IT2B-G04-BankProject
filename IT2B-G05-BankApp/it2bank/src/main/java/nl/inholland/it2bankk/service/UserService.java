package nl.inholland.it2bankk.service;

import jakarta.persistence.EntityNotFoundException;
import nl.inholland.it2bankk.model.UserModel;
import nl.inholland.it2bankk.model.UserRoles;
import nl.inholland.it2bankk.model.dto.UserDTO;
import nl.inholland.it2bankk.repository.UserRepository;
import nl.inholland.it2bankk.util.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Lazy
@Service
public class UserService {

    private final UserRepository userRepository;
    private List<UserModel> users;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private UserModel existingUser;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<UserModel> findUserByAttributes(Integer id,  String firstName, String lastName, Long bsn, String phoneNumber, String email, UserRoles role, Double transactionLimit, Double dailyLimit ){ return (List<UserModel>) userRepository.findUserByAttributes(id, firstName, lastName, bsn, phoneNumber, email, role, transactionLimit, dailyLimit); }

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
        user.setRole(UserRoles.valueOf(userDto.role()));
        user.setTransactionLimit(userDto.transactionLimit());
        user.setDailyLimit(userDto.dailyLimit());

        return user;
    }

    public UserModel getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void updateUser(long id, UserDTO updatedUser){
        UserModel existingUser = this.getUserById(id);

        existingUser.setFirstName(updatedUser.firstName());
        existingUser.setLastName(updatedUser.lastName());
        existingUser.setBsn(updatedUser.bsn());
        existingUser.setEmail(updatedUser.email());
        existingUser.setPhoneNumber(updatedUser.phoneNumber());
        existingUser.setPassword(updatedUser.password());
        existingUser.setRole(UserRoles.valueOf(updatedUser.role()));
        existingUser.setDailyLimit(updatedUser.dailyLimit());
        existingUser.setTransactionLimit(updatedUser.transactionLimit());

        userRepository.save(existingUser);
    }

    public void deleteUser(long id){
        UserModel user = this.getUserById(id);
        if(user.getRole() == UserRoles.User) {
            userRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("User cannot be deleted");
        }
    }

    public UserModel saveUser(UserDTO user){
        return userRepository.save(this.mapObjectToUser(user));
    }

    public String login(String email, String password) throws Exception {
        // See if a user with the provided username exists or throw exception
        UserModel user = this.userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found"));

        // Check if the password hash matches
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // Return a JWT to the client
            return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
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
