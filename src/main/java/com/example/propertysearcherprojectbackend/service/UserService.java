package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.UserDto;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    public User saveUser(UserDto user) {
        return userRepository.save(modelMapper.map(user, User.class));
    }

    public void deleteUserById(final Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(UserDto userDto, long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userDto.getUserId()));
        } else {
            userDto.setUserId(userId);
            return saveUser(userDto);
        }
    }
}
