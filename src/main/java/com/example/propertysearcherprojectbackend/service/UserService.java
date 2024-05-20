package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.UserDto;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuditMessageService auditMessageService;
    private final ModelMapper modelMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(final Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with id %s not found");
        } else {
            return userRepository.findById(userId);
        }
    }

    public User saveUser(UserDto userDto) {
        User user = userRepository.save(modelMapper.map(userDto, User.class));
        auditMessageService.saveAuditMessage(AuditMessage.builder()
                .message("New user added: " + user.getUserName() + " " + user.getUserLastName())
                .createdAt(LocalDateTime.now())
                .build());
        return user;
    }

    public void deleteUserById(final Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with id %s not found");
        } else {
            auditMessageService.saveAuditMessage(AuditMessage.builder()
                    .message("User deleted: user id " + userId)
                    .createdAt(LocalDateTime.now())
                    .build());
            userRepository.deleteById(userId);
        }
    }

    public User updateUser(UserDto userDto, long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        } else {
            User user = userRepository.save(modelMapper.map(userDto, User.class));
            user.setUserId(userId);
            return userRepository.save(user);
        }
    }
}
