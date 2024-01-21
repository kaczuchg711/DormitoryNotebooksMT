package kaczuch.master_thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kaczuch.master_thesis.dto.UserDto;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getRole(), userDto.getFirst_name(), userDto.getLast_name());
        return userRepository.save(user);
    }
    //eeeeee
    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

}
