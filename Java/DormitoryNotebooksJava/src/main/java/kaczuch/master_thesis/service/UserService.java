package kaczuch.master_thesis.service;

import kaczuch.master_thesis.dto.UserDto;
import kaczuch.master_thesis.model.User;

import java.util.Optional;

public interface UserService {
	User save(UserDto userDto);
	Optional<User> findById(Long id);
}
