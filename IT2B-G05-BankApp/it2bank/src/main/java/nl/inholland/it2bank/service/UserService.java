package nl.inholland.it2bank.service;

import jakarta.persistence.EntityNotFoundException;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private List<UserModel> users;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getAllUsers(){
        users = (List<UserModel>) userRepository.findAll();
        return users;
    }

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
        user.setRoleId(userDto.roleId());

        return user;
    }

    public UserModel getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
